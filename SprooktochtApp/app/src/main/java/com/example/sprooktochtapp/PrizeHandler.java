package com.example.sprooktochtapp;

import static android.content.ContentValues.TAG;

import android.util.Log;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

public class PrizeHandler implements Serializable {
    private double pointsGained;
    private double pointsTotal;
    private ArrayList<String> coupons;

    public PrizeHandler(double pointsGained, double pointsTotal) {
        this.pointsGained = pointsGained;
        this.pointsTotal = pointsTotal;
        coupons = new ArrayList<>();
    }

    public void setPointsGained(double pointsGained) {
        this.pointsGained = pointsGained;
    }

    public void setPointsTotal(double pointsTotal) {
        this.pointsTotal = pointsTotal;
    }

    public ArrayList<String> getCoupons() {
        return coupons;
    }

    public double getPointsGained() {
        return pointsGained;
    }

    public double getPointsTotal() {
        return pointsTotal;
    }

    public PrizeHandler(double pointsTotal) {
        this.pointsTotal = pointsTotal;
    }

    private boolean checkPoints() {
        if (pointsGained >= pointsTotal) {
            return true;
        }
        return false;
    }

    public void claimCoupon(Coupon coupon) {
        if (checkPoints()) {
            createCoupon(coupon);
            pointsGained -= 300;
        } else {
            Log.i("TAG", "claimCoupon: Insufficient points gained. Coupon cant be claimed.");
            return;
        }
    }

    private void createCoupon(Coupon coupon) {
//        coupons.add("Jouw coupon codes: \n");
//        if (coupons == null) {
//            coupons = new ArrayList<>();
//            Log.d(TAG, "createCoupon: Created new coupons arraylist");
//        }
        switch (coupon) {
            case DISCOUNT:
                coupons.add("30% korting in onze giftshops!: - " + "Code: K1" + generateCode());
                break;

            case PRIORITYPASS:
                coupons.add("Prioritypass coupon - " + "Code: P1" + generateCode());
                break;

            case SNACK:
                coupons.add("Snack coupon - " + "Code: S1" + generateCode());
                break;
        }
    }

    private String generateCode() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 7;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}
