package com.kp.kafka.service;

import com.kp.kafka.model.pizzaOrder.PizzaOrder;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final ProducerFactory<String, PizzaOrder> pizzaProducerFactory;
    private final ProducerFactory<String, String> producerFactory;

    public void send(PizzaOrder message) throws ExecutionException, InterruptedException {
        ProducerRecord<String, PizzaOrder> record =
                new ProducerRecord<>("in_pizza_topic", UUID.randomUUID().toString(), message);

        try (Producer<String, PizzaOrder> producer = pizzaProducerFactory.createProducer()) {
            producer.send(record).get();
        }
    }

    public void send(String message,String topic) throws ExecutionException, InterruptedException {
        ProducerRecord<String, String> record =
                new ProducerRecord<>(topic, UUID.randomUUID().toString(), message);

        try (Producer<String, String> producer = producerFactory.createProducer()) {
            producer.send(record).get();
        }
    }


}
