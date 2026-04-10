package org.example.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.model.OrderEvent;

import java.util.Properties;
import java.util.Random;

public class KafkaProducerApp {

    public void createKafkaProducer() {

        Properties kafkaProps = KafkaConfig.getProducerProperties();
        ObjectMapper objectMapper = new ObjectMapper();
        Random random = new Random();

         /**************************************************************
         Publish Asynchronously with a callback
         **************************************************************/

         //Using try-with-resources, so when execution leaves the try block, Java automatically calls producer.close()
         //When producer.close() runs: waits for pending messages to be sent, releases network resources, shuts down internal threads
         //NO memory resource leak

         //Publish 20 messages at 2 second intervals, with a random key
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(kafkaProps)) {
            //int startKey = random.nextInt(1000) ;

            //This is to simulate a failure
            int startKey = 450;

            for( int i=startKey; i < startKey + 10; i++) {

                OrderEvent orderEvent = new OrderEvent("order-" + i,
                        "customer-" + (100 + i),
                        "CREATED",
                        Math.round((50 + random.nextDouble() * 500) * 100.0) / 100.0);

                //Current flow:
                //OrderEvent (Java object) -> JSON String (Jackson) -> StringSerializer -> byte[] (Kafka internal)

                String messageKey = orderEvent.getOrderId();
                String messageValue = objectMapper.writeValueAsString(orderEvent);

                //Create a producer Record
                ProducerRecord<String, String> kafkaRecord =
                        new ProducerRecord<>(KafkaConfig.KAFKA_TOPIC, messageKey, messageValue);

                System.out.println("Sending JSON message: " + messageValue);

                //Publish to Kafka
                producer.send(kafkaRecord, new KafkaCallback(messageKey, messageValue));

                Thread.sleep(2000);
            }
            //It ensures all messages are sent before closing.
            producer.flush();
        }
        catch(Exception e) {
            System.err.println("Producer error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting Kafka Producer app.");
        new KafkaProducerApp().createKafkaProducer();
    }
}
