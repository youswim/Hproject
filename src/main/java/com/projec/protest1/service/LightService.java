package com.projec.protest1.service;

import org.springframework.stereotype.Service;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Service
public class LightService {

    private static final String QUEUE_NAME = "change_request_queue";
    private static final String QUEUE_ADDRESS = "192.168.45.36";
    private static final String ID = "admin";
    private static final String PASSWD = "admin";


    public void sendMessage(String message) {
        ConnectionFactory factory = initConFactory();

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, makeArguments());
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Object> makeArguments() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 1000);
        return arguments;
    }

    private ConnectionFactory initConFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(QUEUE_ADDRESS);
        factory.setUsername(ID);
        factory.setPassword(PASSWD);
        return factory;
    }
}
