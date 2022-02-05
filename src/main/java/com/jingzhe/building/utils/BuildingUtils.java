package com.jingzhe.building.utils;

import com.jingzhe.building.exception.InvalidInputDataException;
import com.jingzhe.building.model.BuildingInfo;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.stream.Stream;

public class BuildingUtils {

    public static int getOrDefaultInt(Integer number, int value){
        if (number == null) {
            return value;
        }
        return number;
    }

    public static void checkSortByName(BuildingInfo buildingInfo, String name) {
        if (StringUtils.isBlank(name)) {
            return;
        }

        Stream.of(buildingInfo.getClass().getDeclaredFields())
                .filter(field -> name.replace("_", "").equalsIgnoreCase(field.getName()))
                .findFirst()
                .orElseThrow(() -> new InvalidInputDataException("Invalid sort by:" + name));
    }
}