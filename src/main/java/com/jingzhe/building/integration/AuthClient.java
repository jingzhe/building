package com.jingzhe.building.integration;

import com.jingzhe.building.api.model.Building;
import com.jingzhe.building.config.BuildingProperties;
import com.jingzhe.building.exception.BuildingServiceException;
import com.jingzhe.building.model.GeoData;
import com.nimbusds.jose.jwk.JWKSet;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import reactor.core.publisher.Mono;

import java.text.ParseException;

@Component
public class AuthClient {
    private final WebClient webClient;
    private final CircuitBreaker circuitBreaker;
    private final WebClientErrorHandler webClientErrorHandler;
    private final BuildingProperties buildingProperties;

    public AuthClient(@Qualifier("webClient_auth") WebClient webClient,
                      @Qualifier("circuitBreaker_auth") CircuitBreaker circuitBreaker,
                      WebClientErrorHandler webClientErrorHandler,
                      BuildingProperties buildingProperties) {
        this.webClient =webClient;
        this.circuitBreaker = circuitBreaker;
        this.webClientErrorHandler = webClientErrorHandler;
        this.buildingProperties = buildingProperties;
    }

    public Mono<JWKSet> loadJWKSet() {
        return webClient
                .get()
                .uri(buildingProperties.getAuthPath())
                .retrieve()
                .bodyToMono(String.class)
                .transformDeferred(webClientErrorHandler.transformWebClientErrorAndDelay(circuitBreaker, buildingProperties.getAuthCircuit()))
                .onErrorMap(throwable -> new BuildingServiceException("Failed to load jwks", throwable))
                .map(this::parse);
    }

    private JWKSet parse(String body) {
        try {
            return JWKSet.parse(body);
        } catch (ParseException e) {
            throw new BuildingServiceException("Failed to parse jwks response", e);
        }
    }
}
