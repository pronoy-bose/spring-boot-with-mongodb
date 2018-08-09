package com.osi.vehicle_access.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.osi.vehicle_access.model.Person;

@Repository
public interface PersonsRepository extends MongoRepository<Person, String> {

	@Query("{'vehicle_numbers.number': ?0}")
	Optional<Person> findByNumber(String number);
}
