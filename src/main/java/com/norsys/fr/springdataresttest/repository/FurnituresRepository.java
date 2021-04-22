package com.norsys.fr.springdataresttest.repository;

import com.norsys.fr.springdataresttest.entity.Furniture;
import org.springframework.data.repository.CrudRepository;

public interface FurnituresRepository extends CrudRepository<Furniture, String> {
}
