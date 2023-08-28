package br.com.via.consumer.config;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static br.com.via.consumer.config.KafkaConfig.getProperties;
import static br.com.via.consumer.config.KafkaConfig.getPropertiesYaml;

@Configuration
public class KafkaProducerConfig
{
    KafkaConfig kafkaConfig;

    public static String tipFileConfig;
    public static String server;
    public static String groupId;

    public KafkaProducerConfig(String tipFileConfig) throws IOException {
        this.tipFileConfig = tipFileConfig;

        if(tipFileConfig == "properties") {
            server = getProperties().getProperty("kafka.producer.server");
            groupId = getProperties().getProperty("kafka.producer.grouped");
        } else {
            server = getPropertiesYaml().getProperty("kafka.producer.server");
            groupId = getPropertiesYaml().getProperty("kafka.producer.grouped");
        }
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() throws IOException {
        Map<String, Object> configProps = new HashMap<>();

        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        //configProps.put("schema.registry.url", "http://localhost:8081");

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() throws IOException {
        return new KafkaTemplate<>(producerFactory());
    }
}
