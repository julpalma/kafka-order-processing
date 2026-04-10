package org.example;

import org.example.kafka.KafkaConsumerApp;
import org.example.kafka.KafkaProducerApp;

public class Application {
    public static void main(String[] args) {
        System.out.println("Starting Kafka apps...");

        // Run consumer in a separate thread
        new Thread(() -> new KafkaConsumerApp().createKafkaConsumer()).start();

        // Small delay so consumer starts first
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        // Run producer
        new KafkaProducerApp().createKafkaProducer();
    }
}