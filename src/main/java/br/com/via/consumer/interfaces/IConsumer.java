package br.com.via.consumer.interfaces;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;

public interface IConsumer {

    @KafkaListener( topics = "#{'${spring.kafka.topics}'.split(',')}",
                   groupId = "#{'${spring.kafka.topics}'.split(',')}")
    @RetryableTopic(
            backoff = @Backoff(value = 3000L),
            attempts = "5",
            autoCreateTopics = "true",
            include = RuntimeException.class)

    public default void processaTipico(String topico) {
        System.out.println(topico);
    }

}

