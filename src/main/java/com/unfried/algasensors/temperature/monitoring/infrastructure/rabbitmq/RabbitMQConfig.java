package com.unfried.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String FANOUT_EXCHANGE_NAME = "temperature-processing.temperature-received.v1.e";
	public static final String QUEUE_NAME = "temperature-monitoring.process-temperature.v1.q";

	@Bean
	public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
		return new Jackson2JsonMessageConverter(objectMapper);
	}

	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	public Queue queue() {
		return QueueBuilder.durable(QUEUE_NAME).build();
	}

	public FanoutExchange exchange() {
		return ExchangeBuilder.fanoutExchange(FANOUT_EXCHANGE_NAME).build();
	}

	@Bean
	public Binding binding() {
		return BindingBuilder.bind(queue()).to(exchange());
	}
}
