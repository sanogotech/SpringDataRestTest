package com.norsys.fr.springdataresttest.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;


@Data
@Entity
public class House {

    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id
    String id;

    String name;

    @OneToMany(cascade = CascadeType.ALL)
    Set<Inhabitant> inhabitants;

    @OneToMany(cascade = CascadeType.ALL)
    Set<Furniture> furnitures;

}
