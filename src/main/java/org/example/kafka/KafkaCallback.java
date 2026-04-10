package org.example.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

public class KafkaCallback implements Callback {

    private final String messageKey;
    private final String messageValue;

    public KafkaCallback(String messageKey, String messageValue) {
        this.messageKey = messageKey;
        this.messageValue = messageValue;
    }

    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        if (e != null) {
            System.err.println("Error sending message");
            System.err.println("Key:" + messageKey);
            System.err.println("Value:" + messageValue);
            System.err.println("Error details:" + e.getMessage());
        } else {
            System.out.println("Message sent successfully");
            System.out.println("Key:" + messageKey);
            System.out.println("Topic:" + recordMetadata.topic());
            System.out.println("Partition:" + recordMetadata.partition());
            System.out.println("Offset:" + recordMetadata.offset());
        }
    }
}
