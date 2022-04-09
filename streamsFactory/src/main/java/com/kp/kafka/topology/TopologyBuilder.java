package com.kp.kafka.topology;

import com.kp.kafka.model.incident.Incident;
import com.kp.kafka.model.pizzaOrder.PizzaOrder;
import com.kp.kafka.serdes.CustomSerde;
import com.kp.kafka.service.PizzaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class TopologyBuilder {

    PizzaService pizzaService;

    public Topology createTopology() {

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        String inputTopic = "inputTopic";
        String inputTopic2 = "inputTopic2";
        String validMessagesTopic = "validMessages";
        String invalidMessagesTopic = "invalidMessages";
        String pizzaInTopic = "in_pizza_topic";
        String pizzaOrderTopic = "pizzaOrder";
        String incidentTopic = "pineapplePizzaTopic";

        Collection<String> topicCollection= new ArrayList<>();
        topicCollection.add(inputTopic);
        topicCollection.add(inputTopic2);

        Map<String, KStream<String, String>> wordsMap = streamsBuilder.<String, String>stream(topicCollection)
                .peek((key, message) -> log.info("new incoming message: {}", message))
                .split(Named.as("messages-"))
                .branch((key, message) -> message.length() > 5, Branched.as("valid"))
                .defaultBranch(Branched.as("invalid"));
        wordsMap.get("messages-valid").mapValues((key,message)-> message.toUpperCase()).to(validMessagesTopic);
        wordsMap.get("messages-invalid").mapValues((key,message)-> message.toLowerCase()).to(invalidMessagesTopic);

        CustomSerde<PizzaOrder> pizzaSerde = new CustomSerde<>(PizzaOrder.class);
        CustomSerde<Incident> incSerde= new CustomSerde<>(Incident.class);
        Map<String, KStream<String, PizzaOrder>> pizzaMap = streamsBuilder.<String, PizzaOrder>stream(pizzaInTopic, Consumed.with(Serdes.String(), pizzaSerde))
                .peek((key, order) -> log.info("New order: {}", order))
                .split(Named.as("pizza-"))
                .branch((key, order) -> pizzaService.isPineappleOnPizza(order), Branched.as("incident"))
                .branch((key, order) -> pizzaService.pizzaSize(order).equalsIgnoreCase("big"), Branched.as("big"))
                .branch((key, order) -> pizzaService.pizzaSize(order).equalsIgnoreCase("medium"), Branched.as("mediuum"))
                .defaultBranch(Branched.as("small"));
        pizzaMap.get("pizza-incident").mapValues((key,order)-> pizzaService.mapToIncident(order)).to(incidentTopic,Produced.with(Serdes.String(),incSerde));
        pizzaMap.get("pizza-big").mapValues((key,order)->pizzaService.mapToOrder(order)).to(pizzaOrderTopic);


        return streamsBuilder.build();
    }
}
