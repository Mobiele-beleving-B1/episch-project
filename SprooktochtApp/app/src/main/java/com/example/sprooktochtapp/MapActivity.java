package com.example.sprooktochtapp;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;

public class MapActivity extends AppCompatActivity {
    ImageView bottomHomeImage;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        bottomHomeImage = (ImageView) findViewById(R.drawable.john_lennon);

    }
}