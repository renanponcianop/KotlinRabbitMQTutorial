package com.example.rabbitMQ

import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch

@Component
class Receiver(private var latch : CountDownLatch = CountDownLatch(1)) {

    fun receiveMessage(message : String) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    fun getLatch(): CountDownLatch {
        return latch;
    }
}