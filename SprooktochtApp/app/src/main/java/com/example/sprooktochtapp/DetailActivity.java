package com.example.sprooktochtapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class DetailActivity extends AppCompatActivity {
    TextView fairyTaleName;
    TextView fairyTaleDescription;
    ImageView fairyTaleImage;
    FairyTale selectedFairyTale;
    Button testbutton;
    protected MQTTService service;
    protected Profile profile;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        service = (MQTTService) getIntent().getSerializableExtra("service");
        profile = (Profile) getIntent().getSerializableExtra("profile");
        Intent intent = getIntent();
        String fairyTaleInfo = intent.getStringExtra("fairy_tale_info");
        this.selectedFairyTale = FairyTaleManager.getFairyTale(fairyTaleInfo);
        testbutton = (Button) findViewById(R.id.button);
        testbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile.playGame(selectedFairyTale);
            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Details");
        // shows the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);


        FairyTaleManager.createFairyTales();

        fairyTaleName = (TextView) findViewById(R.id.fairyTaleName);
        fairyTaleDescription = (TextView) findViewById(R.id.fairyTaleDescription);
        fairyTaleImage = (ImageView) findViewById(R.id.fairyTaleImage);


        fairyTaleName.setText(selectedFairyTale.getNameOfTale());
        fairyTaleDescription.setText(selectedFairyTale.getTaleDescription());
        fairyTaleImage.setImageDrawable(ResourcesCompat.getDrawable(getResources(), selectedFairyTale.getImageOfTaleId(), null));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}