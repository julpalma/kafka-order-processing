package org.example.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.example.model.OrderEvent;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class KafkaConsumerApp {

    private final Properties kafkaProps = KafkaConfig.getConsumerProperties();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Simulate one failure per application run
    private boolean failureAlreadySimulated = false;

    public void createKafkaConsumer() {

        try (KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(kafkaProps)) {

            kafkaConsumer.subscribe(Collections.singletonList(KafkaConfig.KAFKA_TOPIC));
            System.out.println("Consumer subscribed to topic: " + KafkaConfig.KAFKA_TOPIC);

            while (true) {
                ConsumerRecords<String, String> records =
                        kafkaConsumer.poll(Duration.ofMillis(500));

                for (ConsumerRecord<String, String> record : records) {
                    try {
                        OrderEvent orderEvent =
                                objectMapper.readValue(record.value(), OrderEvent.class);

                        System.out.println("Processing message.");
                        System.out.println("Key: " + record.key());
                        System.out.println("Order event: " + orderEvent);
                        System.out.println("Partition: " + record.partition());
                        System.out.println("Offset: " + record.offset());

//                        if (!failureAlreadySimulated && "order-450".equals(record.key())) {
//                            failureAlreadySimulated = true;
//                            throw new RuntimeException("Simulated processing failure");
//                        }

                        System.out.println("Message processed successfully.");

                        //Kafka commits the next offset to read, not the current one.
                        //So if Kafka processed offset 20, it will commit offset 21.
                        //That tells Kafka: everything before offset 21 was processed successfully
                        //Stronger pattern is to commit the exact next offset for the record that was just processed.

                        TopicPartition topicPartition =
                                new TopicPartition(record.topic(), record.partition());

                        Map<TopicPartition, OffsetAndMetadata> offsetToCommit = new HashMap<>();
                        offsetToCommit.put(topicPartition, new OffsetAndMetadata(record.offset() + 1));

                        kafkaConsumer.commitSync(offsetToCommit);

                        System.out.println("Committed offset: " + (record.offset() + 1));
                        System.out.println("-----------------------------------");

                    } catch (Exception processingException) {
                        System.err.println("Error processing message: " + processingException.getMessage());
                        System.err.println("Offset NOT committed for key: " + record.key());
                        System.err.println("-----------------------------------");
                        //This will make the consumer stop immediately after the failure
                        return;
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Consumer error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting Kafka Consumer app.");
        new KafkaConsumerApp().createKafkaConsumer();
    }
}