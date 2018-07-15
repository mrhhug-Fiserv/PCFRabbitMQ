package com.example.demo;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author michael
 */
@Configuration
public class AMQPConfiguration {
    
    //I thought this was clever ;)
    protected final String queueName = "Desmond_Llewelyn";
    
    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
	    CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
	    /*
	    CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
	    connectionFactory.setUsername("guest");
	    connectionFactory.setPassword("guest");
	    */
	    return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
	    return new RabbitAdmin(cachingConnectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
	    RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory());
	    template.setRoutingKey(this.queueName);
	    template.setQueue(this.queueName);
	    return template;
    }

    @Bean
    public Queue queueName() {
	    return new Queue(this.queueName);
    }
}
