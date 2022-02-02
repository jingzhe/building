package com.jingzhe.building.respository;

import com.jingzhe.building.api.model.Building;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BuildingRepository extends ReactiveMongoRepository<Building, String> {

}
