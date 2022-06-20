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
    TextView prizeTitle, prizeDesc, couponList, pointsText, snackDesc, giftShopDesc, priorityPassDesc, snackPrizeTitle, priorityPassTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prize);

        giftShopDesc = (TextView) findViewById(R.id.giftShopPrizeDesc);
        priorityPassDesc = (TextView) findViewById(R.id.priorityPassPrizeDesc);
        snackDesc = (TextView) findViewById(R.id.snackPrizeDesc);
        couponList = (TextView) findViewById(R.id.couponTextView);
        pointsText = (TextView) findViewById(R.id.pointsText);
        prizeTitle = (TextView) findViewById(R.id.prizeScreenTitle);
        prizeHandler = new PrizeHandler(900, 300);
        claimGiftShopButton = (Button) findViewById(R.id.giftShopPrizeButton);
        claimSnackButton = (Button) findViewById(R.id.snackPrizeButton);
        claimPriorityPassButton = (Button) findViewById(R.id.priorityPassPrizeButton);
        snackPrizeTitle = (TextView) findViewById(R.id.snackPrizeTitle);
        priorityPassTitle = (TextView) findViewById(R.id.priorityPassPrizeTitle);
        giftShopDesc.setText("Een 30% korting voucher voor die u naar keuze mag toepassen bij een aankoop in een van onze wonderbaarlijke gift shops!");
        snackDesc.setText("Een gratis snack bij uw zak friet die u kan aanschaffen bij een van onze smulkramen!");
        priorityPassDesc.setText("Hiermee kan u bij een balie een pas verzilveren waarmee u bij 2 attracties niet hoeft te wachten in de rij!");
        prizeTitle.setText("Coupon winkel");
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