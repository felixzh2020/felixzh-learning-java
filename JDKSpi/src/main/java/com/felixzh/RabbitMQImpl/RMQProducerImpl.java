package com.felixzh.RabbitMQImpl;

import com.felixzh.SDKInterface.MQMessage;
import com.felixzh.SDKInterface.MQProducer;

public class RMQProducerImpl implements MQProducer {
    @Override
    public void start() {
        System.out.println("rabbitmq producer start ... ");
    }

    @Override
    public void stop() {
        System.out.println("rabbitmq producer stop ... ");
    }

    @Override
    public boolean send(MQMessage<?> message) {
        System.out.println("rabbitmq producer send ... ");
        return true;
    }
}
