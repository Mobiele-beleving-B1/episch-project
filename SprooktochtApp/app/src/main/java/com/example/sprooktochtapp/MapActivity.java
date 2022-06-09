package com.example.sprooktochtapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MapActivity extends AppCompatActivity {
    Button activity2Button;
    TextView selectedFairyTale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        activity2Button = (Button) findViewById(R.id.activity2Button);
        selectedFairyTale = (TextView) findViewById(R.id.selectedFairyTaleText);

    }
}