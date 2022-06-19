package com.example.sprooktochtapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

public class TutorialActivity extends FragmentActivity {
    private Button skipButton;
    /**
     * the paper widget, which manages animation and allows switching horizontally
     * between wizard steps
     */
    private ViewPager tutorialPager;

    /**
     * the pager adapter, which sends the pages to view pager widget
     */
    private TutorialPagerAdapter pagerAdapter;
    protected MQTTProfile MQTTProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        PopUpClass popUpClass = new PopUpClass();

        MQTTProfile = (MQTTProfile) getIntent().getSerializableExtra("profile");
        //Instantiate button with intent
        skipButton = (Button) findViewById(R.id.skipButton);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (skipButton.getText().equals(App.getAppResources().getString(R.string.skip))) {
                    popUpClass.showPopupWindow(v);
                } else if (skipButton.getText().equals(App.getAppResources().getString(R.string.go))) {
                    Intent intent = new Intent(TutorialActivity.this, MapActivity.class);
                    intent.putExtra("profile",MQTTProfile);
                    startActivity(intent);
                }
            }
        });

        // Instantiate a ViewPager and a PagerAdapter
        tutorialPager = (ViewPager) findViewById(R.id.pager);

        /**
         * changes button text based on page number
         */
        tutorialPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                if (position == 2) {
                    skipButton.setText(R.string.go);
                } else {
                    skipButton.setText(R.string.skip);
                }
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(tutorialPager, true);

        pagerAdapter = new TutorialPagerAdapter(getSupportFragmentManager());
        pagerAdapter.add(new Page1());
        pagerAdapter.add(new Page2());
        pagerAdapter.add(new Page3());
        tutorialPager.setAdapter(pagerAdapter);
    }



    @Override
    public void onBackPressed() {
        if (tutorialPager.getCurrentItem() == 0) {
            // If the user is on the first step, let the system handle the Back button.
            // This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            //Otherwise, select previous step
            tutorialPager.setCurrentItem(tutorialPager.getCurrentItem() - 1);
        }
    }

    /**
     * a pager adapter that represents a number of ScreenSlidePageFragment objects,
     * equal to NUM_PAGES
     */
    private class TutorialPagerAdapter extends FragmentStatePagerAdapter {


        /**
         * the number of pages to show in the tutorial
         */
        private static final int NUM_PAGES = 5;
        private final List<Fragment> fragments = new ArrayList<>();


        public TutorialPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void add(Fragment fragment) {
            fragments.add(fragment);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    public class PopUpClass {

        public PopUpClass() {
        }


        //PopupWindow display method

        public void showPopupWindow(final View view) {

            // inflate layout of popup window
            LayoutInflater inflater = (LayoutInflater)
                    view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.popup_window, null);

            // create popup window
            int width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
            int height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true; //
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            // initialize elements of window
            Button yesButton = (Button) popupView.findViewById(R.id.yesButton);
            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(TutorialActivity.this, MapActivity.class);
                    intent.putExtra("profile",MQTTProfile);
                    startActivity(intent);
                    popupWindow.dismiss();
                }
            });

            Button noButton = (Button) popupView.findViewById(R.id.noButton);
            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}