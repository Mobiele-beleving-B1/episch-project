package com.example.sprooktochtapp;


import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttClient;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

public class Profile implements MQTTCallBack, Serializable {

    private String name;
    private String id;
    private MQTTService service;
    private final String mainTopic = "avanstibreda/ti/1.4/B1/sprookTocht/";
    private ArrayList<String> topics;
    private long points;

    JsonObject root;



    public Profile(String name, MQTTService service) {
        this.name = name;
        this.id = MqttClient.generateClientId();
        this.service = service;
        this.points = 0;
        service.listen(this);
        JsonReader reader;
        InputStream stream = getClass().getClassLoader().getResourceAsStream("Games.json");
        reader = Json.createReader(stream);
        root = reader.readObject();
        topics = new ArrayList<>();
        for (JsonValue games : root.getJsonArray("games")) {
            JsonObject game = (JsonObject) games;
            String gameName = game.getString("gameName");
            service.subscribe(mainTopic + "gameData/"+ gameName + "/Score");
        }
    }
    private void gameData(String gameName,String dataType, String data){
        Log.d("mqtt","werkt tot hier");
        if (dataType.equals("Score") && data.startsWith(id)){
            int score = Integer.parseInt(data.replace(id,""));
            for (JsonValue games : root.getJsonArray("games")) {
                JsonObject game = (JsonObject) games;
                if (game.getString("gameName").equals(gameName)){
                    points += score * game.getInt("scoreMulteplier");
                    checkForAchievements(game,score);
                }
            }
        }
    }

    private void checkForAchievements(JsonObject game, int score) {

    }


    @Override
    public void receive(String topic, String data) {
        topic = topic.replace(mainTopic,"");
        if (this.topics.contains(topic)){
            if (topic.startsWith("gameData")){
                String[] game = topic.split("/");
                gameData(game[1],game[2],data);
            }
        }
    }

    public void playGame(FairyTale fairyTale) {
        if (!fairyTale.getGameNames()[0].isEmpty()){
            service.publish(mainTopic+"playerData"+fairyTale.getGameNames()[0],id);
        }
    }
}
