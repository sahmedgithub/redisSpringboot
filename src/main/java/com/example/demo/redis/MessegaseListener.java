package com.example.demo.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.redis.outbound.RedisQueueOutboundChannelAdapter;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MessegaseListener {

    @Autowired
    JedisConnectionFactory factory;

    @ServiceActivator(inputChannel = "inputChannel", outputChannel = "redisChannel")
    public Message<?> receiveFromService(Message<?> message) {
        System.out.println(message);
        return message;
    }

    @ServiceActivator(inputChannel = "redisChannel")
    public void sendMessageToQueue(Message<?> message) {
        RedisQueueOutboundChannelAdapter adapter = new RedisQueueOutboundChannelAdapter("mac-transaction-service-update", factory);
        adapter.handleMessage(message);
    }

    @ServiceActivator(inputChannel = "receiverChannel")
    public void receiveFromQueue(Message<?> message) {
        List transactionData = (List) message.getPayload();
        System.out.println(transactionData);
    }
}
