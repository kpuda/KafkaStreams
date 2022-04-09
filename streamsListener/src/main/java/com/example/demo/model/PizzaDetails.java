package com.example.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class PizzaDetails {

    private String pizzaSize;
    private String doughType;
    private List<String> ingredients;
}
