package com.jingzhe.building.api.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jingzhe.building.model.BuildingInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import javax.validation.constraints.NotBlank;
import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BuildingDataResponse extends BuildingDataRequest {

    @Serial
    private static final long serialVersionUID = 8509135620734162066L;

    @NotBlank
    String id;

    @NotBlank
    double longitude;

    @NotBlank
    double latitude;

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
