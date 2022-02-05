package com.jingzhe.building.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jingzhe.building.api.model.BuildingDataRequest;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.io.Serial;
import java.io.Serializable;

@Value
@With
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BuildingInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = -5918748170178612227L;

    String id;

    String name;

    String street;

    Integer number;

    String postCode;

    String city;

    String country;

    String description;

    Double longitude;

    Double latitude;

    public static String getGeoQuery(BuildingInfo building) {
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

    public static BuildingInfo fromRequest(BuildingDataRequest request) {
        return BuildingInfo.builder()
                .name(request.getName())
                .street(request.getStreet())
                .number(request.getNumber())
                .postCode(request.getPostCode())
                .city(request.getCity())
                .country(request.getCountry())
                .description(request.getDescription())
                .build();
    }
}
