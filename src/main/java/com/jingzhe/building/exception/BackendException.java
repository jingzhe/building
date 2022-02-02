package com.jingzhe.building.exception;

import org.springframework.web.reactive.function.client.WebClientResponseException;

public abstract class BackendException extends RuntimeException{
    public BackendException(WebClientResponseException cause) {
        super(cause);
    }

    public WebClientResponseException getWebClientResponseException() {
        return (WebClientResponseException) getCause();
    }
}
