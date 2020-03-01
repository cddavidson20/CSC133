package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

class GoodApple extends AppleBuilder{

    // An image to represent the goodApple
    private Bitmap mBitmapApple;

    private int mSize;

    /// Set up the goodApple in the constructor
    GoodApple(Context context, PointP sr, int s) {

        super(sr);

        // Make a note of the size of an goodApple
        mSize = s;

        // Load the image to the bitmap
        mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.goodapple);

        // Resize the bitmap
        mBitmapApple = Bitmap.createScaledBitmap(mBitmapApple, s, s, false);
    }

    // Draw the goodApple
    public void draw(Canvas canvas, Paint paint) {
        PointP location = getLocation();
        canvas.drawBitmap(mBitmapApple,
                location.x * mSize, location.y * mSize, paint);

    }

}