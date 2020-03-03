package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

class Apple extends GameObject {

    // An image to represent the goodApple
    private Bitmap mBitmapApple;
    private PointP location;
    private PointP mSpawnRange;
    private int appleID;

    private int mSize;

    /// Set up the goodApple in the constructor
    Apple(Context context, PointP sr, int s, PointP location, int appleID) {

        // Make a note of the size of an apple
        mSize = s;

        this.location = location;
        this.mSpawnRange = sr;
        this.appleID = appleID;

        switch (appleID) {
            case 0:
                // Load the badApple image to the bitmap
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple0);
                mBitmapApple = Bitmap.createScaledBitmap(mBitmapApple, s, s, false);
                break;
            case 1:
                // Load the 1 point apple image to the bitmap
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple1);
                mBitmapApple = Bitmap.createScaledBitmap(mBitmapApple, s, s, false);
                break;
            case 2:
                // Load the 2 point apple image to the bitmap
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple2);
                mBitmapApple = Bitmap.createScaledBitmap(mBitmapApple, s, s, false);
                break;
            case 3:
                // Load the 3 point apple image to the bitmap
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple3);
                mBitmapApple = Bitmap.createScaledBitmap(mBitmapApple, s, s, false);
                break;
            default:
                throw new IllegalArgumentException("This is not a legal ID");
        }

    }

    // Draw the goodApple
    void draw(Canvas canvas, Paint paint) {
        PointP location = getLocation();
        canvas.drawBitmap(mBitmapApple,
                location.x * mSize, location.y * mSize, paint);
    }

    // This is called every time an apple is eaten
    void spawn() {
        // Choose two random values and place the apple
        Random random = new Random();
        location.x = random.nextInt(mSpawnRange.x) + 1;
        location.y = random.nextInt(mSpawnRange.y - 1) + 1;
    }

    // This is called every time a bad apple is eaten
    void updateScore(PlayState playState, Sounds sounds) {
        if (appleID != 0) {
            playState.mScore += appleID;
            sounds.playEatGood();
        } else {
            playState.mScore -= 2;
            sounds.playEatBad();
        }
    }

    // Let SnakeGame know where the apple is
    // SnakeGame can share this with the snake
    PointP getLocation() {
        return location;
    }

}
