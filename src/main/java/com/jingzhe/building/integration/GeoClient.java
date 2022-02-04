package com.jingzhe.building.integration;

import com.jingzhe.building.api.model.Building;
import com.jingzhe.building.config.BuildingProperties;
import com.jingzhe.building.model.GeoData;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GeoClient {
    private final WebClient webClient;
    private final CircuitBreaker circuitBreaker;
    private final WebClientErrorHandler webClientErrorHandler;
    private final BuildingProperties buildingProperties;

    public GeoClient(@Qualifier("webClient_geo") WebClient webClient,
                     @Qualifier("circuitBreaker_geo") CircuitBreaker circuitBreaker,
                     WebClientErrorHandler webClientErrorHandler,
                     BuildingProperties buildingProperties) {
        this.webClient =webClient;
        this.circuitBreaker = circuitBreaker;
        this.webClientErrorHandler = webClientErrorHandler;
        this.buildingProperties = buildingProperties;
    }

    public Mono<GeoData> getGeoInfo(Building building) {
        return webClient
                .get()
                .uri(buildingProperties.getGeoPath(), uriBuilder -> uriBuilder
                        .queryParam("text", Building.getGeoQuery(building))
                        .queryParam("apiKey", buildingProperties.getGeoApiKey())
                        .build())
                .retrieve()
                .bodyToMono(GeoData.class)
                .transformDeferred(webClientErrorHandler.transformWebClientErrorAndDelay(circuitBreaker, buildingProperties.getGeoCircuit()));
    }

}
