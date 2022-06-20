package com.example.sprooktochtapp;

import java.util.HashMap;

public interface MQTTService {
    HashMap<String, String> data = null;
    boolean subscribe(String topic);
    void disconnect();
    void unsubscribe(String topic);
    boolean publish(String topic, String payload);
}
