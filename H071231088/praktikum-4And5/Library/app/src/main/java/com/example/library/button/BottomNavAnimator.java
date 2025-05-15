package com.example.library.button;

import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavAnimator {

    public static void animate(BottomNavigationView bottomNavigationView, int selectedItemId) {
        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
            int itemId = bottomNavigationView.getMenu().getItem(i).getItemId();
            View view = bottomNavigationView.findViewById(itemId);

            if (view != null) {
                if (itemId == selectedItemId) {
                    view.animate()
                            .scaleX(1.2f)
                            .scaleY(1.2f)
                            .translationY(-12)
                            .setDuration(300)
                            .setInterpolator(new DecelerateInterpolator())
                            .start();
                } else {
                    view.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .translationY(0)
                            .setDuration(300)
                            .setInterpolator(new DecelerateInterpolator())
                            .start();
                }
            }
        }
    }
}
