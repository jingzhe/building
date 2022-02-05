package com.jingzhe.building.service;

import com.jingzhe.building.api.model.BuildingDataRequest;
import com.jingzhe.building.api.model.BuildingDataResponse;
import com.jingzhe.building.model.BuildingInfo;
import com.jingzhe.building.exception.BuildingNotFoundException;
import com.jingzhe.building.respository.BuildingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuildingService {
    private final GeoService geoService;
    private final  BuildingRepository buildingRepository;

    public Flux<BuildingDataResponse> create(List<BuildingDataRequest> buildings) {
        List<Mono<BuildingInfo>> buildingMonos = buildings.stream()
                .map(BuildingInfo::fromRequest)
                .map(geoService::updateGeoData).toList();

        return Flux.fromIterable(buildingMonos)
                .flatMap(buildingRepository::saveAll)
                .map(BuildingDataResponse::fromBuildingInfo);
    }

    public Flux<BuildingDataResponse> search(BuildingInfo buildingInfo, int limit) {
        return buildingRepository.findAll(Example.of(buildingInfo))
                .map(BuildingDataResponse::fromBuildingInfo)
                .take(limit);

    }

    public Mono<BuildingDataResponse> update(String id, BuildingDataRequest building) {
        return buildingRepository.findById(id)
                .flatMap(geoService::updateGeoData)
                .map(BuildingDataResponse::fromBuildingInfo)
                .switchIfEmpty(Mono.error(new BuildingNotFoundException("Updating building failed, building not found:" + id)));
    }

    public Mono<Void> delete(String id) {
        return buildingRepository.deleteById(id);
    }
}
