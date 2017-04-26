package com.hxline.threadservice.messaging.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxline.threadservice.domain.Thread;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *
 * @author Handoyo
 */
public class ThreadPublisher{

    private String serviceInstance;
    private ConnectionFactory connectionFactory;
    private BasicProperties properties;
    private final String EXCHANGE = "fanout.thread";

    public void setServiceInstance(String serviceInstance) {
        this.serviceInstance = serviceInstance;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public Boolean send(Thread thread) {
        try {
            properties = new BasicProperties().builder()
                            .expiration("1000000") //message otomatis di hapus setelah 16.6 menit
                            .type(serviceInstance)
                            .build();
            ObjectMapper mapper = new ObjectMapper();
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();

            channel.basicPublish(EXCHANGE, "", properties, mapper.writeValueAsString(thread).getBytes());
            System.out.println("Sent : Success");
//            System.out.println(serviceInstance);
            channel.close();
            connection.close();

            return true;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
}