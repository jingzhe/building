package com.jingzhe.building.exception;

import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.Serial;

public class Backend4xxException extends BackendException{
    @Serial
    private static final long serialVersionUID = -6387680225912978379L;

    public Backend4xxException(WebClientResponseException cause) {
        super(cause);
    }
}
