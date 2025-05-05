package com.example.instagram;

public class NumberFormatter {

    public static String formatNumber(int number) {
        if (number >= 1_000_000) {
            float result = number / 1_000_000f;
            return (result % 1 == 0 ? String.format("%.0f", result) : String.format("%.1f", result)) + "M";
        } else if (number >= 1_000) {
            float result = number / 1_000f;
            return (result % 1 == 0 ? String.format("%.0f", result) : String.format("%.1f", result)) + "K";
        } else {
            return String.valueOf(number);
        }
    }
}

