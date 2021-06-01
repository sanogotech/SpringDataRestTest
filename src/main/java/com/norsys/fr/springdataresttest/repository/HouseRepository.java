package com.norsys.fr.springdataresttest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.norsys.fr.springdataresttest.entity.House;

public interface HouseRepository extends CrudRepository<House, String> {

	@Query("SELECT h FROM House h JOIN h.inhabitants i WHERE i.name = :name")
	public List<House> findHomeByInhabitant(String name);

    public List<House> findByName(String name);

}
