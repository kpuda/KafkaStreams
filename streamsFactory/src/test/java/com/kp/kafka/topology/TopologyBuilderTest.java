package com.kp.kafka.topology;

import com.kp.kafka.ResourceFactory;
import com.kp.kafka.model.incident.Incident;
import com.kp.kafka.model.pizzaOrder.PizzaOrder;
import com.kp.kafka.serdes.CustomSerde;
import com.kp.kafka.service.PizzaService;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.test.TestRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.util.Properties;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OutputCaptureExtension.class)
class TopologyBuilderTest {

    String inputKafkaTopic = "inputTopic";
    String inputKafkaTopic2 = "inputTopic2";
    String validMessagesKafkaTopic = "validMessages";
    String invalidMessagesKafkaTopic = "invalidMessages";
    String pizzaInKafkaTopic = "in_pizza_topic";
    String pizzaOrderKafkaTopic = "pizzaOrder";
    String incidentKafkaTopic = "pineapplePizzaTopic";

    private TopologyTestDriver topologyTestDriver;

    private TestInputTopic<String, String> inputTopic;
    private TestInputTopic<String, String> inputTopic2;
    private TestInputTopic<String, PizzaOrder> inputPizza;


    private TestOutputTopic<String, String> validMessage;
    private TestOutputTopic<String, String> invalidMessage;
    private TestOutputTopic<String, String> pizzaOrder;
    private TestOutputTopic<String, Incident> incident;

    PizzaService pizzaService = new PizzaService();

    CustomSerde<Incident> incSerde = new CustomSerde<>(Incident.class);
    CustomSerde<PizzaOrder> pizzaSerde = new CustomSerde<>(PizzaOrder.class);
    @BeforeEach
    void setUp() {
        TopologyBuilder topologyBuilder = new TopologyBuilder(pizzaService);
        Topology topology = topologyBuilder.createTopology();
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "test");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        topologyTestDriver = new TopologyTestDriver(topology, properties);
        inputTopic = topologyTestDriver.createInputTopic(inputKafkaTopic, Serdes.String().serializer(),Serdes.String().serializer());
        inputTopic2 = topologyTestDriver.createInputTopic(inputKafkaTopic2, Serdes.String().serializer(),Serdes.String().serializer());
        inputPizza = topologyTestDriver.createInputTopic(pizzaInKafkaTopic, Serdes.String().serializer(),pizzaSerde.serializer());

        validMessage = topologyTestDriver.createOutputTopic(validMessagesKafkaTopic,Serdes.String().deserializer(),Serdes.String().deserializer());
        invalidMessage = topologyTestDriver.createOutputTopic(invalidMessagesKafkaTopic,Serdes.String().deserializer(),Serdes.String().deserializer());
        incident = topologyTestDriver.createOutputTopic(incidentKafkaTopic,Serdes.String().deserializer(),incSerde.deserializer());
        pizzaOrder = topologyTestDriver.createOutputTopic(pizzaOrderKafkaTopic,Serdes.String().deserializer(),Serdes.String().deserializer());

    }
    @Test
    void shouldProcessAndSendMessageToValidTopic() {
        //given
        String key= UUID.randomUUID().toString();
        String message = "input message";
        //when
        inputTopic.pipeInput(key, message);
        //then
        TestRecord<String, String> validMessageValue = validMessage.readRecord();
        assertThat(validMessageValue.getValue()).isEqualTo(message.toUpperCase());
        assertThat(validMessageValue.getKey()).isEqualTo(key);
    }
    @Test
    void shouldProcessAndSendMessageToValidTopicFromSecondTopic() {
        //given
        String key= UUID.randomUUID().toString();
        String message = "input message";
        //when
        inputTopic2.pipeInput(key, message);
        //then
        TestRecord<String, String> validMessageValue = validMessage.readRecord();
        assertThat(validMessageValue.getValue()).isEqualTo(message.toUpperCase());
        assertThat(validMessageValue.getKey()).isEqualTo(key);
    }
    @Test
    void shouldProcessAndSendMessageToInvalidTopic() {
        //given
        String key= UUID.randomUUID().toString();
        String message = "msg";
        //when
        inputTopic.pipeInput(key, message);
        //then
        TestRecord<String, String> validMessageValue = invalidMessage.readRecord();
        assertThat(validMessageValue.getValue()).isEqualTo(message.toLowerCase());
        assertThat(validMessageValue.getKey()).isEqualTo(key);
    }
    @Test
    void shouldProcessAndSendMessageToInvalidTopicFromSecondTopic() {
        //given
        String key= UUID.randomUUID().toString();
        String message = "msg";
        //when
        inputTopic2.pipeInput(key, message);
        //then
        TestRecord<String, String> validMessageValue = invalidMessage.readRecord();
        assertThat(validMessageValue.getValue()).isEqualTo(message.toLowerCase());
        assertThat(validMessageValue.getKey()).isEqualTo(key);
    }
    @Test
    void shouldProcessAndSendPizzaOrderToOrderTopic() {
        //given
        String key= UUID.randomUUID().toString();
        PizzaOrder order = ResourceFactory.getPizzaOrder(false);
        //when
        inputPizza.pipeInput(key, order);
        //then
        TestRecord<String, String> pizza =pizzaOrder.readRecord();
        assertThat(pizza.getValue()).isEqualTo(pizzaService.mapToOrder(order));
        assertThat(pizza.getKey()).isEqualTo(key);
    }
    @Test
    void shouldProcessAndSendPizzaOrderToIncident() {
        //given
        String key= UUID.randomUUID().toString();
        PizzaOrder order = ResourceFactory.getPizzaOrder(true);
        //when
        inputPizza.pipeInput(key, order);
        //then
        TestRecord<String, Incident> incidentRecord = incident.readRecord();
        assertThat(incidentRecord.getValue().toString()).isEqualTo(pizzaService.mapToIncident(order).toString());
        assertThat(incidentRecord.getKey()).isEqualTo(key);
    }

}