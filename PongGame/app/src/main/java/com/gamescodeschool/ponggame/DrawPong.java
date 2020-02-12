package com.gamescodeschool.ponggame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class DrawPong {

    private final boolean DEBUGGING = true;

    private SurfaceHolder mOurHolder;
    private Canvas mCanvas;
    private Paint mPaint;

    private long mFPS;
    private final int MILLIS_IN_SECOND = 1000;

    private Ball mBall;
    private Bat mBat;

    private int mFontSize;
    private int mFontMargin;

    public DrawPong(SurfaceHolder mOurHolder, Ball mBall, Bat mBat, int mScreenX) {

        this.mBall = mBall;
        this.mBat = mBat;

        this.mOurHolder = mOurHolder;

        mPaint = new Paint();

        mFontSize = mScreenX / 20;
        mFontMargin = mScreenX / 75;
    }

    // Draw the game objects and the HUD
    public void draw(int mScore, int mLives) {

        if (mOurHolder.getSurface().isValid()) {
            mCanvas = mOurHolder.lockCanvas();

            // Fill the screen with a solid color (dark grey)
            mCanvas.drawColor(Color.argb
                    (255, 50, 54, 57));

            // Paint text/attributes with this color (white)
            mPaint.setColor(Color.argb
                    (255, 255, 255, 255));

            // Draw the bat and ball
            mCanvas.drawRect(mBall.getRect(), mPaint);
            mCanvas.drawRect(mBat.getRect(), mPaint);

            // Choose the font size
            mPaint.setTextSize(mFontSize);
            drawText(mScore, mLives, mPaint);

            if(DEBUGGING){
                printDebuggingText();
            }

            // Display the drawing on screen
            // unlockCanvasAndPost is a method of SurfaceView
            mOurHolder.unlockCanvasAndPost(mCanvas);
        }

    }

    public void update() {
        // Update the bat and the ball
        mBall.update(mFPS);
        mBat.update(mFPS);
    }

    public void frameRate(long frameStartTime) {

        long timeThisFrame = System.currentTimeMillis() - frameStartTime;

        if (timeThisFrame > 0) {
            mFPS = MILLIS_IN_SECOND / timeThisFrame;
        }
    }

    // Draw the HUD
    public void drawText(int mScore, int mLives, Paint mPaint) {
        mCanvas.drawText("Score: " + mScore +
                        "   Lives: " + mLives,
                        mFontMargin, mFontSize, mPaint);
    }

    private void printDebuggingText(){
        int debugSize = mFontSize / 2;
        int debugStart = 150;
        mPaint.setTextSize(debugSize);
        mCanvas.drawText("FPS: " + mFPS ,
                10, debugStart + debugSize, mPaint);
    }
}
