package br.com.via.consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("kafka")
public class KafkaConfig {

    @Value("${spring.kafka.environment}")
    private static String environment;


    // Producer - YAML
    @Value("${spring.kafka.producer.bootstrap-servers}")  // BOOTSTRAP_SERVERS_CONFIG
    private static String producerBootstrapServers;
    @Value("${spring.kafka.producer.group-id}")           // GROUP_ID_CONFIG
    private static String producerGroupId;

    // Consumer - YAML
    @Value("${spring.kafka.consumer.bootstrap-servers}")  // BOOTSTRAP_SERVERS_CONFIG
    private static String consumerBootstrapServers;
    @Value("${spring.kafka.consumer.group-id}")           // GROUP_ID_CONFIG
    private static String consumerGroupId;
    @Value("${spring.kafka.consumer.pool-records}")       // MAX_POLL_RECORDS_CONFIG
    private static String consumerPoolRecords;
    @Value("${spring.kafka.consumer.auto-offset-reset}")  // AUTO_OFFSET_RESET_CONFIG
    private static String consumerAutoOffsetReset;


    public static String tipFileConfig;

    // Construtor para obter a configuracao do Kafka conforme o Tipo de Arquivo utilizado no projeto
    public KafkaConfig(String tipFileConfig) throws IOException {
        this.tipFileConfig = tipFileConfig;

        if(tipFileConfig == "properties") {
            getProperties();
        } else {
            getPropertiesYaml();
        }
    }

    public static Properties getProperties() throws IOException {
        Properties props = new Properties();

        FileInputStream file = new FileInputStream("src/main/resources/application.properties");

        props.load(file);

        // Producer
        props.getProperty("kafka.producer.server");
        props.getProperty("kafka.producer.topic");
        props.getProperty("kafka.producer.client");
        props.getProperty("kafka.producer.grouped");

        // Consumer
        props.getProperty("kafka.consumer.server");
        props.getProperty("kafka.consumer.topic");
        props.getProperty("kafka.consumer.client");
        props.getProperty("kafka.consumer.grouped");
        props.getProperty("kafka.consumer.pool.records");
        props.getProperty("kafka.consumer.auto.offset.reset");

        return props;
    }

    public static Properties getPropertiesYaml() {

        Properties props = new Properties();

        props.setProperty("kafka.environment", environment);

        // Producer
        props.setProperty("kafka.producer.server", producerBootstrapServers);
        props.setProperty("kafka.producer.grouped", producerGroupId);

        // Consumer
        props.setProperty("kafka.consumer.server", consumerBootstrapServers);
        props.setProperty("kafka.consumer.grouped", consumerGroupId);
        props.setProperty("kafka.consumer.pool.records", consumerPoolRecords);
        props.setProperty("kafka.consumer.auto.offset.reset", consumerAutoOffsetReset);

        return props;
    }
}
