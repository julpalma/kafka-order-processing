package org.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaConfig {

    public static final String KAFKA_TOPIC = "kafka.learning.orders";
    public static final String BOOTSTRAP_SERVERS = "localhost:9092";
    public static final String GROUP_ID = "kafka-java-consumer";

    public static Properties getConsumerProperties() {

        Properties kafkaConsumerProps = new Properties();
        //List of Kafka brokers to connect to
        kafkaConsumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        //Consumer Group ID for this consumer
        kafkaConsumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        //Deserializer class to convert Keys from Byte Array to String
        kafkaConsumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //Deserializer class to convert Messages from Byte Array to String
        kafkaConsumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //Set to consume from the earliest message, on start when no offset is
        //available in Kafka
        kafkaConsumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                "earliest");
        kafkaConsumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        return kafkaConsumerProps;
    }

    public static Properties getProducerProperties() {

        Properties kafkaProducerProps = new Properties();

        //List of Kafka brokers to connect to
        kafkaProducerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        //Serializer class used to convert Keys type String to Byte Arrays
        kafkaProducerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //Serializer class used to convert Messages type String to Byte Arrays
        kafkaProducerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // Reliability settings
        kafkaProducerProps.put(ProducerConfig.ACKS_CONFIG, "all");
        kafkaProducerProps.put(ProducerConfig.RETRIES_CONFIG, 3);

        return kafkaProducerProps;
    }
}
