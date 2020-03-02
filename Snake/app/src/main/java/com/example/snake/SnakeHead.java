package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

class SnakeHead extends Snake {

     PointP headLocation;

    // A bitmap for each direction the head can face
    private Bitmap mBitmapHeadRight;
    private Bitmap mBitmapHeadLeft;
    private Bitmap mBitmapHeadUp;
    private Bitmap mBitmapHeadDown;

    // How big is each segment of the snake?
    private int mSegmentSize;

    SnakeHead(Context context, int ss) {
        // Create and scale the bitmaps
        mBitmapHeadRight = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.head);

        // Create 3 more versions of the head for different headings
        mBitmapHeadLeft = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.head);

        mBitmapHeadUp = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.head);

        mBitmapHeadDown = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.head);

        // Modify the bitmaps to face the snake head
        // in the correct direction
        mBitmapHeadRight = Bitmap
                .createScaledBitmap(mBitmapHeadRight,
                        ss, ss, false);

        // A matrix for scaling
        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);

        mBitmapHeadLeft = Bitmap
                .createBitmap(mBitmapHeadRight,
                        0, 0, ss, ss, matrix, true);

        // A matrix for rotating
        matrix.preRotate(-90);
        mBitmapHeadUp = Bitmap
                .createBitmap(mBitmapHeadRight,
                        0, 0, ss, ss, matrix, true);

        // Matrix operations are cumulative
        // so rotate by 180 to face down
        matrix.preRotate(180);
        mBitmapHeadDown = Bitmap
                .createBitmap(mBitmapHeadRight,
                        0, 0, ss, ss, matrix, true);

        mSegmentSize = ss;

    }

    void draw(Canvas canvas, Paint paint, Heading heading, PointP segmentLocation) {
        // Draw the head
        switch (heading) {
            case RIGHT:
                canvas.drawBitmap(mBitmapHeadRight,
                        segmentLocation.x
                                * mSegmentSize,
                        segmentLocation.y
                                * mSegmentSize, paint);
                break;

            case LEFT:
                canvas.drawBitmap(mBitmapHeadLeft,
                        segmentLocation.x
                                * mSegmentSize,
                        segmentLocation.y
                                * mSegmentSize, paint);
                break;

            case UP:
                canvas.drawBitmap(mBitmapHeadUp,
                        segmentLocation.x
                                * mSegmentSize,
                        segmentLocation.y
                                * mSegmentSize, paint);
                break;

            case DOWN:
                canvas.drawBitmap(mBitmapHeadDown,
                        segmentLocation.x
                                * mSegmentSize,
                        segmentLocation.y
                                * mSegmentSize, paint);
                break;
        }
    }

    void addHead(PointP headLocation) {
        this.headLocation = headLocation;
    }

    void clearHead(int w, int h) {
        headLocation = null;
    }
}
