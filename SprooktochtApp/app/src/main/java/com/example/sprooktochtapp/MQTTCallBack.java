package com.example.sprooktochtapp;

import java.util.HashMap;

public interface MQTTCallBack {
    public void receive(String topic, String data);
}
