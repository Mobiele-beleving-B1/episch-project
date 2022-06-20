package com.example.sprooktochtapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.MenuItem;

public class DetailActivity extends AppCompatActivity {
    TextView fairyTaleName;
    TextView fairyLand;
    TextView gameDescription;
    ImageView fairyTaleImage;
    FairyTale selectedFairyTale;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String fairyTaleInfo = intent.getStringExtra("fairy_tale_info");
        this.selectedFairyTale = FairyTaleManager.getFairyTale(fairyTaleInfo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Details");
        // shows the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);


        FairyTaleManager.createFairyTales();

        fairyTaleImage = (ImageView) findViewById(R.id.fairyTaleImage);
        fairyLand = (TextView) findViewById(R.id.landTextView);
        fairyTaleName = (TextView) findViewById(R.id.fairyTaleName);
        gameDescription = (TextView) findViewById(R.id.fairyTaleDescription);



        fairyTaleName.setText(selectedFairyTale.getNameOfTale());
        fairyLand.setText(selectedFairyTale.getNameOfLand());
        gameDescription.setText(selectedFairyTale.getGameDescription());
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