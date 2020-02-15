package com.example.demo.redis;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface MessageGateway {
    @Gateway(requestChannel = "inputChannel")
    <T> void sendMessage(T request);
}
