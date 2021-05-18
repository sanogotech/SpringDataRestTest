package com.norsys.fr.springdataresttest.repository;

import com.norsys.fr.springdataresttest.entity.House;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HouseRepository extends CrudRepository<House, String> {

    public List<House> findByName(String name);

}
