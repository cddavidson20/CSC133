package com.gamescodeschool.ponggame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

/**
 * Draws to the canvas during the Pong game.
 */
class DrawPong {

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
    private int rightHand;
    private int nameFontSize;

    /**
     * Sets all attributes to draw.
     * @param mOurHolder required surfaceview method.
     * @param mBall the ball object.
     * @param mBat the bat object.
     * @param mScreenX the screen width.
     */
    DrawPong(SurfaceHolder mOurHolder, Ball mBall, Bat mBat, int mScreenX) {

        this.mBall = mBall;
        this.mBat = mBat;

        this.mOurHolder = mOurHolder;

        mPaint = new Paint();

        mFontSize = mScreenX / 20;
        mFontMargin = mScreenX / 75;
        nameFontSize = mScreenX / 40;
        // String length divided by 2 with some space!
        rightHand = mScreenX - (nameFontSize * 16);
    }

    // Draw the game objects and the HUD
    void draw(int mScore, int mLives) {

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
            drawText(mScore, mLives);
            mPaint.setTextSize(nameFontSize);
            drawNames();

            if(DEBUGGING){
                printDebuggingText();
            }

            // Display the drawing on screen
            // unlockCanvasAndPost is a method of SurfaceView
            mOurHolder.unlockCanvasAndPost(mCanvas);
        }

    }

    void update() {
        // Update the bat and the ball
        mBall.update(mFPS);
        mBat.update(mFPS);
    }

    void frameRate(long frameStartTime) {

        long timeThisFrame = System.currentTimeMillis() - frameStartTime;

        if (timeThisFrame > 0) {
            mFPS = MILLIS_IN_SECOND / timeThisFrame;
        }
    }

    // Draw the HUD
    private void drawText(int mScore, int mLives) {
        mCanvas.drawText("Score: " + mScore +
                        "   Lives: " + mLives,
                        mFontMargin, mFontSize, mPaint);
    }

    private void drawNames() {
        mCanvas.drawText("Chris Davidson & Ronny Ritprasert", rightHand , nameFontSize, mPaint);
    }

    private void printDebuggingText(){
        int debugSize = mFontSize / 2;
        int debugStart = 150;
        mPaint.setTextSize(debugSize);
        mCanvas.drawText("FPS: " + mFPS ,
                10, debugStart + debugSize, mPaint);
    }
}
