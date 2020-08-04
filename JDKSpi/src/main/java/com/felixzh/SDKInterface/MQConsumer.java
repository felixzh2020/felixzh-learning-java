package com.felixzh.SDKInterface;

public interface MQConsumer {
    public void start();

    public void stop();

    public void subscribe(String topic, MQBusinessHandler topicHandler);

    public void unsubscribe(String topic);
}
