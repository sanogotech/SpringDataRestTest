package com.norsys.fr.springdataresttest.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Inhabitant {

    @GeneratedValue @Id
    String id;
    String name;
    int age;

    @ManyToOne(cascade = CascadeType.ALL)
    House house;
}
