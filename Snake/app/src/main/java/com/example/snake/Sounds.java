package com.example.snake;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import java.io.IOException;

public class Sounds {
    private SoundPool mSP;
    private int mEat_good_ID = -1;
    private int mEat_bad_ID = -1;
    private int mCrashID = -1;

    private float leftVolume = 1;
    private float rightVolume = 1;
    private int priority = 0;
    private int loop = 0;
    private float rate = 1;

    private Context context;

    Sounds(Context context) {
        this.context = context;

        // Initialize the SoundPool
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            mSP = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            mSP = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }

        loadSounds();
    }

    private void loadSounds() {
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            // Prepare the sounds in memory
            descriptor = assetManager.openFd("get_good_apple.ogg");
            mEat_good_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("get_bad_apple.ogg");
            mEat_bad_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("snake_death.ogg");
            mCrashID = mSP.load(descriptor, 0);

        } catch (IOException e) {
            Log.e("error", "failed to load sound files");
        }
    }

    void playEatGood() {
        mSP.play(mEat_good_ID, leftVolume, rightVolume, priority, loop, rate);
    }

    void playEatBad() {
        mSP.play(mEat_bad_ID, leftVolume, rightVolume, priority, loop, rate);
    }

    void playCrash() {
        mSP.play(mCrashID, leftVolume, rightVolume, priority, loop, rate);
    }
}
