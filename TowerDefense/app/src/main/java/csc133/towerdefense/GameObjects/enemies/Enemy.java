package csc133.towerdefense.GameObjects.enemies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import csc133.towerdefense.GameObjects.GameObject;
import csc133.towerdefense.R;

public class Enemy extends GameObject {

    private Point headLocation;

    private Point[] path;

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
        super();
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
        hudOffset = new Point(0, (int)(2.5 * blocksize));

        path = new Point[] {
                new Point(0, 0),
                new Point(25, 0),
                new Point(25, 15),
                new Point(50, 15)
        };
        headLocation = new Point(path[0].x, path[0].y);
    }

    private Point hudOffset;

    public void drawPath(Canvas canvas, Paint paint) {
        //riksy coding only
        for (int i = 0; i < path.length - 1; ++i) {
            int x1 = blocksize * path[i].x;
            int x2 = blocksize * path[i+1].x;
            int y1 = blocksize * path[i].y;
            int y2 = blocksize * path[i+1].y;
            int left, top, right, bottom;
            if (x1 < x2) {
                left = x1 - blocksize / 2;
                right = x2 + blocksize / 2;
            } else {
                left = x1 + blocksize / 2;
                right = x2 - blocksize / 2;
            }
            if (y1 < y2) {
                top = y1 - blocksize / 2;
                bottom = y2 + blocksize / 2;
            } else {
                top = y1 + blocksize / 2;
                bottom = y2 - blocksize / 2;
            }
            int xOffset = blocksize / 2;
            int yOffset = blocksize / 2;

            Rect rect = new Rect(left + xOffset, top + yOffset + hudOffset.y, right + xOffset, bottom + yOffset + hudOffset.y);
            paint.setColor(Color.argb(50, 255, 255, 150));
            canvas.drawRect(rect, paint);
        }
        paint.setAlpha(255);

    }

    public void draw(Canvas canvas, Paint paint) {
        drawPath(canvas, paint);
        // Draw the head
        switch (heading) {
            case RIGHT:
                canvas.drawBitmap(mBitmapHeadRight,
                        headLocation.x
                                * blocksize,
                        headLocation.y
                                * blocksize + hudOffset.y, paint);
                break;

            case LEFT:
                canvas.drawBitmap(mBitmapHeadLeft,
                        headLocation.x
                                * blocksize,
                        headLocation.y
                                * blocksize + hudOffset.y, paint);
                break;

            case UP:
                canvas.drawBitmap(mBitmapHeadUp,
                        headLocation.x
                                * blocksize,
                        headLocation.y
                                * blocksize + hudOffset.y, paint);
                break;

            case DOWN:
                canvas.drawBitmap(mBitmapHeadDown,
                        headLocation.x
                                * blocksize,
                        headLocation.y
                                * blocksize + hudOffset.y, paint);
                break;
        }
    }

    public void reset(int w, int h) {
        // Reset the heading
        heading = Heading.RIGHT;

        headLocation = new Point(w / 2, h / 2);
    }

    public void move() {
        Point p = headLocation;
        followPath();



    }
//kekxd global
    int currPathIndex = 0;

    public void followPath() {

        while (true) {
            boolean getout = true;
            if (currPathIndex >= path.length) return;
            // thi shit aint work for daigon alley
            int targetDirX = path[currPathIndex].x - headLocation.x;
            int targetDirY = path[currPathIndex].y - headLocation.y;
            System.out.println(targetDirX + " " + targetDirY);
            //heading = Heading.LEFT;
            // disgusting non safe coding try not to vomit
            if (targetDirX > 0) {
                heading = Heading.RIGHT;
            } else if (targetDirX < 0) {
                heading = Heading.LEFT;
            } else if (targetDirY > 0) {
                heading = Heading.DOWN;
            } else if (targetDirY < 0) {
                heading = Heading.UP;
            } else if (targetDirX == 0 && targetDirY == 0) {
                ++currPathIndex;
                getout = false;
            }
            if (getout) break;
        }

        // Move it appropriately
        switch (heading) {
            case UP:
                headLocation.y--;
                break;

            case RIGHT:
                headLocation.x++;
                break;

            case DOWN:
                headLocation.y++;
                break;

            case LEFT:
                headLocation.x--;
                break;
        }

    }

    // Handle changing direction
    public void switchHeading(MotionEvent motionEvent) {

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

    public void spawn() {
    }
}
