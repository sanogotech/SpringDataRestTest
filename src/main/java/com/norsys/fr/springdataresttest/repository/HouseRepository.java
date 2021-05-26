package com.norsys.fr.springdataresttest.repository;

import com.norsys.fr.springdataresttest.entity.House;
import com.norsys.fr.springdataresttest.entity.HouseProjection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

public interface HouseRepository extends CrudRepository<House, String> {

    public List<House> findByName(String name);

}
