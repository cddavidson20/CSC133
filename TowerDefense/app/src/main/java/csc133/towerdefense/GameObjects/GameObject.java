package csc133.towerdefense.GameObjects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import csc133.towerdefense.GameObjects.enemies.Enemy;
import csc133.towerdefense.GameObjects.towers.AbstractTower;
import csc133.towerdefense.GameObjects.towers.DefenseTower;

public class GameObject {

    private Point mSpawnRange;

    private Context context;
    private int blockSize;

    private Enemy enemy;
    private AbstractTower tower;

    private final int NUM_BLOCKS_WIDE = 40;
    private int mNumBlocksHigh;

    public GameObject(){}

    public GameObject(Context context, Point size) {

        //define the context so that apples can use it when made
        this.context = context;
        // Work out how many pixels each block is
        this.blockSize = size.x / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        mNumBlocksHigh = size.y / blockSize;


        mSpawnRange = new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh);

        enemy = new Enemy(context, blockSize, new Point(50, 50));
        tower = new DefenseTower(context, new Point(NUM_BLOCKS_WIDE*10, mNumBlocksHigh*10), blockSize, enemy);
    }

    public void newGame() {
        //enemy.reset(NUM_BLOCKS_WIDE, mNumBlocksHigh);
        spawn();
    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        enemy.draw(mCanvas, mPaint);
        tower.draw(mCanvas, mPaint);
    }

    public void update() {
        enemy.move();
    }

    public void switchHeading(MotionEvent motionEvent) {
        enemy.switchHeading(motionEvent);
    }

    public void spawn() {
    }
}
