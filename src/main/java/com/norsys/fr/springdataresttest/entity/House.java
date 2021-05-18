package com.norsys.fr.springdataresttest.entity;

import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Data
@Entity
public class House {

    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id
    String id;

    @Version
    Long version;

    @LastModifiedDate
    Date date;

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
