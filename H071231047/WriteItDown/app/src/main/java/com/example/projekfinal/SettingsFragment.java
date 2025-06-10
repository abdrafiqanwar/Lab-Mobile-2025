package com.example.projekfinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private static final String THEME_PREFS = "theme_prefs";
    private static final String THEME_KEY = "theme_key";

    // Theme constants
    private static final int LIGHT_MODE = 1;
    private static final int DARK_MODE = 2;
    private static final int SYSTEM_MODE = 3;

    private RadioGroup radioGroupTheme;
    private RadioButton radioLight, radioDark, radioSystem;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize views
        radioGroupTheme = view.findViewById(R.id.radio_group_theme);
        radioLight = view.findViewById(R.id.radio_light);
        radioDark = view.findViewById(R.id.radio_dark);
        radioSystem = view.findViewById(R.id.radio_system);

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences(THEME_PREFS, Context.MODE_PRIVATE);

        // Set the selected radio button based on saved theme
        int savedTheme = sharedPreferences.getInt(THEME_KEY, SYSTEM_MODE);
        switch (savedTheme) {
            case LIGHT_MODE:
                radioLight.setChecked(true);
                break;
            case DARK_MODE:
                radioDark.setChecked(true);
                break;
            case SYSTEM_MODE:
                radioSystem.setChecked(true);
                break;
        }

        // Set up the radio button listener
        radioGroupTheme.setOnCheckedChangeListener((group, checkedId) -> {
            int themeMode;

            if (checkedId == R.id.radio_light) {
                themeMode = LIGHT_MODE;
            } else if (checkedId == R.id.radio_dark) {
                themeMode = DARK_MODE;
            } else {
                themeMode = SYSTEM_MODE;
            }

            setTheme(themeMode);
        });

        return view;
    }

    private void setTheme(int themeMode) {
        // Save the selected theme to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(THEME_KEY, themeMode);
        editor.apply();

        // Show toast message to inform user
        String message = "";
        switch (themeMode) {
            case LIGHT_MODE:
                message = "Light theme applied";
                break;
            case DARK_MODE:
                message = "Dark theme applied";
                break;
            case SYSTEM_MODE:
                message = "System theme applied";
                break;
        }
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();

        // Use MainActivity's method to apply theme instead of applying it directly
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setAppTheme(themeMode);
        }
    }
}