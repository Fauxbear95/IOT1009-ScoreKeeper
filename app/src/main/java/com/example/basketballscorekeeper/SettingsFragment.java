package com.example.basketballscorekeeper;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment {


    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

    }
}