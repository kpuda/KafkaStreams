package com.kp.kafka.service;

import com.kp.kafka.model.incident.Incident;
import com.kp.kafka.model.pizzaOrder.PizzaOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class PizzaService {

    public boolean isPineappleOnPizza(PizzaOrder pizza){
        List<String> ingredients = pizza.getPizzaDetails().getIngredients();
        Optional<String> pineapple = ingredients.stream().filter(ingredient -> ingredient.equalsIgnoreCase("pineapple")).findAny();
        if (pineapple.isPresent()){
            log.warn("Pineapple on pizza detected! Blocking the customer account.");
            return true;
        } else
        return false;
    }

    public String pizzaSize(PizzaOrder pizza){
        String pizzaSize = pizza.getPizzaDetails().getPizzaSize().toLowerCase();
        switch (pizzaSize){
            case "big":
                return "big";
            case "medium":
                return "medium";
            case "small":
                return "small";
            default:
                return "unknown";
        }
    }

    public Incident mapToIncident(PizzaOrder pizza){
        return Incident.builder()
                .firstName(pizza.getRecipientDetails().getFirstName())
                .lastName(pizza.getRecipientDetails().getLastName())
                .phoneNumber(pizza.getRecipientDetails().getPhoneNumber())
                .city(pizza.getRecipientDetails().getRecipientAddress().getCity())
                .street(pizza.getRecipientDetails().getRecipientAddress().getStreet())
                .number(pizza.getRecipientDetails().getRecipientAddress().getNumber())
                .apartamentNumber(pizza.getRecipientDetails().getRecipientAddress().getApartamentNumber())
                .build();
    }

    public String mapToOrder(PizzaOrder pizza){
        List<String> ingredients = pizza.getPizzaDetails().getIngredients();
        Collections.sort(ingredients);
        pizza.getPizzaDetails().setIngredients(ingredients);
        return "New pizza order received:\n"+
                "RecipientPhoneNumber("+pizza.getRecipientDetails().getPhoneNumber()+")\n"+
                pizza.getRecipientDetails().getRecipientAddress()+"\n"+
                pizza.getPizzaDetails();
    }
}
