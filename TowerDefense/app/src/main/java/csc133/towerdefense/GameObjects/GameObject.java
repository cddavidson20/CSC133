package csc133.towerdefense.GameObjects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceView;

import java.util.ArrayList;

import csc133.towerdefense.GameObjects.enemies.Enemy;
import csc133.towerdefense.GameObjects.towers.AbstractTower;
import csc133.towerdefense.GameObjects.towers.DefenseTower;

public class GameObject extends SurfaceView {

    private Context context;
    private int blockSize;

    private Enemy enemy;
    private ArrayList<AbstractTower> towers;

    private final int NUM_BLOCKS_WIDE = 40;
    private int mNumBlocksHigh;

    public GameObject(Context context, Point size) {
        super(context);

        //define the context so that apples can use it when made
        this.context = context;
        // Work out how many pixels each block is
        this.blockSize = size.x / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        mNumBlocksHigh = size.y / blockSize;

        towers = new ArrayList<>();

        enemy = new Enemy(context, blockSize, new Point(50, 50));
    }

    public void newGame() {
        enemy.reset(NUM_BLOCKS_WIDE, mNumBlocksHigh);
        towers.clear();
    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        enemy.draw(mCanvas, mPaint);
        for (AbstractTower t : towers) {
            t.draw(mCanvas, mPaint);
        }
    }

    public void update() {
        enemy.move();
    }

    public void newTowerLocation(int x, int y) {
       towers.add(new DefenseTower(context, new Point(x, y),enemy, blockSize));
    }

}
