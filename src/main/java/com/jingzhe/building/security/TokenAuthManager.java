package com.jingzhe.building.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthManager implements ReactiveAuthenticationManager {

    private final JwtBearerTokenVerifier jwtVerifier;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .filter(authToken -> authToken instanceof BearerTokenAuthenticationToken)
                .cast(BearerTokenAuthenticationToken.class)
                .flatMap(bearerToken -> jwtVerifier.decode(bearerToken.getToken())
                        .map(claims -> {
                            log.info("Access token claims:{}", claims);
                            bearerToken.setAuthenticated(true);
                            return bearerToken;
                        }))
                .cast(Authentication.class);

    }
}
