package com.example.demo.topology;

import com.example.demo.model.Incident;
import com.example.demo.streams.CustomSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
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
        String pizzaOrderTopic = "pizzaOrder";
        String incidentTopic = "pineapplePizzaTopic";

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        //valid messages
        KStream<String, String> validStream = streamsBuilder.stream(validMessagesTopic, Consumed.with(Serdes.String(), Serdes.String()));
        validStream.peek((key,messages)-> log.info("Valid message: {}",messages));

        //invalid messages
        KStream<String, String> invalidStream = streamsBuilder.stream(invalidMessagesTopic, Consumed.with(Serdes.String(), Serdes.String()));
        invalidStream.peek((key,messages)-> log.info("Inalid message: {}",messages));

        //order stream
        KStream<Object, Object> pizzaMap = streamsBuilder.stream(pizzaOrderTopic);
        pizzaMap.peek((key,value)->log.info("New order: \n {}",value));

        //incident
        CustomSerde<Incident> incSerde= new CustomSerde<>(Incident.class);
        KStream<String, Incident> incMap = streamsBuilder.stream(incidentTopic, Consumed.with(Serdes.String(), incSerde));
        incMap.peek((key,value)-> log.info("{}", value));

        //Stateful operations

        return streamsBuilder.build();
    }
}
