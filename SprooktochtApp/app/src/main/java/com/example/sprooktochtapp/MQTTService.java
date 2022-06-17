package com.example.sprooktochtapp;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class MQTTService implements Serializable {
    private MqttAndroidClient client;
    private int qos = 0;
    private HashMap<String,String>data;
    private ArrayList<MQTTCallBack> callbacks;
    public MQTTService(AppCompatActivity activity) {
        data = new HashMap<>();
        String clientId = MqttClient.generateClientId();
        client =
                new MqttAndroidClient(activity.getApplicationContext(), "tcp://broker.hivemq.com:1883",
                        clientId);
        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d("TAG", "onSuccess");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("TAG", "onFailure");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                if (data.containsKey(topic)){
                    String text = String.valueOf(message.getPayload());
                    data.put(topic,text);
                    for (MQTTCallBack callback : callbacks) {
                        callback.receive(topic,text);
                    }
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
    public boolean publish(String topic,String payload){
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);
            return true;
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean subscribe(MQTTCallBack callBack,String topic){
        if(!callbacks.contains(callBack)){
            callbacks.add(callBack);
        }
        return subscribe(topic);
    }
    public boolean subscribe(String topic){
        try {
            IMqttToken subToken = client.subscribe(topic, qos);
            final boolean[] succes = {false};
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    data.put(topic,"");
                    succes[0] = true;
                    // The message was published
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    succes[0] = false;
                    // The subscription could not be performed, maybe the user was not
                    // authorized to subscribe on the specified topic e.g. using wildcards

                }
            });
            return succes[0];
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }

    }
    public void listen(MQTTCallBack callBack){
        if(!callbacks.contains(callBack)){
            callbacks.add(callBack);
        }
    }


    public HashMap<String,String> getData(){
        return data;
    }
    public void disconnect(){
        try {
            IMqttToken token = client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void unsubscribe(String topic){
        try {
            IMqttToken token = client.unsubscribe(topic);
            data.remove(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
