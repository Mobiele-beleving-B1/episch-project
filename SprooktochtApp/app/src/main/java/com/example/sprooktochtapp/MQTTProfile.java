package com.example.sprooktochtapp;


import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

public class MQTTProfile implements MQTTCallBack {

    private String name;
    private String id;
    private final String mainTopic = "avanstibreda/ti/1.4/B1/sprookTocht/";
    private HashMap<String, Double> games = new HashMap<>();
    private long points;
    private HashMap<String, String> data;


    public MQTTProfile(String name) {
        games.put("biggenspel", 0.04);
        this.name = name;
        this.id = MqttClient.generateClientId();
        this.points = 0;
        this.data = new HashMap<>();
        for (String s : games.keySet()) {
            data.put(mainTopic + "gameData/" + s + "/Score", "");
            Log.d("MQTT","added to topics: "+ mainTopic + "gameData/" + s + "/Score");
        }
    }
    public Set<String> getTopics(){
        return  data.keySet();
    }


    private void gameData(String gameName, String dataType, String data) {
        Log.d("mqtt", "werkt tot hier");
        if (dataType.equals("Score") && data.startsWith(id)) {
            int score = Integer.parseInt(data.replace(id, ""));
            for (String game : games.keySet()) {
                if (game.equals(gameName)) {
                    points += score * games.get(game);
                }

            }
        }
    }

    private void checkForAchievements(JsonObject game, int score) {

    }

    public long getPoints() {
        return points;
    }

    @Override
    public void receive(String topic, String data) {
        topic = topic.replace(mainTopic, "");
        String[] dataNames = (String[]) this.data.keySet().toArray();
        for (int i = 0; i < this.data.keySet().size(); i++) {
            if (topic.startsWith(dataNames[i])){
                if (topic.startsWith("gameData")) {
                    String[] game = topic.split("/");
                    gameData(game[1], game[2], data);
                }
            }
        }
    }

}
