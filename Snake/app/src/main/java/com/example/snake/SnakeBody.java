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

    private Vector<PointP> bodySegments;
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
        bodySegments = new Vector<>();
    }

    void draw(Canvas canvas, Paint paint){
        // Draw the snake body one block at a time
        for (int i = 1; i < bodySegments.size(); i++) {
            canvas.drawBitmap(mBitmapBody,
                    bodySegments.get(i).x
                            * mSegmentSize,
                    bodySegments.get(i).y
                            * mSegmentSize, paint);

        }
    }

    void move(PointP headPosition){
        // Move the body
        // Start at the back and move it
        // to the position of the segment in front of it
        for (int i = bodySegments.size() - 1; i >= 0; i--) {

            if (i == 0) {
                bodySegments.get(i).x = headPosition.x;
                bodySegments.get(i).y = headPosition.y;
            } else {
                // Make it the same value as the next segment
                // going forwards towards the head
                bodySegments.get(i).x = bodySegments.get(i - 1).x;
                bodySegments.get(i).y = bodySegments.get(i - 1).y;
            }
        }
    }

    boolean hasEatenBody(PointP headPosition) {

        for (int i = bodySegments.size() - 1; i > 0; i--) {
            // Have any of the sections collided with the head
            if (headPosition.x == bodySegments.get(i).x &&
                    headPosition.y == bodySegments.get(i).y) {

                return true;
            }
        }
        return false;
    }

    void addBodySegments(PointP bodySegment) {
        bodySegments.add(bodySegment);
    }

    void clearBodySegments() {
        bodySegments.clear();
        addBodySegments(new PointP());
    }
}
