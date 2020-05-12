package com.gamescodeschool.ponggame;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.RectF;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

class RunPongGame extends SurfaceView implements Runnable {

    // Holds the resolution of the screen
    private int mScreenX;
    private int mScreenY;

    private int mScore = 0;
    private int mLives = 3;

    private Bat mBat;
    private Ball mBall;
    private DrawPong drawCanvas;
    private Sounds sound;

    // Here is the Thread and two control variables
    private Thread mGameThread = null;
    // This volatile variable can be accessed
    // from inside and outside the thread
    private volatile boolean mPlaying;
    private boolean mPaused = true;

    // executed from PongActivity
    public RunPongGame(Context context, int x, int y) {
        // Super... calls the parent class
        // constructor of SurfaceView
        super(context);

        mScreenX = x;
        mScreenY = y;

        SurfaceHolder mOurHolder = getHolder();

        mBall = new Ball(mScreenX);
        mBat = new Bat(mScreenX, mScreenY);

        drawCanvas = new DrawPong(mOurHolder, mBall, mBat, mScreenX);
        sound = new Sounds(context);

        startNewGame();
    }

    private void startNewGame() {
        mBall.reset(mScreenX, mScreenY);
        mScore = 0;
        mLives = 3;
    }

    // When we start the thread with:
    // mGameThread.start();
    // the run method is continuously called by Android
    // because we implemented the Runnable interface
    // Calling mGameThread.join();
    // will stop the thread
    @Override
    public void run() {
        // mPlaying gives us finer control
        // rather than just relying on the calls to run
        // mPlaying must be true AND
        // the thread running for the main loop to execute
        while (mPlaying) {

            long frameStartTime = System.currentTimeMillis();

            if (!mPaused) {
                drawCanvas.update();
                detectCollisions();
            }

            // The movement has been handled and collisions
            // detected now we can draw the scene.
            drawCanvas.draw(mScore, mLives);

            drawCanvas.frameRate(frameStartTime);
        }
    }

    private void detectCollisions() {
        // Has the bat hit the ball?
        if (RectF.intersects(mBat.getRect(), mBall.getRect())) {
            mBall.batBounce(mBat.getRect());
            mBall.increaseVelocity();
            mScore++;
            sound.playBeep();
        }

        // Has the ball hit the edge of the screen?

        // Bottom
        if (mBall.getRect().bottom > mScreenY) {
            mBall.reverseYVelocity();

            mLives--;
            sound.playMiss();

            if (mLives == 0) {
                mPaused = true;
                startNewGame();
            }
        }

        // Top
        if (mBall.getRect().top < 0) {
            mBall.reverseYVelocity();
            sound.playBoop();
        }

        // Left
        if (mBall.getRect().left < 0) {
            mBall.reverseXVelocity();
            sound.playBop();
        }

        // Right
        if (mBall.getRect().right > mScreenX) {
            mBall.reverseXVelocity();
            sound.playBop();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        // This switch block replaces the
        // if statement from the Sub Hunter game
        switch (motionEvent.getAction() &
                MotionEvent.ACTION_MASK) {

            // The player has put their finger on the screen
            case MotionEvent.ACTION_DOWN:

                // If the game was paused unpause
                mPaused = false;

                // Where did the touch happen
                if (motionEvent.getX() > mScreenX / 2) {
                    // On the right hand side
                    mBat.setMovementState(mBat.RIGHT);
                } else {
                    // On the left hand side
                    mBat.setMovementState(mBat.LEFT);
                }

                break;

            // The player lifted their finger
            // from anywhere on screen.
            // It is possible to create bugs by using
            // multiple fingers. We will use more
            // complicated and robust touch handling
            // in later projects
            case MotionEvent.ACTION_UP:

                // Stop the bat moving
                mBat.setMovementState(mBat.STOPPED);
                break;
        }
        return true;
    }

    // when the player quits the game
    public void pause() {

        // Set mPlaying to false
        // Stopping the thread isn't
        // always instant
        mPlaying = false;
        try {
            // Stop the thread
            mGameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    // This method is called by PongActivity
    // when the player starts the game
    public void resume() {
        mPlaying = true;
        mGameThread = new Thread(this);

        mGameThread.start();
    }
}