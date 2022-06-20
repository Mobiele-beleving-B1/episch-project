package com.example.sprooktochtapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MapActivity extends AppCompatActivity {
    private Map<Button, String> buttonMap;
    private String selectedFairyTale;
    protected MQTTProfile MQTTProfile;
    private Button backButton;
    private ImageView throphyImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        throphyImage = (ImageView) findViewById(R.id.throphyImage);

        MQTTProfile = (MQTTProfile) getIntent().getSerializableExtra("profile");
        this.buttonMap = new HashMap<>();
        TextView fairyTaleName = (TextView) findViewById(R.id.activeSprookje);
        fairyTaleName.setText("Kies een locatie");
        Button infoButton = (Button) findViewById(R.id.infoButton);
        backButton = (Button) findViewById(R.id.backButton);

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                    intent.putExtra("profile", MQTTProfile);
                    intent.putExtra("fairy_tale_info", selectedFairyTale);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("MyActivity::MyMethod", e.getMessage());
                }
            }
        });
        throphyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(getApplicationContext(), PrizeActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("MyActivity::MyMethod", e.getMessage());
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(getApplicationContext(), TutorialActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("MyActivity::MyMethod", e.getMessage());
                }
            }
        });
        /**
         * Put in the buttons with the corresponding fairy tale
         */
        this.buttonMap.put((Button) findViewById(R.id.drieBiggetjesButton),
                FairyTaleManager.getFairyTale(0).getNameOfTale());
        this.buttonMap.put((Button) findViewById(R.id.activity2Button),
                FairyTaleManager.getFairyTale(0).getNameOfTale());


        for (Button button : buttonMap.keySet()) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedFairyTale = buttonMap.get(button);
                    if (selectedFairyTale != null) {
                        fairyTaleName.setText(selectedFairyTale);
                    }
                }
            });
        }

    }


}