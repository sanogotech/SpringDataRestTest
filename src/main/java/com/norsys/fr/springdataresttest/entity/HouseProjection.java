package com.norsys.fr.springdataresttest.entity;

import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name = "test", types = { House.class })
public interface HouseProjection {
    String getName();
    List<Furniture> getFurnitures();
}
