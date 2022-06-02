package com.example.sprooktochtapp;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button beginButton;
    private EditText nameInput;
    private ImageView homeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beginButton = (Button) findViewById(R.id.beginButton);
        nameInput = (EditText) findViewById(R.id.nameInput);
        homeImage = (ImageView) findViewById(R.id.homeImage);

        beginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(getApplicationContext(), TutorialActivity.class));
                }
                catch (Exception e) {
                    Log.e("MyActivity::MyMethod", e.getMessage());
                }
            }
        });

    }
}