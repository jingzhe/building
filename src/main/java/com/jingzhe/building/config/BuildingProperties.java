package com.jingzhe.building.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URL;

@Validated
@Data
@ConfigurationProperties(prefix = "building")
public class BuildingProperties {
    @NotNull
    URL geoBaseUrl;

    @NotBlank
    String geoPath;

    @NotNull
    URL authBaseUrl;

    @NotBlank
    String authPath;

    @NotBlank
    String geoApiKey;

    CircuitProperties geoCircuit;

    CircuitProperties authCircuit;

}
