package com.example.sprooktochtapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;

public class TutorialActivity extends FragmentActivity {

    /**
     * the paper widget, which manages animation and allows switching horizontally
     * between wizard steps
     */
    private ViewPager tutorialPager;

    /**
     * the pager adapter, which sends the pages to view pager widget
     */
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        // Instantiate a ViewPager and a PagerAdapter
        tutorialPager = (ViewPager) findViewById(R.id.pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(tutorialPager, true);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
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
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        /**
         * TODO: make a list of Fragments containing tutorial info
         */
        private List<Fragment> fragments;

        /**
         * the number of pages to show in the tutorial
         * TODO: replace with list of Fragments
         */
        private static final int NUM_PAGES = 5;

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * TODO: return element of list of ScreenSlidePageFragment
         */
        @Override
        public Fragment getItem(int i) {
            return new ScreenSlidePageFragment();
        }

        /**
         * TODO: return list of Fragments size
         */
        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}