package com.jingzhe.building.service;

import com.jingzhe.building.api.model.BuildingDataRequest;
import com.jingzhe.building.api.model.BuildingDataResponse;
import com.jingzhe.building.model.BuildingInfo;
import com.jingzhe.building.exception.BuildingNotFoundException;
import com.jingzhe.building.respository.BuildingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
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
                .doOnNext(info -> log.info(String.valueOf(info.getLatitude())))
                .map(BuildingDataResponse::fromBuildingInfo)
                .doOnNext(res -> log.info(String.valueOf(res.getLatitude())));
    }

    public Flux<BuildingDataResponse> search(BuildingInfo buildingInfo, int limit, int offset, String sortBy, String order) {
        Sort.Direction direction = order == null ? Sort.Direction.ASC : Sort.Direction.fromString(order);
        Sort sort = StringUtils.isBlank(sortBy) ? null : Sort.by(direction, sortBy);

        Flux<BuildingInfo> flux;
        if (sort != null) {
            flux = buildingRepository.findAll(Example.of(buildingInfo), sort);
        } else {
            flux = buildingRepository.findAll(Example.of(buildingInfo));
        }
        return flux
                .map(BuildingDataResponse::fromBuildingInfo)
                .skip(offset)
                .take(limit);

    }

    public Mono<BuildingDataResponse> getBuilding(String id) {
        return buildingRepository.findById(id)
                .map(BuildingDataResponse::fromBuildingInfo)
                .switchIfEmpty(Mono.error(new BuildingNotFoundException("Updating building failed, building not found:" + id)));
    }

    public Mono<BuildingDataResponse> update(String id, BuildingDataRequest building) {
        return buildingRepository.findById(id)
                .map(orig -> BuildingInfo.fromRequest(building).withId(orig.getId()))
                .flatMap(geoService::updateGeoData)
                .flatMap(buildingRepository::save)
                .map(BuildingDataResponse::fromBuildingInfo)
                .switchIfEmpty(Mono.error(new BuildingNotFoundException("Updating building failed, building not found:" + id)));
    }

    public Mono<Void> delete(String id) {
        return buildingRepository.deleteById(id);
    }
}
