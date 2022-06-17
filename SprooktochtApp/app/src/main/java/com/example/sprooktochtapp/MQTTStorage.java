package com.example.sprooktochtapp;

import android.telecom.CallAudioState;

import java.util.ArrayList;
import java.util.HashMap;

public class MQTTStorage implements MQTTCallBack{
    private HashMap<String,String> data;
    private ArrayList<MQTTCallBack> callbacks;

    public MQTTStorage() {
        data = new HashMap<>();
        callbacks = new ArrayList<>();
    }

    @Override
    public void receive(String topic, String data) {

    }

    public HashMap<String, String> getData() {
        return data;
    }

    public ArrayList<MQTTCallBack> getCallbacks() {
        return callbacks;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }

    public void setCallbacks(ArrayList<MQTTCallBack> callbacks) {
        this.callbacks = callbacks;
    }
}
