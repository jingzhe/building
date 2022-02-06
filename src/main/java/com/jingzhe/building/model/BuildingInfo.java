package com.jingzhe.building.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jingzhe.building.api.model.BuildingDataRequest;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serial;
import java.io.Serializable;

@Slf4j
@Value
@With
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BuildingInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = -5918748170178612227L;

    String id;

    @Indexed
    String name;

    @Indexed
    String street;

    @Indexed
    Integer number;

    @Indexed
    String postCode;

    @Indexed
    String city;

    @Indexed
    String country;

    String description;

    Double longitude;

    Double latitude;

    public static String getGeoQuery(BuildingInfo building) {
        String query = building.getStreet() +
                " " +
                building.getNumber() +
                "," +
                building.getCity() +
                " " +
                building.getPostCode() +
                "," +
                building.getCountry();
        log.info("Query={}", query);
        return query;
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
