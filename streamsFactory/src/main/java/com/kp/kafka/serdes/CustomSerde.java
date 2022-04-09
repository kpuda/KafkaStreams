package com.kp.kafka.serdes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class CustomSerde<T> implements Serde<T> {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final Class<T> type;
    public CustomSerde(Class<T> type) {
        this.type = type;
        OBJECT_MAPPER.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }
    @Override
    public Serializer<T> serializer() {
        return (topic, data) -> serialize(data);
    }
    @SneakyThrows
    private byte[] serialize(T data) {
        return OBJECT_MAPPER.writeValueAsBytes(data);
    }

    @Override
    public Deserializer<T> deserializer() {
        return (topic, bytes) -> deserialize(bytes);
    }
    @SneakyThrows
    private T deserialize(byte[] bytes) {
        return OBJECT_MAPPER.readValue(bytes, type);
    }
}
