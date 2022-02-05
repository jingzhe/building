package com.jingzhe.building.service;

import com.jingzhe.building.model.BuildingInfo;
import com.jingzhe.building.integration.GeoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GeoService {

    private final GeoClient geoClient;

    public Mono<BuildingInfo> updateGeoData(BuildingInfo building) {
        return geoClient.getGeoInfo(building)
                .map(geoData -> building.withLatitude(geoData.getLatitude()).withLongitude(geoData.getLongitude()));
    }
}
