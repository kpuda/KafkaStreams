package com.kp.kafka.config;

import com.kp.kafka.topology.TopologyBuilder;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@AllArgsConstructor
public class KafkaStreamConfig {

    private final TopologyBuilder topologyBuilder;

    @Bean
    public KafkaStreams run() {

        Topology topology = topologyBuilder.createTopology();

        KafkaStreams kafkaStreams = new KafkaStreams(topology, createStreamsProperties());

        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                kafkaStreams.close();
            }
        });

        try {
            kafkaStreams.start();
        } catch (Throwable e) {e.printStackTrace();
        }
        return kafkaStreams;
    }

    public Properties createStreamsProperties() {
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "kp-pizzaOrderApp");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:29092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.put(StreamsConfig.producerPrefix(ProducerConfig.ACKS_CONFIG),"all");
        return properties;
    }
}
