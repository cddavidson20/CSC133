package csc133.towerdefense;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

public class GameObject {

    private Point mSpawnRange;

    private Context context;
    private int blockSize;
    private Enemy enemy;

    private final int NUM_BLOCKS_WIDE = 40;
    private int mNumBlocksHigh;

    GameObject(){}

    GameObject(Context context, Point size) {

        //define the context so that apples can use it when made
        this.context = context;
        // Work out how many pixels each block is
        this.blockSize = size.x / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        mNumBlocksHigh = size.y / blockSize;


        mSpawnRange = new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh);

        enemy = new Enemy(context, blockSize, new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh));
    }

    void newGame() {
        enemy.reset(NUM_BLOCKS_WIDE, mNumBlocksHigh);
    }

    void draw(Canvas mCanvas, Paint mPaint) {
        enemy.draw(mCanvas, mPaint);
    }

    void update() {
        enemy.move();
    }

    void switchHeading(MotionEvent motionEvent) {
        enemy.switchHeading(motionEvent);
    }
}
