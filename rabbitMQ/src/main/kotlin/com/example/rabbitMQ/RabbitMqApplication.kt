package com.example.rabbitMQ

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.util.concurrent.CountDownLatch


@SpringBootApplication
class RabbitMqApplication() {

	companion object {
		const val topicExchangeName: String = "spring-boot-exchange"
		const val queueName: String = "spring-boot"
	}

	@Bean
	fun queue(): Queue {
		return Queue(queueName, false)
	}

	@Bean
	fun exchange(): TopicExchange {
		return TopicExchange(topicExchangeName)
	}

	@Bean
	fun binding(queue: Queue?, exchange: TopicExchange?): Binding {
		return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#")
	}

	@Bean
	fun container(
		connectionFactory: ConnectionFactory,
		listenerAdapter: MessageListenerAdapter?
	): SimpleMessageListenerContainer? {
		val container = SimpleMessageListenerContainer()
		container.connectionFactory = connectionFactory
		container.setQueueNames(queueName)
		container.setMessageListener(listenerAdapter!!)
		return container
	}

	@Bean
	fun listenerAdapter(receiver: Receiver?): MessageListenerAdapter {
		return MessageListenerAdapter(receiver, "receiveMessage")
	}

}

fun main(args: Array<String>) {
	runApplication<RabbitMqApplication>(*args)
}