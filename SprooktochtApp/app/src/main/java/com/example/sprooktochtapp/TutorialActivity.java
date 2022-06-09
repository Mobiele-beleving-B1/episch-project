package com.example.sprooktochtapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
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
    private TutorialPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        // Instantiate a ViewPager and a PagerAdapter
        tutorialPager = (ViewPager) findViewById(R.id.pager);
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
}