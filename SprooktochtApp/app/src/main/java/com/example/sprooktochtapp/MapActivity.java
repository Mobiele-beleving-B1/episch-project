package com.example.sprooktochtapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MapActivity extends AppCompatActivity {

    @SuppressLint("ResourceType")
    Button activity2Button;
    TextView selectedFairyTale;
    FairyTaleManager fairyTaleManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        bottomHomeImage = (ImageView) findViewById(R.drawable.john_lennon);
        activity2Button = (Button) findViewById(R.id.activity2Button);
        selectedFairyTale = (TextView) findViewById(R.id.selectedFairyTaleText);

                if(!selectedFairyTale.getText().equals("")){
                    selectedFairyTale.setText(drieBiggetjesButton.getFairyTaleText());
                }
            }

        });
        drieBiggetjesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    startActivity(new Intent(getApplicationContext(), DetailActivity.class));
                } catch (Exception e) {
                    Log.e("MyActivity::MyMethod", e.getMessage());
                }
            }
        });
    }
}