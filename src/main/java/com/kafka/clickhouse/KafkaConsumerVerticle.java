package com.kafka.clickhouse;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.kafka.client.consumer.KafkaConsumer;

public class KafkaConsumerVerticle extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new KafkaConsumerVerticle());
    }

    @Override
    public void start() throws Exception {
        JsonObject jsonObject = new Gson().fromJson(new FileReader("./configs/manager.json"), JsonObject.class);
        createTableIfNotExists(jsonObject.getAsJsonArray("clickhouse"));

        JsonObject kafkaConfig = jsonObject.getAsJsonObject("kafka");
        String kafkaBroker = kafkaConfig.get("broker").getAsString();
        String kafkaTopic = kafkaConfig.get("topic").getAsString();
        String groupId = kafkaConfig.get("group_id").getAsString();
        boolean enableAutoCommit = kafkaConfig.get("enable_auto_commit").getAsBoolean();
        String autoOffsetReset = kafkaConfig.get("auto_offset_reset").getAsString();

        Map<String, String> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBroker);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, String.valueOf(enableAutoCommit));

        KafkaConsumer<String, String> consumer = KafkaConsumer.create(vertx, config);

        consumer.subscribe(kafkaTopic);
        System.out.println("\nStarting insert to ClickHouse cluster");
        consumer.handler(record -> {
            System.out.println("\nData processing key=" + record.key() + ",value=" + record.value() + ",partition="
                    + record.partition() + ",offset=" + record.offset());
            
            insertToClickHouse(record.value(), jsonObject.getAsJsonArray("clickhouse"));
        });
    }
    
    private static void createTableIfNotExists(JsonArray clickhouseConfigs) {
    	try {
    		for (JsonElement element : clickhouseConfigs) {
                JsonObject clickhouseConfig = element.getAsJsonObject();
                String clickhouseName = clickhouseConfig.get("name").getAsString();
                String clickhouseUrl = clickhouseConfig.get("url").getAsString();
                String clickhouseDatabase = clickhouseConfig.get("database").getAsString();
                String clickhouseUser = clickhouseConfig.get("user").getAsString();
                String clickhousePassword = clickhouseConfig.get("password").getAsString();
                String clickhosueCreateTable = clickhouseConfig.get("create_table_query").getAsString();
                
                Connection connection = DriverManager.getConnection(clickhouseUrl + clickhouseDatabase, clickhouseUser, clickhousePassword);
                PreparedStatement statement = connection.prepareStatement(clickhosueCreateTable);

                statement.executeUpdate();

                statement.close();
                connection.close();

                System.out.println("Created table if not exists into " + clickhouseName);
            
    		}
    		
    	}catch (Exception e) {
			// TODO: handle exception
		}
        
    }

    private static void insertToClickHouse(String value, JsonArray clickhouseConfigs) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            LogModel log = objectMapper.readValue(value, LogModel.class);
            for (JsonElement element : clickhouseConfigs) {
                JsonObject clickhouseConfig = element.getAsJsonObject();
                String clickhouseName = clickhouseConfig.get("name").getAsString();
                String clickhouseUrl = clickhouseConfig.get("url").getAsString();
                String clickhouseDatabase = clickhouseConfig.get("database").getAsString();
                String clickhouseUser = clickhouseConfig.get("user").getAsString();
                String clickhousePassword = clickhouseConfig.get("password").getAsString();
                String insertQuery = clickhouseConfig.get("insert_logs_query").getAsString();

                Connection connection = DriverManager.getConnection(clickhouseUrl + clickhouseDatabase, clickhouseUser, clickhousePassword);
                PreparedStatement statement = connection.prepareStatement(insertQuery);

                statement.setTimestamp(1, new Timestamp(log.getReceive_time().getTime()));
                statement.setString(2, log.getServices());
                statement.setString(3, log.getSource_host());
                statement.setString(4, log.getRemote_addr());
                statement.setLong(5, log.getBody_bytes_sent());
                statement.setLong(6, log.getBytes_sent());
                statement.setDouble(7, log.getRequest_time());
                statement.setInt(8, log.getStatus());
                statement.setString(9, log.getRequest_uri());
                statement.setString(10, log.getRequest_method());
                statement.setString(11, log.getHost());
                statement.setString(12, log.getUri());
                statement.setString(13, log.getHostname());
                statement.setString(14, log.getHttp_referer());
                statement.setString(15, log.getHttp_user_agent());
                statement.setDouble(16, log.getUpstream_response_time());
                statement.setString(17, log.getUpstream_host());
                statement.setString(18, log.getUpstream_cache_status());
                statement.setLong(19, log.getRequest_length());
                statement.setInt(20, log.getServer_port());
                statement.setString(21, log.getServer_protocol());
                statement.setLong(22, log.getConnection());
                statement.setLong(23, log.getConnection_requests());
                statement.setString(24, log.getIsp());

                statement.executeUpdate();

                // Close the connection and statement
                statement.close();
                connection.close();

                System.out.println("Data inserted successfully into " + clickhouseName);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}

