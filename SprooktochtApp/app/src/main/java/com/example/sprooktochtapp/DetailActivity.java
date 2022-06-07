package com.example.sprooktochtapp;

import android.support.v7.app.ActionBar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);


        FairyTaleManager.createFairyTales();

        fairyTaleName = (TextView) findViewById(R.id.fairyTaleName);
        fairyTaleDescription = (TextView) findViewById(R.id.fairyTaleDescription);
        fairyTaleImage = (ImageView) findViewById(R.id.fairyTaleImage);

        FairyTale selectedFairyTale = FairyTaleManager.getFairyTale(1);

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