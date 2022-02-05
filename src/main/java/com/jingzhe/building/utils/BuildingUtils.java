package com.jingzhe.building.utils;

public class BuildingUtils {

    public static int getOrDefaultInt(Integer number, int value){
        if (number == null) {
            return value;
        }
        return number;
    }
}