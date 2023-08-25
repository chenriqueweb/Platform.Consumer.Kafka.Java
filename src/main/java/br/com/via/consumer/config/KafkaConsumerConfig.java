package br.com.via.consumer.config;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static br.com.via.consumer.config.KafkaConfig.getProperties;
import static br.com.via.consumer.config.KafkaConfig.getPropertiesYaml;

@Configuration
public class KafkaConsumerConfig {

    KafkaConfig kafkaConfig;

    public static String tipFileConfig;
    public static String server;
    public static String groupId;
    public static String poolRecords;
    public static String autoOffsetReset;


    public KafkaConsumerConfig(String tipFileConfig) throws IOException {
        this.tipFileConfig = tipFileConfig;

        if(tipFileConfig == "properties") {
            server = getProperties().getProperty("kafka.consumer.server");
            groupId = getProperties().getProperty("kafka.consumer.grouped");
            poolRecords = getProperties().getProperty("kafka.consumer.pool.records");
            autoOffsetReset = getProperties().getProperty("kafka.consumer.auto.offset.reset");
        } else {
            server = getPropertiesYaml().getProperty("kafka.consumer.server");
            groupId = getPropertiesYaml().getProperty("kafka.consumer.grouped");
            poolRecords = getPropertiesYaml().getProperty("kafka.consumer.pool.records");
            autoOffsetReset = getPropertiesYaml().getProperty("kafka.consumer.auto.offset.reset");
        }
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() throws IOException {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put("schema.registry.url", "http://localhost:8081");
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, poolRecords);      // default 500
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset); // default "latest"

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() throws IOException {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());
        factory.setCommonErrorHandler(errorHandler());
        return factory;
    }

    public CommonErrorHandler errorHandler() {
        BackOff fixedBackOff = new FixedBackOff(1000, 20);
        DefaultErrorHandler errorHandler = new DefaultErrorHandler((consumerRecord, e) -> {}, fixedBackOff);
        errorHandler.addRetryableExceptions(RuntimeException.class);
        return errorHandler;
    }
}
