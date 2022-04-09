package com.kp.kafka.service;

import com.kp.kafka.model.pizzaOrder.PizzaOrder;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class StreamsController {

    KafkaProducer kafkaProducerService;

    @PostMapping("/sendPizza")
    public String sendPizza(@RequestBody PizzaOrder order) throws ExecutionException, InterruptedException {
        kafkaProducerService.send(order);
        return "pizza order sent!";
    }

    @PostMapping("/sendString")
    public String sendString(@RequestParam("message") String message) throws ExecutionException, InterruptedException {
        kafkaProducerService.send(message, "inputTopic");
        return "message sent to inputTopic!";
    }

    @PostMapping("/sendStringFromDifferentTopic")
    public String sendAnother(@RequestParam("message") String message) throws ExecutionException, InterruptedException {
        kafkaProducerService.send(message, "inputTopic2");
        return "message sent to inputTopic2!";
    }

}
