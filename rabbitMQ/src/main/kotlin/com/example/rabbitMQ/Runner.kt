package com.example.rabbitMQ

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit


@Component
class Runner(
    private var rabbitTemplate: RabbitTemplate,
    private var receiver: Receiver
    ) : CommandLineRunner
{

    @Throws(Exception::class)
    override fun run(vararg args: String?) {
        println("Sending message...")
        rabbitTemplate.convertAndSend(
            RabbitMqApplication.topicExchangeName,
            "foo.bar.baz",
            "Hello from RabbitMQ!"
        )
        receiver!!.getLatch().await(10000, TimeUnit.MILLISECONDS)
    }
}