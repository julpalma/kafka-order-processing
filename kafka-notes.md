## 🧠 Kafka Concepts (Summary)

### What is Kafka?
Apache Kafka is a distributed event streaming platform where:
- Producers publish messages
- Consumers subscribe and process messages

---

### Core Components

**Messages**
- Key: used for partitioning
- Value: actual data (payload)
- Timestamp: event time

**Topics**
- Logical channels that store messages
- Can have multiple partitions
- Support multiple producers and consumers

**Brokers**
- Kafka servers that store and manage data
- Handle topics, partitions, and replication

---

### Consumer Groups

- A group of consumers sharing the same workload
- Each message is processed by only one consumer in a group
- Enables horizontal scaling
- Different consumer groups receive the same messages independently

---

### Partitions

- Topics are divided into partitions for scalability
- Each partition is ordered and handled by a leader broker
- Same key → same partition (ordering guarantee)
- Enables parallel processing

---

### Offsets

- Offset = position of a message in a partition
- Kafka tracks:
    - **Current offset** (last read)
    - **Committed offset** (last successfully processed)

✔ In this project:
- Auto-commit is disabled
- Offsets are committed **only after successful processing**

👉 This ensures **at-least-once delivery**

---

### Key Benefits of Kafka

- High throughput
- Low latency
- Fault tolerance
- Horizontal scalability
- Decoupling between producers and consumers

---

### Common Use Cases

- Asynchronous messaging
- Real-time stream processing
- Event-driven architectures
- Logging and monitoring
- Real-time analytics