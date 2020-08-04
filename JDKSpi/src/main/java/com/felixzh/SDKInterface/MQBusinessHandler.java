package com.felixzh.SDKInterface;

public interface MQBusinessHandler {
    public boolean doBusiness(MQMessage<?> message);
}
