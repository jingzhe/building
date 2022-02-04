package com.jingzhe.building.exception;

import java.io.Serial;

public class BuildingServiceException extends RuntimeException{


    @Serial
    private static final long serialVersionUID = 5583365247184506468L;

    public BuildingServiceException(String msg) {
        super(msg);
    }

    public BuildingServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
