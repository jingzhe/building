package com.jingzhe.building.exception;

import org.springframework.web.reactive.function.client.WebClientResponseException;

public class Backend5xxException extends BackendException{
    public Backend5xxException(WebClientResponseException cause) {
        super(cause);
    }
}
