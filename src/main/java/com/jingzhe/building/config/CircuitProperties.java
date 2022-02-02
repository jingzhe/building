package com.jingzhe.building.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import lombok.Data;

import java.time.Duration;

import static io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.*;

@Data
public class CircuitProperties {

    public static final float DEFAULT_CIRCUIT_MAX_FAILURE_THRESHOLD = DEFAULT_FAILURE_RATE_THRESHOLD;
    public static final int DEFAULT_CIRCUIT_WAIT_DURATION_IN_OPEN_STATE_MS = DEFAULT_WAIT_DURATION_IN_OPEN_STATE * 1000;
    public static final float DEFAULT_CIRCUIT_SLOW_CALL_RATE_THRESHOLD = DEFAULT_SLOW_CALL_RATE_THRESHOLD;
    public static final long DEFAULT_CIRCUIT_SLOW_CALL_DURATION_THRESHOLD = DEFAULT_SLOW_CALL_DURATION_THRESHOLD * 1000;
    public static final int DEFAULT_CIRCUIT_MINIMUM_NUMBER_OF_CALLS = DEFAULT_MINIMUM_NUMBER_OF_CALLS;
    public static final Duration DEFAULT_DELAY_CALL_NOT_PERMITTED_EXCEPTION = Duration.ofSeconds(1);

    private boolean enabled = true;
    private float failureRateThreshold = DEFAULT_CIRCUIT_MAX_FAILURE_THRESHOLD;
    private int waitDurationInOpenState = DEFAULT_CIRCUIT_WAIT_DURATION_IN_OPEN_STATE_MS;

    public Duration getWaitDurationInOpenState() {
        return Duration.ofMillis(waitDurationInOpenState);
    }

    private Duration delayCallNotPermittedException = DEFAULT_DELAY_CALL_NOT_PERMITTED_EXCEPTION;

    private float slowCallRateThreshold = DEFAULT_CIRCUIT_SLOW_CALL_RATE_THRESHOLD;
    private long slowCallDurationThreshold = DEFAULT_CIRCUIT_SLOW_CALL_DURATION_THRESHOLD;
    private CircuitBreakerConfig.SlidingWindowType slidingWindowType = DEFAULT_SLIDING_WINDOW_TYPE;
    private int slidingWindowSize = DEFAULT_SLIDING_WINDOW_SIZE;
    private int permittedNumberOfCallsInHalfOpenState = DEFAULT_PERMITTED_CALLS_IN_HALF_OPEN_STATE;
    private int minimumNumberOfCalls = DEFAULT_CIRCUIT_MINIMUM_NUMBER_OF_CALLS;
    private boolean automaticTransitionFromOpenToHalfOpenEnabled = false;
}
