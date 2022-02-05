package com.jingzhe.building.controller;

import com.jingzhe.building.api.BuildingEndpoint;
import com.jingzhe.building.api.model.BuildingDataRequest;
import com.jingzhe.building.api.model.BuildingDataResponse;
import com.jingzhe.building.model.BuildingInfo;
import com.jingzhe.building.service.BuildingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.jingzhe.building.utils.BuildingUtils.getOrDefaultInt;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BuildingController implements BuildingEndpoint {

    private final BuildingService buildingService;

    @Override
    public Flux<BuildingDataResponse> create(List<BuildingDataRequest> buildings) {
        return buildingService.create(buildings);
    }

    @Override
    public Flux<BuildingDataResponse> search(String name, String street, Integer number, String postCode, String city, String country, Integer limit, Integer offset) {
        BuildingInfo buildingInfo = BuildingInfo.builder()
                .name(name)
                .street(street)
                .number(number)
                .postCode(postCode)
                .city(city)
                .country(country)
                .build();
        return buildingService.search(buildingInfo, getOrDefaultInt(limit, 100) , getOrDefaultInt(offset, 0));
    }

    @Override
    public Mono<BuildingDataResponse> update(String id, BuildingDataRequest building) {
        return buildingService.update(id, building);
    }

    @Override
    public Mono<Void> delete(String id) {
        return buildingService.delete(id);
    }


}
