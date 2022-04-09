package com.example.demo.streams;

import com.example.demo.topology.TopologyBuilder;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@AllArgsConstructor
@Component
public class KafkaStreamsConfig {

    private final TopologyBuilder topologyBuilder;

    @SneakyThrows
    @Bean
    public KafkaStreams run() {
        if (!Files.exists(Paths.get("kafkaVolume/kafka-streams").toAbsolutePath())){
            Files.createDirectories(Paths.get("kafkaVolume/kafka-streams").toAbsolutePath());
        }

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
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return kafkaStreams;
    }

    @SneakyThrows
    public Properties createStreamsProperties()  {
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "kp-kp");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:29092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG,"0");
        String path = Paths.get("kafkaVolume/kafka-streams").toAbsolutePath().toString();
        properties.put(StreamsConfig.STATE_DIR_CONFIG,path);
        return properties;
    }
}
