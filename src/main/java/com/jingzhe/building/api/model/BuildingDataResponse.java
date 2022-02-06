package com.jingzhe.building.api.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jingzhe.building.model.BuildingInfo;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BuildingDataResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 8509135620734162066L;

    @NotBlank
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

    double longitude;

    double latitude;

    String description;

    public static BuildingDataResponse fromBuildingInfo(BuildingInfo buildingInfo) {
        return BuildingDataResponse.builder()
                .id(buildingInfo.getId())
                .name(buildingInfo.getName())
                .street(buildingInfo.getStreet())
                .number(buildingInfo.getNumber())
                .postCode(buildingInfo.getPostCode())
                .city(buildingInfo.getCity())
                .country(buildingInfo.getCountry())
                .description(buildingInfo.getDescription())
                .latitude(buildingInfo.getLatitude())
                .longitude(buildingInfo.getLongitude())
                .build();
    }
}
