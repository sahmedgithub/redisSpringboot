package com.example.demo.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.redis.inbound.RedisQueueMessageDrivenEndpoint;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

@Configuration
@EnableIntegration
@IntegrationComponentScan("com.example.redis")
public class IntegrationConfig {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("localhost");
        configuration.setPort(6379);

        JedisClientConfiguration.JedisClientConfigurationBuilder j = JedisClientConfiguration.builder();
        j.connectTimeout(Duration.ofSeconds(60));
        GenericObjectPoolConfig poolingConfig = new GenericObjectPoolConfig();
        poolingConfig.setMaxIdle(10);
        poolingConfig.setMaxTotal(20);
        poolingConfig.setMaxWaitMillis(10000);
        poolingConfig.setTestOnBorrow(true);
        poolingConfig.setTestOnReturn(true);
        poolingConfig.setTestWhileIdle(true);
        j.usePooling().poolConfig(poolingConfig);

        return new JedisConnectionFactory(configuration, j.build());
    }

    @Bean
    public DirectChannel receiverChannel() {
        return new DirectChannel();
    }

    @Bean
    public RedisQueueMessageDrivenEndpoint consumerEndPoint() {
        RedisQueueMessageDrivenEndpoint endpoint = new RedisQueueMessageDrivenEndpoint("mac-transaction-service-update", jedisConnectionFactory());
        endpoint.setOutputChannelName("receiverChannel");
        return endpoint;
    }
}
