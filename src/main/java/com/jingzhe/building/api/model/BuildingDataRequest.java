package com.jingzhe.building.api.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BuildingDataRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 7001955564540267263L;

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
}
