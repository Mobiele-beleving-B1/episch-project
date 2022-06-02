package com.example.sprooktochtapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    TextView sprookjeName;
    TextView sprookjeDescription;
    ImageView sprookjeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        FairyTaleManager.createFairyTales();

        sprookjeName = (TextView) findViewById(R.id.sprookjeName);
        sprookjeDescription = (TextView) findViewById(R.id.sprookjeDescription);
        sprookjeImage = (ImageView) findViewById(R.id.sprookjeImage);

        FairyTale selectedFairyTale = FairyTaleManager.getFairyTale(1);

        sprookjeName.setText(selectedFairyTale.getNameOfTale());
        sprookjeDescription.setText(selectedFairyTale.getTaleDescription());
    }
}