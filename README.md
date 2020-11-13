# Using Kafka Streams

## Stream from topic `streams-plaintext-input` to topic `streams-pipe-output`.

### Download & Install Kafka
Download Kafka [here](https://www.apache.org/dyn/closer.cgi?path=/kafka/2.6.0/kafka_2.13-2.6.0.tgz)
```
% tar -zxf kafka_2.13-2.6.0.tgz
% cd kafka_2.13-2.6.0
```

### Build:
```
% git clone https://www.github.com/sreev/streams-sample.git
% cd streams-sample
% mvn clean package
```

### Run:
I.
**Terminal#1. Start ZooKeeper**

`% bin/zookeeper-server-start.sh config/zookeeper.properties`

II.
**Terminal#2. Start Kafka Server**

`% bin/kafka-server-start.sh config/server.properties`

III.
**Terminal#3 (_keep in view_). Start Kafka Console Producer**

`% bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic streams-plaintext-input`

IV.
**Terminal#4. Start `streams-sample` application**

`% mvn exec:java -Dexec.mainClass="org.sreev.kafka.Pipe"`

V.
**Terminal#5 (_keep in view_). Start Kafka Console Consumer**

`% bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic streams-pipe-output --from-beginning`

### Instructions:
Type words/sentences and press Enter, in **Terminal#3**.

Watch they appear in **Terminal#5**.

:tophat: :rabbit:
