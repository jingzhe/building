package com.jingzhe.building.service;

import com.jingzhe.building.api.model.Building;
import com.jingzhe.building.integration.GeoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GeoService {

    private final GeoClient geoClient;

    public Mono<Building> updateGeoData(Building building) {
        return geoClient.getGeoInfo(building)
                .map(geoData -> building.withLatitude(geoData.getLatitude()).withLongitude(geoData.getLongitude()));
    }
}
