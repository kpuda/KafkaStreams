package com.kp.kafka.model.pizzaOrder;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PizzaOrder {

    private String pizzaOrderId;
    private RecipientDetails recipientDetails;
    private PizzaDetails pizzaDetails;
}
