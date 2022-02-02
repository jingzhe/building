package com.jingzhe.building.controller;

import com.jingzhe.building.api.BuildingEndpoint;
import com.jingzhe.building.api.model.Building;
import com.jingzhe.building.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BuildingController implements BuildingEndpoint {
    
    private final BuildingService buildingService;


    @Override
    public Flux<Building> create(List<Building> buildings) {
        return buildingService.create(buildings);
    }

    @Override
    public Mono<Building> update(String id, Building building) {
        return null;
    }

    @Override
    public Mono<Void> delete(String id) {
        return null;
    }
}
