package com.jingzhe.building.integration;

import com.jingzhe.building.config.CircuitProperties;
import com.jingzhe.building.exception.Backend4xxException;
import com.jingzhe.building.exception.Backend5xxException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class WebClientErrorHandler {

    private Throwable mapToBackendException(Throwable t) {
        if (t instanceof WebClientResponseException ex) {

            HttpStatus statusCode = ex.getStatusCode();
            if (statusCode.is4xxClientError()) {
                return new Backend4xxException(ex);
            }
            if (statusCode.is5xxServerError()) {
                return new Backend5xxException(ex);
            }
        }
        return t;
    }

    public <T> Function<Mono<T>, Mono<T>> transformWebClientErrorAndDelay(CircuitBreaker cb, CircuitProperties properties) {
        return monoItem -> monoItem.onErrorMap
                        (this::mapToBackendException)
                .transform(CircuitBreakerOperator.of(cb))
                .onErrorResume(t -> addDelay(t, properties));
    }

    private <T> Mono<T> addDelay(Throwable error, CircuitProperties properties) {
        if (error instanceof CallNotPermittedException) {
            return Flux.interval(properties.getDelayCallNotPermittedException())
                    .take(1)
                    .single()
                    .flatMap(l -> Mono.error(error));
        }
        return Mono.error(error);
    }
}
