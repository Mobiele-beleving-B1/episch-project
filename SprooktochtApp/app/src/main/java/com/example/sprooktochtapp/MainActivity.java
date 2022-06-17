package com.example.sprooktochtapp;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Button beginButton;
    private EditText nameInput;
    private ImageView homeImage;
    private ConstraintLayout layout;
    protected MQTTService service;
    protected MQTTStorage storage;
    protected MQTTProfile MQTTProfile;
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
        storage = new MQTTStorage();

        service = new MQTTService(this,storage);
        beginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = nameInput.getText().toString();
                    Log.d("name",name);
                    MQTTProfile = new MQTTProfile(name,service,storage);
                    Intent intent = new Intent(getApplicationContext(), TutorialActivity.class);
                    intent.putExtra("profile", MQTTProfile);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("MyActivity::MyMethod", e.getMessage());
                }
            }
        });

    }
}