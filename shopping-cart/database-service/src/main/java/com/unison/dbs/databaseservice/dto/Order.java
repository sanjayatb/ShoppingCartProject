package com.unison.dbs.databaseservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Order implements Serializable {
    private String name;
    private double price;
    private double quantity;
}
