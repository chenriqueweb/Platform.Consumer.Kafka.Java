package br.com.via.consumer.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class KafkaConfig {

    public static Properties getProperties() throws IOException {
        Properties props = new Properties();

        FileInputStream file = new FileInputStream("src/main/resources/application.properties");

        props.load(file);

        props.getProperty("kafka.producer.server");
        props.getProperty("kafka.producer.topic");
        props.getProperty("kafka.producer.client");

        return props;
    }
}
