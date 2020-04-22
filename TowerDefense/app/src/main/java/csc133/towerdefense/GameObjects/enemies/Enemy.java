package csc133.towerdefense.GameObjects.enemies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Paint;

import csc133.towerdefense.GameObjects.GameObject;
import csc133.towerdefense.GameState;
import csc133.towerdefense.R;

public class Enemy extends AbstractEnemy {

    public Point headLocation;
    private int health = 100;
    private int deadGold = 100;

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

    private int blockSize;
    private int currPathIndex = 0;

    private Point hudOffset;
    private GameState gameState;
    private GameObject gameObject;

    public Enemy(Context context, GameObject gameObject, GameState gameState, int bs, Point mr) {
        super();
        blockSize = bs;
        this.gameState = gameState;
        this.gameObject = gameObject;
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
                        blockSize, blockSize, false);

        // A matrix for scaling
        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);

        mBitmapHeadLeft = Bitmap
                .createBitmap(mBitmapHeadRight,
                        0, 0, blockSize, blockSize, matrix, true);

        // A matrix for rotating
        matrix.preRotate(-90);
        mBitmapHeadUp = Bitmap
                .createBitmap(mBitmapHeadRight,
                        0, 0, blockSize, blockSize, matrix, true);

        // Matrix operations are cumulative
        // so rotate by 180 to face down
        matrix.preRotate(180);
        mBitmapHeadDown = Bitmap
                .createBitmap(mBitmapHeadRight,
                        0, 0, blockSize, blockSize, matrix, true);

        // How many blocks of the same size will fit into the height
        int mNumBlocksHigh = mr.y / blockSize;
        int halfWayPoint = mr.x * blockSize / 2;
        hudOffset = new Point(0, (int)(2.5 * blockSize));

        path = new Point[] {
                new Point(0, 0),
                new Point(25, 0),
                new Point(25, 15),
                new Point(50, 15)
        };
        headLocation = new Point(path[0].x, path[0].y);
    }

    public void draw(Canvas canvas, Paint paint) {
        // Draw the head
        switch (heading) {
            case RIGHT:
                canvas.drawBitmap(mBitmapHeadRight,
                        headLocation.x
                                * blockSize,
                        headLocation.y
                                * blockSize + hudOffset.y, paint);
                break;

            case LEFT:
                canvas.drawBitmap(mBitmapHeadLeft,
                        headLocation.x
                                * blockSize,
                        headLocation.y
                                * blockSize + hudOffset.y, paint);
                break;

            case UP:
                canvas.drawBitmap(mBitmapHeadUp,
                        headLocation.x
                                * blockSize,
                        headLocation.y
                                * blockSize + hudOffset.y, paint);
                break;

            case DOWN:
                canvas.drawBitmap(mBitmapHeadDown,
                        headLocation.x
                                * blockSize,
                        headLocation.y
                                * blockSize + hudOffset.y, paint);
                break;
        }
    }

    public void reset(int w, int h) {
        // Reset the heading
        heading = Heading.RIGHT;
        currPathIndex = 0;
        headLocation = new Point(path[0].x, path[0].y);
    }

    public void move() {
        followPath();
    }

    private void followPath() {

        while (true) {
            boolean getout = true;
            if (currPathIndex >= path.length) return;

            int targetDirX = path[currPathIndex].x - headLocation.x;
            int targetDirY = path[currPathIndex].y - headLocation.y;
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

    public void enemyShot(int healthTaken, int indexOfEnemy) {
        health -= healthTaken;
        if (health <= 0) {
            gameState.gainedGold(100);
            gameObject.enemyDead(indexOfEnemy);
        }
    }

    public void enemyReachedBase(int indexOfEnemy) {
        gameObject.enemyReachedBase(indexOfEnemy);
    }
}
