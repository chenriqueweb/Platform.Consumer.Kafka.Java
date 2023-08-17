package br.com.via.consumer.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.util.Properties;

import static br.com.via.consumer.config.KafkaConfig.getProperties;

public class KafkaProducerConfig {

    public static Properties propertiesProducer() throws IOException {
        var properties = new Properties();

        Properties prop = getProperties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, prop.getProperty("kafka.producer.server"));
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.CLIENT_ID_CONFIG, prop.getProperty("kafka.producer.client"));

        return properties;
    }
}
