package com.norsys.fr.springdataresttest.repository;

import com.norsys.fr.springdataresttest.entity.Furniture;
import com.norsys.fr.springdataresttest.entity.FurnitureExcerpt;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = FurnitureExcerpt.class)
public interface FurnituresRepository extends PagingAndSortingRepository<Furniture, String> {
}
