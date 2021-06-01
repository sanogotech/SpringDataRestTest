package com.norsys.fr.springdataresttest.repository;

import com.norsys.fr.springdataresttest.entity.House;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface HouseRepository extends CrudRepository<House, String> {

	@Query("SELECT h FROM House h JOIN h.inhabitants i WHERE i.name = :name")
	List<House> findHomeByInhabitant(String name);
}
