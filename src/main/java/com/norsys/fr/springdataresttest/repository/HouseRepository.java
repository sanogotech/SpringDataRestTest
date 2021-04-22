package com.norsys.fr.springdataresttest.repository;

import com.norsys.fr.springdataresttest.entity.House;
import org.springframework.data.repository.CrudRepository;

public interface HouseRepository extends CrudRepository<House, String> {
}
