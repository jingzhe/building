package com.jingzhe.building.config;

import com.jingzhe.building.exception.Backend4xxException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import java.time.Duration;

@UtilityClass
@Slf4j
public class CircuitBreakerFactory {

    public CircuitBreaker fromCircuitProperties(String host, CircuitProperties circuit) {
        CircuitBreakerConfig.Builder builder = CircuitBreakerConfig.custom()
                .ignoreExceptions(Backend4xxException.class)
                .slidingWindowType(circuit.getSlidingWindowType())
                .slowCallDurationThreshold(Duration.ofMillis(circuit.getSlowCallDurationThreshold()))
                .slowCallRateThreshold(circuit.getSlowCallRateThreshold())
                .failureRateThreshold(circuit.getFailureRateThreshold())
                .waitDurationInOpenState(circuit.getWaitDurationInOpenState())
                .automaticTransitionFromOpenToHalfOpenEnabled(circuit.isAutomaticTransitionFromOpenToHalfOpenEnabled())
                .slidingWindowSize(circuit.getSlidingWindowSize())
                .minimumNumberOfCalls(circuit.getMinimumNumberOfCalls())
                .permittedNumberOfCallsInHalfOpenState(circuit.getPermittedNumberOfCallsInHalfOpenState());

        CircuitBreaker cb = CircuitBreaker.of(host, builder.build());
        if (!circuit.isEnabled()) {
            cb.transitionToDisabledState();
        }
        return cb;
    }
}