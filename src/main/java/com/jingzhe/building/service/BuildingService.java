package com.jingzhe.building.service;

import com.jingzhe.building.api.model.Building;
import com.jingzhe.building.exception.BuildingNotFoundException;
import com.jingzhe.building.respository.BuildingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public record BuildingService(GeoService geoService, BuildingRepository buildingRepository) {

    public Flux<Building> create(List<Building> buildings) {
        List<Mono<Building>> buildingMonos = buildings.stream()
                .map(geoService::updateGeoData).toList();

        return Flux.fromIterable(buildingMonos)
                .flatMap(buildingRepository::saveAll);
    }

    public Mono<Building> update(String id, Building building) {
        return buildingRepository.findById(id)
                .map(buildingFromDB -> building.withId(id))
                .flatMap(geoService::updateGeoData)
                .switchIfEmpty(Mono.error(new BuildingNotFoundException("Updating building failed, building not found:" + id)));
    }

    public Mono<Void> delete(String id) {
        return buildingRepository.deleteById(id);
    }
}
