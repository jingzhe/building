package com.jingzhe.building.controller;

import com.jingzhe.building.api.BuildingEndpoint;
import com.jingzhe.building.api.model.BuildingDataRequest;
import com.jingzhe.building.api.model.BuildingDataResponse;
import com.jingzhe.building.config.BuildingProperties;
import com.jingzhe.building.model.BuildingInfo;
import com.jingzhe.building.service.BuildingService;
import com.jingzhe.building.utils.BuildingUtils;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.jingzhe.building.utils.BuildingUtils.empty2Null;
import static com.jingzhe.building.utils.BuildingUtils.getOrDefaultInt;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BuildingController implements BuildingEndpoint {

    private final BuildingService buildingService;
    private final BuildingProperties buildingProperties;

    @Override
    public Flux<BuildingDataResponse> create(List<BuildingDataRequest> buildings) {
        return buildingService.create(buildings);
    }

    @Override
    public Flux<BuildingDataResponse> search(String name, String street, Integer number, String postCode,
                                             String city, String country, Integer limit, Integer offset,
                                             String sortBy, String order) {
        BuildingInfo buildingInfo = BuildingInfo.builder()
                .name(empty2Null(name))
                .street(empty2Null(street))
                .number(number)
                .postCode(empty2Null(postCode))
                .city(empty2Null(city))
                .country(empty2Null(country))
                .build();
        BuildingUtils.checkSortByName(buildingInfo, sortBy);

        return buildingService.search(buildingInfo, getOrDefaultInt(limit, buildingProperties.getDefaultLimit()),
                getOrDefaultInt(offset, 0), sortBy, order);
    }

    @Override
    public Mono<BuildingDataResponse> getBuilding(String id) {
        return buildingService.getBuilding(id);
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
