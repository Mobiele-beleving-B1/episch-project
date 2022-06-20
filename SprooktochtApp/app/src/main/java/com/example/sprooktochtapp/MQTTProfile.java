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
    private static long points;
    private HashMap<String, String> data;


    public MQTTProfile(String name) {
        games.put("biggenSpel", 0.04);
        this.name = name;
        this.id = MqttClient.generateClientId();
        this.points = 0;
        this.data = new HashMap<>();
        for (String s : games.keySet()) {
            data.put(mainTopic + "gameData/" + s + "/Score", "");
            Log.d("MQTT", "added to topics: " + mainTopic + "gameData/" + s + "/Score");
        }
    }

    public Set<String> getTopics() {
        return data.keySet();
    }


    private void gameData(String gameName, String dataType, String data) {
        if (dataType.equals("Score") && data.startsWith(id)) {
            int score = Integer.parseInt(data.replace(id, ""));
            for (String game : games.keySet()) {
                Log.d("mqtt", game);
                if (game.equals(gameName)) {
                    points += score * games.get(game);
                    savePoint(points);
                    Log.d("MQTT","Total points: "+points);
                }
            }
        }
    }

    private void savePoint(long points) {

    }

    private void checkForAchievements(JsonObject game, int score) {

    }

    public String getId() {
        return id;
    }

    public long getPoints() {
        return points;
    }

    @Override
    public void receive(String topic, String data) {
        String topic2 = topic.replace(mainTopic, "");

        Log.d("MQTT", topic + "--" + data);
        if (topic2.startsWith("gameData")) {
            topic2 = topic2.replace("gameData/", "");
            for (String s : this.data.keySet()) {
                String s2 = s.replace(mainTopic + "gameData/","");
                if (topic2.equals(s2)) {
                    this.data.put(topic,data);
                    String[] game = topic2.split("/");
                    gameData(game[0], game[1], data);
                }
            }
        }
    }
}
