package com.unison.dbs.databaseservice.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="orders")
@Getter @Setter
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name="price", columnDefinition="Decimal(10,2) default '0.00'")
    private double price;
    @Column(name = "qty")
    private double quantity;
}
