package com.jingzhe.building;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@UtilityClass
public class Utils {

    public static double setPrecision(double value) {
        return BigDecimal.valueOf(value)
                .setScale(5, RoundingMode.HALF_UP)
                .doubleValue();
    }

}
