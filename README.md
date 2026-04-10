# 📦 Kafka Order Processing Project

![Java](https://img.shields.io/badge/Java-17-blue)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-3.9-black)
![Docker](https://img.shields.io/badge/Docker-Containerized-blue)
![Build](https://img.shields.io/badge/Build-Maven-red)
![Status](https://img.shields.io/badge/Project-Active-brightgreen)

This project demonstrates a Kafka-based event-driven system using Java, focusing on:

- Producer/Consumer architecture
- JSON event serialization
- Manual offset management
- Failure handling and message reprocessing
- At-least-once delivery semantics

## 🛠️ Tech Stack
- Java 17
- Apache Kafka
- Docker
- Jackson (JSON serialization)
- Maven

## 🏗️ Architecture Overview

```text
KafkaProducerApp → Kafka Topic → KafkaConsumerApp
```

- Producer sends OrderEvent messages as JSON
- Consumer processes messages and commits offsets manually
- Failed messages are not committed and can be reprocessed

## 📌 Features Implemented

### ✅ Kafka Producer
- Asynchronous message publishing
- Custom callback implementation
- JSON serialization using Jackson
- Idempotent producer configuration
- Retry mechanism

### ✅ Kafka Consumer
- Manual offset commit (commitSync)
- Commit only after successful processing
- Failure simulation for learning
- Controlled reprocessing behavior

## 🔄 Message Flow

### Normal Flow
1. Producer sends message
2. Consumer processes message
3. Consumer commits offset

### Failure Flow (Manual Commit)
1. Consumer reads message
2. Processing fails
3. Offset is NOT committed
4. Consumer restarts
5. Kafka re-delivers the message

## 🧠 Key Kafka Concepts Demonstrated

### 1. Serialization
```text
OrderEvent → JSON → Kafka → JSON → OrderEvent
```

### 2. Offset Management
- Auto-commit disabled
- Manual commit after successful processing
- Offset = next record to read

### 3. At-Least-Once Delivery
- Messages are not lost
- Failed messages are reprocessed
- Duplicate processing is possible

### 4. Producer Reliability
- acks=all
- retries=3
- enable.idempotence=true

## 🐳 Running Kafka (Docker)

1. Please make sure that Docker is already installed in the system.

2. If on Windows O/S, open a Powershell window.
   If on Mac OS or Linux, open a Terminal window.

3. Execute the following command from kafka directory
```text
docker-compose -f docker-compose.yml up -d
```

4. Check if the kafka container is up and running
```text
docker ps
```

5. Run consumer
```text
Run in IntelliJ: KafkaConsumerApp
```

6. Run Producer
 ```text
Run in IntelliJ: KafkaProducerApp
```

7. Testing Failure & Reprocessing

   Simulate a failure in consumer:
   - Run consumer first, then run producer
   - Observe:
   - message fails
   - offset not committed
   - Comment out the failure condition and restart consumer

   👉 The same message is re-delivered


8. To shutdown and remove the setup, execute this command in the same directory

        docker-compose -f docker-compose.yml down

## 🎯 Learning Outcomes

This project demonstrates:

- Kafka consumer offset management
- Failure handling strategies
- Message reprocessing behavior
- Producer reliability configurations
- Event-driven system fundamentals

## 📚 For detailed Kafka notes, see [Kafka Notes](kafka-notes.md)