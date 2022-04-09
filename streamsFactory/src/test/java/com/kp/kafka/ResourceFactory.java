package com.kp.kafka;

import com.kp.kafka.model.pizzaOrder.RecipientAddress;
import com.kp.kafka.model.pizzaOrder.RecipientDetails;
import com.kp.kafka.model.pizzaOrder.PizzaDetails;
import com.kp.kafka.model.pizzaOrder.PizzaOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResourceFactory {
/*
    public static ApplicationEnvironment applicationEnvironment(){
        ApplicationEnvironment applicationEnvironment= mock(ApplicationEnvironment.class);
        when(applicationEnvironment.getPizzaInTopic()).thenReturn("in_pizza_topic");
        when(applicationEnvironment.getBigPizzaTopic()).thenReturn("bigPizzaTopic");
        when(applicationEnvironment.getMediumPizzaTopic()).thenReturn("mediumPizzaTopic");
        when(applicationEnvironment.getSmallPizzaTopic()).thenReturn("smallPizzaTopic");
        when(applicationEnvironment.getPineapplePizzaTopic()).thenReturn("pineapplePizzaTopic");
        when(applicationEnvironment.getBadPizzaTopic()).thenReturn("badPizzaTopic");
        return applicationEnvironment;
    }*/
    public static PizzaOrder getSizedPizzaOrder(String size){
        PizzaOrder pizzaOrder = new PizzaOrder();

        List<String> ingredientList = new ArrayList<>();
        ingredientList.add("Salami");
        ingredientList.add("Mozzarella");

        RecipientAddress recipientAddress = new RecipientAddress();
        recipientAddress.setCity("Warsaw");
        recipientAddress.setStreet("Al. Jerozolimskie");
        recipientAddress.setNumber("55");

        RecipientDetails recipientDetails= new RecipientDetails();
        recipientDetails.setFirstName("John");
        recipientDetails.setLastName("Doe");
        recipientDetails.setPhoneNumber("123456789");

        PizzaDetails pizzaDetails = new PizzaDetails();
        pizzaDetails.setPizzaSize(size);
        pizzaDetails.setDoughType("THICK");
        pizzaDetails.setIngredients(ingredientList);

        recipientDetails.setRecipientAddress(recipientAddress);
        pizzaOrder.setPizzaOrderId(UUID.randomUUID().toString());
        pizzaOrder.setRecipientDetails(recipientDetails);
        pizzaOrder.setPizzaDetails(pizzaDetails);

        return pizzaOrder;
    }
    public static PizzaOrder getPineapplePizzaOrder(){
        PizzaOrder pizzaOrder = new PizzaOrder();

        List<String> ingredientList = new ArrayList<>();
        ingredientList.add("Pineapple");
        ingredientList.add("Salami");
        ingredientList.add("Mozzarella");

        RecipientAddress recipientAddress = new RecipientAddress();
        recipientAddress.setCity("Warsaw");
        recipientAddress.setStreet("Al. Jerozolimskie");
        recipientAddress.setNumber("55");

        RecipientDetails recipientDetails= new RecipientDetails();
        recipientDetails.setFirstName("John");
        recipientDetails.setLastName("Doe");
        recipientDetails.setPhoneNumber("123456789");

        PizzaDetails pizzaDetails = new PizzaDetails();
        pizzaDetails.setPizzaSize("BIG");
        pizzaDetails.setDoughType("THICK");
        pizzaDetails.setIngredients(ingredientList);

        recipientDetails.setRecipientAddress(recipientAddress);
        pizzaOrder.setPizzaOrderId(UUID.randomUUID().toString());
        pizzaOrder.setRecipientDetails(recipientDetails);
        pizzaOrder.setPizzaDetails(pizzaDetails);

        return pizzaOrder;
    }

    public static PizzaOrder getPizzaOrder(boolean isPineappleOn) {
        PizzaOrder pizzaOrder = new PizzaOrder();

        List<String> ingredientList = new ArrayList<>();
        if (isPineappleOn==true) {
            ingredientList.add("Pineapple");
        }
        ingredientList.add("Salami");
        ingredientList.add("Mozzarella");


        RecipientAddress recipientAddress = new RecipientAddress();
        recipientAddress.setCity("Warsaw");
        recipientAddress.setStreet("Al. Jerozolimskie");
        recipientAddress.setNumber("55");

        RecipientDetails recipientDetails= new RecipientDetails();
        recipientDetails.setFirstName("John");
        recipientDetails.setLastName("Doe");
        recipientDetails.setPhoneNumber("123456789");

        PizzaDetails pizzaDetails = new PizzaDetails();
        pizzaDetails.setPizzaSize("BIG");
        pizzaDetails.setDoughType("THICK");
        pizzaDetails.setIngredients(ingredientList);

        recipientDetails.setRecipientAddress(recipientAddress);
        pizzaOrder.setPizzaOrderId(UUID.randomUUID().toString());
        pizzaOrder.setRecipientDetails(recipientDetails);
        pizzaOrder.setPizzaDetails(pizzaDetails);
        return pizzaOrder;

    }
}
