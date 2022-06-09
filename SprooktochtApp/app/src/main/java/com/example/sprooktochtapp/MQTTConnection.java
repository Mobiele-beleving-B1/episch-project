package com.example.sprooktochtapp;

import android.util.Log;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.UUID;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MQTTConnection {
    private final String BROKER_HOST_URL;
    private final String USERNAME;
    private final String PASSWORD;
    private final String CLIENT_ID = "MQTTExampleTryout_" + UUID.randomUUID().toString();
    private final int QUALITY_OF_SERVICE = 0;
    private ArrayList<String> subscribedTopics;
    private MqttAndroidClient client;
    private static final String LOGTAG = MainActivity.class.getName();

    public MQTTConnection(String BROKER_HOST_URL, String USERNAME, String PASSWORD) {
        this.BROKER_HOST_URL = BROKER_HOST_URL;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        connectToBroker();
    }

    public MQTTConnection() {
        BROKER_HOST_URL = "tcp://broker.hivemq.com:1883";
        USERNAME = "";
        PASSWORD = "";
        connectToBroker();
    }
    private void connectToBroker() {
        // Set up connection options for the connection to the MQTT broker
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(false);
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());
        // Add more options if necessary
        try {
            // Try to connect to the MQTT broker
            IMqttToken token = client.connect(options);
            // Set up callbacks for the result
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(LOGTAG, "MQTT client is now connected to MQTT broker");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.e(LOGTAG, "MQTT client failed to connect to MQTT broker: " +
                            exception.getLocalizedMessage());
                }
            });
        } catch (MqttException e) {
            Log.e(LOGTAG, "MQTT exception while connecting to MQTT broker, reason: " +
                    e.getReasonCode() + ", msg: " + e.getMessage() + ", cause: " + e.getCause());
            e.printStackTrace();
        }
    }

    public void disconnectFromBroker(MqttAndroidClient client) {
        try {
            // Try to disconnect from the MQTT broker
            IMqttToken token = client.disconnect();
            // Set up callbacks to handle the result
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(LOGTAG, "MQTT client is now disconnected from MQTT broker");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.e(LOGTAG, "MQTT failed to disconnect from MQTT broker: " +
                            exception.getLocalizedMessage());
                }
            });
        } catch (MqttException e) {
            Log.e(LOGTAG, "MQTT exception while disconnecting from MQTT broker, reason: " +
                    e.getReasonCode() + ", msg: " + e.getMessage() + ", cause: " + e.getCause());
            e.printStackTrace();
        }
    }

    public void publishMessage(String topic, String msg) {
        byte[] encodedPayload = new byte[0];
        try {
            // Convert the message to a UTF-8 encoded byte array
            encodedPayload = msg.getBytes("UTF-8");
            // Store it in an MqttMessage
            MqttMessage message = new MqttMessage(encodedPayload);
            // Set parameters for the message
            message.setQos(QUALITY_OF_SERVICE);
            message.setRetained(false);
            // Publish the message via the MQTT broker
            client.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            Log.e(LOGTAG, "MQTT exception while publishing topic to MQTT broker, msg: " + e.getMessage() +
                    ", cause: " + e.getCause());
            e.printStackTrace();
        }
    }

    public void subscribeToTopic(MqttAndroidClient client, final String topic) {
        try {
            // Try to subscribe to the topic
            IMqttToken token = client.subscribe(topic, QUALITY_OF_SERVICE);
            // Set up callbacks to handle the result
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(LOGTAG, "MQTT client is now subscribed to topic " + topic);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.e(LOGTAG, "MQTT failed to subscribe to topic " + topic + " because: " +
                            exception.getLocalizedMessage());
                }
            });
        } catch (MqttException e) {
            Log.e(LOGTAG, "MQTT exception while subscribing to topic on MQTT broker, reason: " +
                    e.getReasonCode() + ", msg: " + e.getMessage() + ", cause: " + e.getCause());
            e.printStackTrace();
        }
    }
    public void unsubscribeToTopic(final String topic) {
        try {
            // Try to unsubscribe to the topic
            IMqttToken token = client.unsubscribe(topic);
            // Set up callbacks to handle the result
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(LOGTAG, "MQTT client is now unsubscribed to topic " + topic);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.e(LOGTAG, "MQTT client failed to unsubscribe to topic " + topic + " because: " +
                            exception.getLocalizedMessage());
                }
            });
        } catch (MqttException e) {
            Log.e(LOGTAG, "MQTT exception while unsubscribing from topic on MQTT broker, reason: " +
                    e.getReasonCode() + ", msg: " + e.getMessage() + ", cause: " + e.getCause());
            e.printStackTrace();
        }
    }


}
