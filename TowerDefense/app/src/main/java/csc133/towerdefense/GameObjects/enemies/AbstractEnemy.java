package csc133.towerdefense.GameObjects.enemies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

import csc133.towerdefense.GameObjects.GameObject;
import csc133.towerdefense.GameState;
import csc133.towerdefense.R;

public abstract class AbstractEnemy {

    Point headLocation;
    private Point enemyPos;
    Point[] path;

    private int health;
    private int killedGold;
    // Start by heading to the right
    AbstractEnemy.Heading heading = AbstractEnemy.Heading.RIGHT;

    private int blockSize;
    private int currPathIndex = 0;

    private Point hudOffset;
    private GameState gameState;
    private GameObject gameObject;

    // A bitmap for each direction the head can face
    private Bitmap mBitmapHeadRight;
    private Bitmap mBitmapHeadLeft;
    private Bitmap mBitmapHeadUp;
    private Bitmap mBitmapHeadDown;

    // For tracking movement Heading
    enum Heading {
        UP, RIGHT, DOWN, LEFT
    }

    AbstractEnemy(Context context, GameObject gameObject, GameState gameState, int bs, Point mr,
                  String type, int health, int killedGold) {
        blockSize = bs;
        this.gameState = gameState;
        this.gameObject = gameObject;
        this.health = health;
        this.killedGold = killedGold;
        createBitmap(context, mr, type);
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
    public abstract void move();
    public void reset(int w, int h){
        heading = AbstractEnemy.Heading.RIGHT;
        currPathIndex = 0;
        headLocation = new Point(enemyPos.x, enemyPos.y);
    }
    public Point centerOfEnemy() {
        Point centerOfEnemy = new Point();
        centerOfEnemy.x = headLocation.x * blockSize + 20;
        centerOfEnemy.y = headLocation.y * blockSize + (int) (blockSize * 2.5) + 20;
        return centerOfEnemy;
    }
    public void enemyShot(int healthTaken, int indexOfEnemy) {
        health -= healthTaken;
        if (health <= 0) {
            gameState.gainedGold(killedGold);
            gameObject.enemyDead(indexOfEnemy);
        }
    }

    public void enemyReachedBase(int indexOfEnemy) {
        gameObject.enemyReachedBase(indexOfEnemy);
    }

    private void createBitmap(Context context, Point mr, String type) {
        // Create and scale the bitmaps
        if(type.equals("Enemy")) {
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
        } else if (type.equals("SlowEnemy")) {
            mBitmapHeadRight = BitmapFactory
                    .decodeResource(context.getResources(),
                            R.drawable.slowenemy);

            // Create 3 more versions of the head for different headings
            mBitmapHeadLeft = BitmapFactory
                    .decodeResource(context.getResources(),
                            R.drawable.slowenemy);

            mBitmapHeadUp = BitmapFactory
                    .decodeResource(context.getResources(),
                            R.drawable.slowenemy);

            mBitmapHeadDown = BitmapFactory
                    .decodeResource(context.getResources(),
                            R.drawable.slowenemy);
        }

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

        hudOffset = new Point(0, (int) (2.5 * blockSize));

        path = new Point[]{
                new Point(0, 0),
                new Point(25, 0),
                new Point(25, 15),
                new Point(50, 15)
        };
        enemyPos = mr;
        headLocation = new Point(enemyPos.x, enemyPos.y);
    }
}
