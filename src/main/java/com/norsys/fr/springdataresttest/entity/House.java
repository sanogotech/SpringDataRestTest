package com.norsys.fr.springdataresttest.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
public class House {

    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id
    String id;

    @Version
    Long version;

    String name;
    
    @OneToOne
    Address address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "house_id")
    List<Inhabitant> inhabitants;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "house_id")
    List<Furniture> furnitures;

}
