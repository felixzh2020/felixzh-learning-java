package com.felixzh.SDKInterface;

public interface MQProducer {
    public void start();
    public void stop();
    public boolean send(MQMessage<?> message);
}
