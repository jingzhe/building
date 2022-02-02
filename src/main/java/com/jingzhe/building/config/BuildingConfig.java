package com.jingzhe.building.config;

import com.jingzhe.building.integration.WebClientErrorHandler;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(BuildingProperties.class)
@RequiredArgsConstructor
public class BuildingConfig {
    private final BuildingProperties properties;

    @Bean("webClient_geo")
    public WebClient webClientGeo(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(properties.getGeoBaseUrl().toString())
                .build();
    }

    @Bean("circuitBreaker_geo")
    public CircuitBreaker circuitBreakerGeo() {
        return CircuitBreakerFactory.fromCircuitProperties(properties.getGeoBaseUrl().getHost(),
                properties.getCircuit());
    }

    @Bean
    public WebClientErrorHandler webClientErrorHandler() {
        return new WebClientErrorHandler();
    }
}
