package com.felixzh;

import com.felixzh.SDKInterface.MQMessage;
import com.felixzh.SDKInterface.MQProducer;

import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
        ServiceLoader<MQProducer> mqProducers = ServiceLoader.load(MQProducer.class);
        for (MQProducer mqProducer : mqProducers) {
            mqProducer.start();
            mqProducer.send(new MQMessage<>());
            mqProducer.stop();
        }
    }
}
