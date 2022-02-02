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
    List<Feature> features;

    public double getLongitude() {
        return getCoordinates().get(0);
    }

    public double getLatitude() {
        return features.get(0).geometry.coordinates.get(1);
    }

    private List<Double> getCoordinates() {
        return features.get(0).geometry.coordinates;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Feature {
        @NotNull
        Geometry geometry;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Geometry {
        @Size(min = 2, max = 2, message = "Latitude and Longitude")
        List<Double> coordinates;
    }
}
