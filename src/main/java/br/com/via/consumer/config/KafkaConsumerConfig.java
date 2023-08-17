package br.com.via.consumer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.IOException;
import java.util.Properties;

import static br.com.via.consumer.config.KafkaConfig.getProperties;

public class KafkaConsumerConfig {

    public static Properties propertiesConsumer() throws IOException {
        var properties = new Properties();

        Properties prop = getProperties();

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, prop.getProperty("kafka.producer.server"));
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, prop.getProperty("kafka.producer.client"));

        return properties;
    }
}
