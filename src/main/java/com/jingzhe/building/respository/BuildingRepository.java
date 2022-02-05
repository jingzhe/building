package com.jingzhe.building.respository;

import com.jingzhe.building.model.BuildingInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BuildingRepository extends ReactiveMongoRepository<BuildingInfo, String> {

}
