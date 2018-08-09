package com.osi.vehicle_access.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.osi.vehicle_access.model.Building;

@Repository
public interface BuildingRepository extends MongoRepository<Building, String> {

	List<Building> findAllByName(String name);
}
