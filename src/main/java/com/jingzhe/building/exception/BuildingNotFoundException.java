package com.jingzhe.building.exception;

import java.io.Serial;

public class BuildingNotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -7229106778121443120L;

    public BuildingNotFoundException(String msg) {
        super(msg);
    }
}
