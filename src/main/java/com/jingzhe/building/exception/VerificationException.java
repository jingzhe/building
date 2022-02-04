package com.jingzhe.building.exception;

import java.io.Serial;

public class VerificationException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -5927506272586879457L;

    public VerificationException(String msg) {
        super(msg);
    }
}
