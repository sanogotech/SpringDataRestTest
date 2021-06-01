package com.norsys.fr.springdataresttest.entity;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "excerpt", types = { Furniture.class })
public interface FurnitureExcerpt {
    String getId();
    String getName();
    int getPrice();
}
