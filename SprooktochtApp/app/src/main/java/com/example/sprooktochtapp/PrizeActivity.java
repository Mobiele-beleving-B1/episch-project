package com.example.sprooktochtapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PrizeActivity extends AppCompatActivity {
    PrizeHandler prizeHandler;
    Button claimGiftShopButton, claimSnackButton, claimPriorityPassButton;
    String couponString;
    TextView prizeTitle, prizeDesc, couponList, pointsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prize);

        couponList = (TextView) findViewById(R.id.couponTextView);
        pointsText = (TextView) findViewById(R.id.pointsText);
        prizeHandler = new PrizeHandler(900, 300);
        claimGiftShopButton = (Button) findViewById(R.id.giftShopPrizeButton);
        claimSnackButton = (Button) findViewById(R.id.snackPrizeButton);
        claimPriorityPassButton = (Button) findViewById(R.id.priorityPassPrizeButton);


        claimSnackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    prizeHandler.claimCoupon(Coupon.SNACK);
                    couponString = "";
                    if (prizeHandler.getCoupons().size() > 0) {
                        for (String coupon : prizeHandler.getCoupons()) {
                            couponString += coupon + "\n";
                            couponList.setText(couponString);
                        }
                    }
                } catch (Exception e) {
                    Log.e("MyActivity::MyMethod", e.getMessage());
                }
                update();
            }
        });

        claimPriorityPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    prizeHandler.claimCoupon(Coupon.PRIORITYPASS);
                    couponString = "";
                    if (prizeHandler.getCoupons().size() > 0) {
                        for (String coupon : prizeHandler.getCoupons()) {
                            couponString += coupon + "\n";
                            couponList.setText(couponString);
                        }
                    }
                } catch (Exception e) {
                    Log.e("MyActivity::MyMethod", e.getMessage());
                }
                update();
            }
        });
        claimGiftShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    prizeHandler.claimCoupon(Coupon.DISCOUNT);
                    couponString = "";
                    if (prizeHandler.getCoupons().size() > 0) {
                        for (String coupon : prizeHandler.getCoupons()) {
                            couponString += coupon + "\n";
                            couponList.setText(couponString);
                        }
                    }
                } catch (Exception e) {
                    Log.e("MyActivity::MyMethod", e.getMessage());
                }
                update();
            }
        });
    }

    private void update() {
        pointsText.setText("Jouw puntend: " + prizeHandler.getPointsGained() + "/" + prizeHandler.getPointsTotal());
    }

}