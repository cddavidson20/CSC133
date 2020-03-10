package csc133.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Enemy extends GameObject {

    Point headLocation;

    // A bitmap for each direction the head can face
    private Bitmap mBitmapHeadRight;
    private Bitmap mBitmapHeadLeft;
    private Bitmap mBitmapHeadUp;
    private Bitmap mBitmapHeadDown;

    // For tracking movement Heading
    enum Heading {
        UP, RIGHT, DOWN, LEFT
    }
    // Start by heading to the right
    private Heading heading = Heading.RIGHT;

    private int blocksize;
    private int mNumBlocksHigh;
    private int halfWayPoint;

    public Enemy(Context context, int bs, Point mr) {
        blocksize = bs;
        // Create and scale the bitmaps
        mBitmapHeadRight = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.spaceship);

        // Create 3 more versions of the head for different headings
        mBitmapHeadLeft = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.spaceship);

        mBitmapHeadUp = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.spaceship);

        mBitmapHeadDown = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.spaceship);

        // Modify the bitmaps to face the snake head
        // in the correct direction
        mBitmapHeadRight = Bitmap
                .createScaledBitmap(mBitmapHeadRight,
                        blocksize, blocksize, false);

        // A matrix for scaling
        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);

        mBitmapHeadLeft = Bitmap
                .createBitmap(mBitmapHeadRight,
                        0, 0, blocksize, blocksize, matrix, true);

        // A matrix for rotating
        matrix.preRotate(-90);
        mBitmapHeadUp = Bitmap
                .createBitmap(mBitmapHeadRight,
                        0, 0, blocksize, blocksize, matrix, true);

        // Matrix operations are cumulative
        // so rotate by 180 to face down
        matrix.preRotate(180);
        mBitmapHeadDown = Bitmap
                .createBitmap(mBitmapHeadRight,
                        0, 0, blocksize, blocksize, matrix, true);

        // How many blocks of the same size will fit into the height
        mNumBlocksHigh = mr.y / blocksize;

        halfWayPoint = mr.x * blocksize / 2;
        headLocation = new Point(new Point(40 / 2, mNumBlocksHigh / 2));
    }

    void draw(Canvas canvas, Paint paint) {
        // Draw the head
        switch (heading) {
            case RIGHT:
                canvas.drawBitmap(mBitmapHeadRight,
                        headLocation.x
                                * blocksize,
                        headLocation.y
                                * blocksize, paint);
                break;

            case LEFT:
                canvas.drawBitmap(mBitmapHeadLeft,
                        headLocation.x
                                * blocksize,
                        headLocation.y
                                * blocksize, paint);
                break;

            case UP:
                canvas.drawBitmap(mBitmapHeadUp,
                        headLocation.x
                                * blocksize,
                        headLocation.y
                                * blocksize, paint);
                break;

            case DOWN:
                canvas.drawBitmap(mBitmapHeadDown,
                        headLocation.x
                                * blocksize,
                        headLocation.y
                                * blocksize, paint);
                break;
        }
    }

    void reset(int w, int h) {

        // Reset the heading
        heading = Heading.RIGHT;

        headLocation = new Point(new Point(w / 2, h / 2));
    }

    void move() {
        Point p = headLocation;

        // Move it appropriately
        switch (heading) {
            case UP:
                p.y--;
                break;

            case RIGHT:
                p.x++;
                break;

            case DOWN:
                p.y++;
                break;

            case LEFT:
                p.x--;
                break;
        }
    }

    // Handle changing direction
    void switchHeading(MotionEvent motionEvent) {

        // Is the tap on the right hand side?
        if (motionEvent.getX() >= halfWayPoint) {
            switch (heading) {
                // Rotate right
                case UP:
                    heading = Heading.RIGHT;
                    break;
                case RIGHT:
                    heading = Heading.DOWN;
                    break;
                case DOWN:
                    heading = Heading.LEFT;
                    break;
                case LEFT:
                    heading = Heading.UP;
                    break;

            }
        } else {
            // Rotate left
            switch (heading) {
                case UP:
                    heading = Heading.LEFT;
                    break;
                case LEFT:
                    heading = Heading.DOWN;
                    break;
                case DOWN:
                    heading = Heading.RIGHT;
                    break;
                case RIGHT:
                    heading = Heading.UP;
                    break;
            }
        }
    }
}
