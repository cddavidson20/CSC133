package csc133.towerdefense.game;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.io.IOException;

public class SoundEngine {
    // for playing sound effects
    private SoundPool sp;
    private int click_id;
    private int win_id;
    private int lose_id;
    private int towerPlaced_id;
    private int machineGun_id;
    private int notPlaceable_id;
    private int loseLife_id;
    private int enemyKilled_id;
    private static boolean initialized  = false;
    private static SoundEngine se;

    public static SoundEngine getSoundEngine() {
        if (initialized) return se;
        initialized = true;
        se = new SoundEngine();
        return se;
    }

    private SoundEngine(){
        Context c = GameEngine.context;
        // Initialize the SoundPool
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes =
                    new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes
                                    .CONTENT_TYPE_SONIFICATION)
                            .build();
            sp = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
        try {
            AssetManager assetManager = c.getAssets();
            AssetFileDescriptor descriptor;

            // Prepare the sounds in memory
            descriptor = assetManager.openFd("click.wav");
            click_id = sp.load(descriptor, 0);
            descriptor = assetManager.openFd("win.wav");
            win_id = sp.load(descriptor, 0);
            descriptor = assetManager.openFd("lose.wav");
            lose_id = sp.load(descriptor, 0);
            descriptor = assetManager.openFd("tower_placed.wav");
            towerPlaced_id = sp.load(descriptor, 0);
            descriptor = assetManager.openFd("machine_gun.wav");
            machineGun_id = sp.load(descriptor, 0);
            descriptor = assetManager.openFd("not_placeable.wav");
            notPlaceable_id = sp.load(descriptor, 0);
            descriptor = assetManager.openFd("lose_life.wav");
            loseLife_id = sp.load(descriptor, 0);
            descriptor = assetManager.openFd("enemy_killed.wav");
            enemyKilled_id = sp.load(descriptor, 0);
        } catch (IOException e) {
            // Error
        }
    }



    void playClick(){
        sp.play(click_id,1, 1, 0, 0, 1);
    }
    void playWin(){
        sp.play(win_id,1, 1, 0, 0, 1);
    }
    void playLose() {
        sp.play(lose_id,1, 1, 0, 0, 1);
    }
    void playTowerPlaced() {
        sp.play(towerPlaced_id,1, 1, 0, 0, 1);
    }
    public void playMachineGun() {
        sp.play(machineGun_id,0.4f, 0.4f, 0, 0, 1);
    }
    void playNotPlaceable() {
        sp.play(notPlaceable_id,1, 1, 0, 0, 1);
    }
    public void playLoseLife() {
        sp.play(loseLife_id,1, 1, 0, 0, 1);
    }
    public void playEnemyKilled() {
        sp.play(enemyKilled_id,1, 1, 0, 0, 1);
    }

}