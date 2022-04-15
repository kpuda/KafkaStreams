package com.example.demo.topology;

import com.example.demo.model.Incident;
import com.example.demo.streams.CustomSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.regex.Pattern;

@Component
public class TopologyBuilder {

    Logger log = LoggerFactory.getLogger("KafkaStreamsPresentation");

    public Topology createTopology() {
        String validMessagesTopic = "validMessages";
        String invalidMessagesTopic = "invalidMessages";
        String pizzaTopic = "pizzaOrder";
        String incidentTopic = "pineapplePizzaTopic";

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        //CREATE STREAM FOR VALID MESSAGES
        KStream<String, String> validStream = streamsBuilder.<String, String>stream(validMessagesTopic);
        validStream.peek((key,messages)->log.info("Valid message received: {}",messages));

        //CREATE STREAM FOR INVALID MESSAGES
        KStream<String, String> invalidStream = streamsBuilder.<String, String>stream(invalidMessagesTopic);
        invalidStream.peek((key,messages)->log.info("Invalid message received: {}",messages));


        //CREATE STREAM FOR PIZZA ORDERS
        KStream<String, String> pizzaStream = streamsBuilder.<String, String>stream(pizzaTopic);
        pizzaStream.peek((key,value)->log.info("{}",value));

        //CREATE STREAM FOR INCIDENTS
        CustomSerde<Incident> incSerde= new CustomSerde<>(Incident.class);
        KStream<String, Incident> incStream = streamsBuilder.stream(incidentTopic, Consumed.with(Serdes.String(), incSerde));
        incStream.peek((key,value)->log.info("New incident: {}",value));

        //CREATE STREAM FOR STATEFUL OPERATIONS
        Pattern pattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);
        KTable<String, Long> kTable = validStream.flatMapValues(value -> Arrays.asList(pattern.split(value.toLowerCase())))
                .groupBy((key, word) -> word)
                .count();
        kTable.toStream()
                .foreach((word, count) -> System.out.println("word: " + word + " -> " + count));
        return streamsBuilder.build();
    }
}
