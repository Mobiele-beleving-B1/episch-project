package com.example.sprooktochtapp;

import java.io.Serializable;
import java.util.HashMap;

public interface  MQTTCallBack extends Serializable {
    public void receive(String topic, String data);
}
