package com.norsys.fr.springdataresttest.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Furniture {

    @GeneratedValue @Id
    String id;
    String name;
    int price;

    @ManyToOne(cascade = CascadeType.ALL)
    House house;
}
