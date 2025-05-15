package com.example.library.button;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.library.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SoundPlayer {
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void playClickSound(Context context) {
        executor.execute(() -> {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.clickhome);
            if (mediaPlayer != null) {
                mediaPlayer.setOnCompletionListener(MediaPlayer::release);
                mediaPlayer.start();
            }
        });
    }

    public void AddBookClickSound(Context context) {
        executor.execute(() -> {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.click2);
            if (mediaPlayer != null) {
                mediaPlayer.setOnCompletionListener(MediaPlayer::release);
                mediaPlayer.start();
            }
        });
    }
}