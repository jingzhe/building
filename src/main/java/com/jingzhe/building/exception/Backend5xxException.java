package com.jingzhe.building.exception;

import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.Serial;

public class Backend5xxException extends BackendException{
    @Serial
    private static final long serialVersionUID = -7211571123538922321L;

    public Backend5xxException(WebClientResponseException cause) {
        super(cause);
    }
}
