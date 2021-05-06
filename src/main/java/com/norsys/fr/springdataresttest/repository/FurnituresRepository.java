package com.norsys.fr.springdataresttest.repository;

import com.norsys.fr.springdataresttest.entity.Furniture;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FurnituresRepository extends PagingAndSortingRepository<Furniture, String> {
}
