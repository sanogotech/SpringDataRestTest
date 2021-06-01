package com.norsys.fr.springdataresttest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.norsys.fr.springdataresttest.entity.House;
import com.norsys.fr.springdataresttest.repository.HouseRepository;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RepositoryRestController
public class InhabitantController {
	
    private final HouseRepository houseRepository;

    @Autowired
    public InhabitantController(HouseRepository repo) { 
        houseRepository = repo;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/houses/inhabitants") 
    public @ResponseBody ResponseEntity<?> searchInhabitants(@RequestParam String name) {
        List<House> inhabitants = houseRepository.findHomeByInhabitant(name); 

        CollectionModel<House> resources = CollectionModel.of(inhabitants); 

        resources.add(linkTo(methodOn(InhabitantController.class).searchInhabitants(name)).withSelfRel()); 

        return ResponseEntity.ok(resources); 
    }

}
