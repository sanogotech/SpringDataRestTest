package com.norsys.fr.springdataresttest.entity;

import com.norsys.fr.springdataresttest.entity.Furniture;
import com.norsys.fr.springdataresttest.entity.House;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name = "test", types = { House.class })
interface HouseProjection {
    String getName();
    List<Furniture> getFurnitures();
}
