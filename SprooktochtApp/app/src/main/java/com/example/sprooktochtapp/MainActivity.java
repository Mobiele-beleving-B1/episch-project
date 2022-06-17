package com.example.sprooktochtapp;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MainActivity extends AppCompatActivity {

    private Button beginButton;
    private EditText nameInput;
    private ImageView homeImage;
    private ConstraintLayout layout;
    protected MQTTService service;
    protected Profile profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beginButton = (Button) findViewById(R.id.beginButton);
        nameInput = (EditText) findViewById(R.id.nameInput);
        layout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        //layout.setBackground(R.drawable.starbackground);
        nameInput.setHint("Voer uw naam in");
//        beginButton.setText("Begin uw avontuur");
        service = new MQTTService(this);
        beginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    profile = new Profile(nameInput.getText().toString(),service);
                    Intent intent = new Intent(getApplicationContext(), TutorialActivity.class);
                    intent.putExtra("profile",profile);
                    intent.putExtra("service",service);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("MyActivity::MyMethod", e.getMessage());
                }
            }
        });

    }
}