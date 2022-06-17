package com.example.sprooktochtapp;


import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

public class MQTTProfile implements MQTTCallBack {

    private String name;
    private String id;
    private transient MQTTService service;
    private final String mainTopic = "avanstibreda/ti/1.4/B1/sprookTocht/";
    private ArrayList<String> topics;
    private HashMap<String, Double> games = new HashMap<>();
    private MQTTStorage storage;
    private long points;


    public MQTTProfile(String name, MQTTService service, MQTTStorage storage) {
        games.put("biggenspel", 0.04);
        this.name = name;
        this.storage = storage;
        this.id = MqttClient.generateClientId();
        this.points = 0;
        service.listen(this);
        reInitialise(service);

    }

    public MQTTStorage getStorage() {
        return storage;
    }

    public void reInitialise(MQTTService service) {
        this.service = service;
        for (String gameName : games.keySet()) {
            service.subscribe(mainTopic + "gameData/" + gameName + "/Score");
        }
    }

    private void gameData(String gameName, String dataType, String data) {
        Log.d("mqtt", "werkt tot hier");
        if (dataType.equals("Score") && data.startsWith(id)) {
            int score = Integer.parseInt(data.replace(id, ""));
            for (String games : new String[]{"biggenSpel"}) {
                if (games.equals(gameName)) {
                    points += score;
                }

            }
        }
    }

    private void checkForAchievements(JsonObject game, int score) {

    }


    @Override
    public void receive(String topic, String data) {
        topic = topic.replace(mainTopic, "");
        if (this.topics.contains(topic)) {
            if (topic.startsWith("gameData")) {
                String[] game = topic.split("/");
                gameData(game[1], game[2], data);
            }
        }
    }

    public void playGame(FairyTale fairyTale) {
        if (!fairyTale.getGameNames()[0].isEmpty()) {
            service.publish(mainTopic + "playerData" + fairyTale.getGameNames()[0], id);
        }
    }
}
