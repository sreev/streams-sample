package org.sreev.kafka;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class Pipe {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-pipe");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final StreamsBuilder streamsBuilder = new StreamsBuilder();

        streamsBuilder.stream("streams-plaintext-input").to("streams-pipe-output");

        final Topology topology = streamsBuilder.build();

        final KafkaStreams kafkaStreams = new KafkaStreams(topology, properties);

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        // catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                kafkaStreams.close();
                countDownLatch.countDown();
            }
        });

        try {
            kafkaStreams.start();
            countDownLatch.await();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.exit(0);
    }
}
