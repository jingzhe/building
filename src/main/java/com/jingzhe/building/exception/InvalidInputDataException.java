package com.jingzhe.building.exception;

import java.io.Serial;

public class InvalidInputDataException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 91943287740771321L;

    public InvalidInputDataException(String msg) {
        super(msg);
    }
}
