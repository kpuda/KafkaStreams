package com.kp.kafka.model.pizzaOrder;

import lombok.Data;

@Data
public class RecipientAddress {

    private String city;
    private String street;
    private String number;
    private String apartamentNumber;
}
