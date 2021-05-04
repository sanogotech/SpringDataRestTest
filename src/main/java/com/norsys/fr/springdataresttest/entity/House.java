package com.norsys.fr.springdataresttest.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
public class House {

    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id
    String id;

    String name;
    
    @OneToOne
    Address address;

    @OneToMany(cascade = CascadeType.ALL)
    List<Inhabitant> inhabitants;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "house_id")
    List<Furniture> furnitures;

}
