package com.hxline.threadservice.messaging.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxline.threadservice.domain.Thread;
import com.hxline.threadservice.services.interfaces.ThreadServicesInterface;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

/**
 *
 * @author Handoyo
 */
public class ThreadConsumer {
    
    private String serviceInstance;
    private ThreadServicesInterface threadServices;
    private ConnectionFactory connectionFactory;
    private final String EXCHANGE = "fanout.thread";

    public void setServiceInstance(String serviceInstance) {
        this.serviceInstance = serviceInstance;
    }

    public String getServiceInstance() {
        return serviceInstance;
    }

    public void setThreadServices(ThreadServicesInterface threadServices) {
        this.threadServices = threadServices;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
    
    public void consuming(){
        try {
            String queueName = "Thread-Queue-" + serviceInstance;
            Connection connection = connectionFactory.newConnection();

            Channel channel = connection.createChannel();
            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.FANOUT, true);
            channel.queueBind("", EXCHANGE, "");
            channel.basicConsume(queueName, consumer);

            while (true) {
                try {
                    Delivery delivery = consumer.nextDelivery();
                    long deliveryTag = delivery.getEnvelope().getDeliveryTag();
                    if (delivery == null) {
//                        break;
                    } else {
                        //hanya mengambil pesan dengan messageId selain miliknya sendiri
                        if (!delivery.getProperties().getType().equalsIgnoreCase(serviceInstance)) {
                            if (delivery.getEnvelope().isRedeliver()) {
                                System.out.println("Message has been delivered before.");
                            } else {
                                if (save(delivery)) {
                                    channel.basicAck(deliveryTag, false);
                                    System.out.println("Message Delivered.");
                                }
                            }
                            
                        } else {
                            channel.basicAck(deliveryTag, false);
                        }
                    }
                } catch (Exception e) {
                    System.err.println(e);
                    break;
                }
            }

            channel.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    private Boolean save(Delivery delivery) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String msg = new String(delivery.getBody(), "UTF-8");
            threadServices.saveQueue(mapper.readValue(msg, Thread.class));
            return true;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
}