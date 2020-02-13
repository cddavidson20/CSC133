package com.gamescodeschool.ponggame;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import java.io.IOException;

/**
 * Loads and plays sounds.
 */
class Sounds {
    private SoundPool mSP;
    private int mBeepID = -1;
    private int mBoopID = -1;
    private int mBopID = -1;
    private int mMissID = -1;

    private float leftVolume = 1;
    private float rightVolume = 1;
    private int priority = 0;
    private int loop = 0;
    private float rate = 1;

    private Context context;

    /**
     * Loads the sounds and adds audio attributes.
     * @param context imports context to get assets.
     */
    Sounds(Context context) {
        this.context = context;

        // Prepare the SoundPool instance
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
    // Open each of the sound files in turn
    // and load them in to Ram ready to play
        try {
        AssetManager assetManager = context.getAssets();
        AssetFileDescriptor descriptor;

        descriptor = assetManager.openFd("beep.ogg");
        mBeepID = mSP.load(descriptor, 0);

        descriptor = assetManager.openFd("boop.ogg");
        mBoopID = mSP.load(descriptor, 0);

        descriptor = assetManager.openFd("bop.ogg");
        mBopID = mSP.load(descriptor, 0);

        descriptor = assetManager.openFd("miss.ogg");
        mMissID = mSP.load(descriptor, 0);

        } catch(IOException e) {
            Log.e("error", "failed to load sound files");
        }
    }

    void playBeep() {
        mSP.play(mBeepID, leftVolume, rightVolume, priority, loop, rate);
    }

    void playBoop() {
        mSP.play(mBoopID, leftVolume, rightVolume, priority, loop, rate);
    }

    void playBop() {
        mSP.play(mBopID, leftVolume, rightVolume, priority, loop, rate);
    }

    void playMiss() {
        mSP.play(mMissID, leftVolume, rightVolume, priority, loop, rate);
    }
}
