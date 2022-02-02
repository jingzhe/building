package com.jingzhe.building.service;

import com.jingzhe.building.api.model.Building;
import com.jingzhe.building.respository.BuildingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuildingService {

    private final GeoService geoService;
    private final BuildingRepository buildingRepository;


    public Flux<Building> create(List<Building> buildings) {
        List<Mono<Building>> buildingMonos = buildings.stream()
                .map(geoService::updateGeoData).toList();

        return Flux.fromIterable(buildingMonos)
                .flatMap(buildingRepository::saveAll);
    }
}
