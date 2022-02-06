package com.jingzhe.building.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoData {
    @NotNull
    @Size(min = 1)
    private List<Feature> features;

    public double getLongitude() {
        return features.get(0).properties.lon;
    }

    public double getLatitude() {
        return features.get(0).properties.lat;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Feature {
        @NotNull
        private Properties properties;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Properties {
        @NotNull
        private Double lon;
        @NotNull
        private Double lat;
    }
}
