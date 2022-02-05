package com.jingzhe.building.exception;

import java.io.Serial;

public class GeoIntegrationException extends RuntimeException{


    @Serial
    private static final long serialVersionUID = 1675229143847135011L;

    public GeoIntegrationException(String msg) {
        super(msg);
    }

    public GeoIntegrationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
