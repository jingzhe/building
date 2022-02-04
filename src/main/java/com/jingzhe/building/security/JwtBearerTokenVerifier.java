package com.jingzhe.building.security;

import com.jingzhe.building.exception.VerificationException;
import com.jingzhe.building.service.RemoteJwksService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.BadJWTException;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;

@Component
public class JwtBearerTokenVerifier {

    private final RemoteJwksService jwksService;
    private final DefaultJWTClaimsVerifier<SecurityContext> defaultJWTVerifier;

    @Autowired
    public JwtBearerTokenVerifier(RemoteJwksService jwksService) {
        this.jwksService = jwksService;
        defaultJWTVerifier = new DefaultJWTClaimsVerifier<>(
                new JWTClaimsSet.Builder().issuer("auth-service").build(),
                new HashSet<>(Arrays.asList("exp", "user", "group")));
    }

    public Mono<JWTClaimsSet> decode(String token) {
        return Mono.fromCallable(() -> JWTParser.parse(token))
                .cast(SignedJWT.class)
                .flatMap(signedJWT -> {
                    JWTClaimsSet claimsSet = extractClaims(signedJWT);
                    return resolveVerifier(signedJWT.getHeader())
                            .doOnSuccess(jwsVerifier -> verifySignature(signedJWT, jwsVerifier))
                            .doOnSuccess(jwsVerifier -> {
                                try {
                                    defaultJWTVerifier.verify(claimsSet, null);
                                } catch (BadJWTException e) {
                                    throw new VerificationException("Invalid access token claims, cause:" + e);
                                }
                            })
                            .then(Mono.just(claimsSet));
                });
    }

    private JWTClaimsSet extractClaims(SignedJWT signedJWT) {
        try {
            return signedJWT.getJWTClaimsSet();
        } catch (ParseException e) {
            throw new VerificationException("Failed to extract claims from the access token");
        }
    }

    private Mono<JWSVerifier> resolveVerifier(JWSHeader header) {
        String kid = header.getKeyID();
        return jwksService.getVerificationKey(kid, JWSAlgorithm.RS256, false)
                .cast(RSAKey.class)
                .map(rsaKey -> {
                    try {
                        return new RSASSAVerifier(rsaKey);
                    } catch (JOSEException e) {
                        throw new VerificationException("Create verifier failed");
                    }
                });
    }

    private void verifySignature(SignedJWT signedJWT, JWSVerifier verifier) {
        try {
            if (!signedJWT.verify(verifier)) {
                throw new VerificationException("Invalid access token signature");
            }
        } catch (JOSEException e) {
            throw new VerificationException("Access token verification failed");
        }
    }
}
