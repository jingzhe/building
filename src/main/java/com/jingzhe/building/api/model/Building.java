package com.jingzhe.building.api.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;
import lombok.With;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

@Value
@With
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Building implements Serializable {
    @Serial
    private static final long serialVersionUID = 7001955564540267263L;

    String id;

    @NotBlank
    String name;

    @NotBlank
    String street;

    @Min(1)
    int number;

    @NotBlank
    String postCode;

    @NotBlank
    String city;

    @NotBlank
    String country;

    String description;

    double longitude;

    double latitude;

    public static String getGeoQuery(Building building) {
        return building.getStreet() +
                " " +
                building.getNumber() +
                "," +
                building.getCity() +
                " " +
                building.getPostCode() +
                "," +
                building.getCountry();
    }
}
