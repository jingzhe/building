package com.jingzhe.building.service;

import com.jingzhe.building.config.BuildingProperties;
import com.jingzhe.building.exception.VerificationException;
import com.jingzhe.building.integration.AuthClient;
import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.source.DefaultJWKSetCache;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Service
public class RemoteJwksService {
    private final AuthClient authClient;
    private final DefaultJWKSetCache defaultJWKSetCache;

    public RemoteJwksService(BuildingProperties buildingProperties, AuthClient authClient) {
        this.authClient = authClient;
        defaultJWKSetCache = new DefaultJWKSetCache(48L, 24L, TimeUnit.HOURS);
    }

    public Mono<JWK> getVerificationKey(String kid, JWSAlgorithm alg, boolean flush) {
        return getJWK(kid, alg, KeyUse.SIGNATURE, flush)
                .onErrorResume(throwable -> getJWK(kid, alg, KeyUse.SIGNATURE, true));
    }

    private Mono<JWK> getJWK(String kid, Algorithm alg, KeyUse use, boolean flush) {
        return getJWKSet(flush)
                .map(jwkSet -> findKey(jwkSet, kid, alg, use));

    }

    private Mono<JWKSet> getJWKSet(boolean flush) {
        if (flush || defaultJWKSetCache.get() == null || defaultJWKSetCache.isExpired() || defaultJWKSetCache.requiresRefresh()) {
            return authClient.loadJWKSet()
                    .map(this::cacheJWKSet);
        } else {
            return Mono.fromCallable(defaultJWKSetCache::get);
        }
    }

    private JWKSet cacheJWKSet(JWKSet jwkSet) {
        this.defaultJWKSetCache.put(jwkSet);
        return jwkSet;
    }

    private JWK findKey(JWKSet jwkSet, String kid, Algorithm alg, KeyUse use) {
        return jwkSet.getKeys().stream()
                .filter(jwk -> jwk.getKeyUse() == use && jwk.getKeyID().equals(kid) && jwk.getAlgorithm().equals(alg))
                .findFirst()
                .orElseThrow(() -> new VerificationException("Cannot find suitable key for kid=" + kid + ",alg=" + alg + ",use=" + use));
    }
}
