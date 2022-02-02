package com.jingzhe.building.exception;

import org.springframework.web.reactive.function.client.WebClientResponseException;

public class Backend4xxException extends BackendException{
    public Backend4xxException(WebClientResponseException cause) {
        super(cause);
    }
}
