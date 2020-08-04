package com.felixzh.KafkaImpl;

import com.felixzh.SDKInterface.MQMessage;
import com.felixzh.SDKInterface.MQProducer;

public class KMQProducerImpl implements MQProducer {
    @Override
    public void start() {
        System.out.println("kafka producer start ... ");
    }

    @Override
    public void stop() {
        System.out.println("kafka producer stop ... ");
    }

    @Override
    public boolean send(MQMessage<?> message) {
        System.out.println("kafka producer send ... ");
        return true;
    }
}
