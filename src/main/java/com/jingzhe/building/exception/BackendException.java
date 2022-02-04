package com.jingzhe.building.exception;

import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.Serial;

public abstract class BackendException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -1363332303303108293L;

    public BackendException(WebClientResponseException cause) {
        super(cause);
    }

    public WebClientResponseException getWebClientResponseException() {
        return (WebClientResponseException) getCause();
    }
}
