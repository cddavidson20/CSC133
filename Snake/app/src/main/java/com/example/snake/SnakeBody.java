package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Vector;

public class SnakeBody extends Snake {

    // A bitmap for the body
    Bitmap mBitmapBody;

    // How big is each segment of the snake?
    private int mSegmentSize;

    public SnakeBody(Context context, int ss){
        // Create and scale the body
        mBitmapBody = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.body);

        mBitmapBody = Bitmap
                .createScaledBitmap(mBitmapBody,
                        ss, ss, false);
        mSegmentSize = ss;
    }

    void draw(Canvas canvas, Paint paint , Vector<PointP> segmentLocations){
        // Draw the snake body one block at a time
        for (int i = 1; i < segmentLocations.size(); i++) {
            canvas.drawBitmap(mBitmapBody,
                    segmentLocations.get(i).x
                            * mSegmentSize,
                    segmentLocations.get(i).y
                            * mSegmentSize, paint);

        }
    }
}
