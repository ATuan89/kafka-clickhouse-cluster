package com.kafka.clickhouse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class Producer {
	
	private static final String TOPIC_NAME = "test"; // Change to your Kafka topic name
    private static final String FILE_PATH = "./src/main/resources/test/test.log"; // Change to the path of your log file

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "172.20.0.3:29092"); // Change to your Kafka broker address
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            KafkaProducer<String, String> producer = new KafkaProducer<>(props);
            String line;
            while ((line = br.readLine()) != null) {
                ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, null, line);
                producer.send(record);
                System.out.println("data sended kafka " + record);
            }
            producer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
