package com.example.sprooktochtapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ScreenSlidePageFragment extends Fragment {

    public ScreenSlidePageFragment() {
    }

    /**
     * creates View from fragment layout
     * @param inflater : instantiate fragment_screen_slide_page to View object
     * @param container : contain inflated fragment_screen_slide_page
     * @param savedInstanceState : save data from activity
     * @return ViewGroup that contains inflated fragment_screen_slide_page
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
    }
}
