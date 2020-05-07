package csc133.towerdefense.GameObjects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

import csc133.towerdefense.GameObjects.enemies.AbstractEnemy;
import csc133.towerdefense.GameObjects.enemies.Enemy;
import csc133.towerdefense.GameObjects.enemies.SlowEnemy;
import csc133.towerdefense.GameObjects.towers.AbstractTower;
import csc133.towerdefense.GameObjects.towers.DefenseTower;
import csc133.towerdefense.GameState;
import csc133.towerdefense.Path;
import csc133.towerdefense.Base;

public class GameObject extends SurfaceView {

    private Context context;
    private int blockSize;

    private GameState gameState;

    private ArrayList<AbstractEnemy> enemies;
    private ArrayList<AbstractTower> towers;
    private Path path;
    private Base base;
    private Random rand;

    private final int NUM_BLOCKS_WIDE = 40;
    private int mNumBlocksHigh;
    public boolean spawnWave = true;

    public GameObject(Context context, GameState gameState, Point size) {
        super(context);

        //define the context so that apples can use it when made
        this.context = context;
        this.gameState = gameState;
        // Work out how many pixels each block is
        this.blockSize = size.x / NUM_BLOCKS_WIDE; //40 currently
        // How many blocks of the same size will fit into the height
        mNumBlocksHigh = size.y / blockSize;

        rand = new Random();

        towers = new ArrayList<>();
        enemies = new ArrayList<>();
        path = new Path(blockSize);
        base = new Base(blockSize);
    }

    public void enemyDead(int index) {
        //kill the enemy that was shot
        enemies.remove(index);
    }

    public void enemyReachedBase(int index) {
        enemies.remove(index);
    }

    public void newGame() {
        gameState.reset();
        enemies.clear();
        towers.clear();
    }

    public void draw(Canvas mCanvas, Paint mPaint) {
        path.drawPath(mCanvas, mPaint);
        base.drawBase(mCanvas, mPaint);
        for (AbstractEnemy enemy : enemies)
            enemy.draw(mCanvas, mPaint);

        for (int i = 0; i < towers.size(); ++i) {
            AbstractTower t = towers.get(i);
            t.draw(mCanvas, mPaint);
        }
    }

    public void drawEnemy(Canvas mCanvas, Paint mPaint, AbstractEnemy enemy){
        enemy.draw(mCanvas, mPaint);
    }

    public void update(Canvas mCanvas, Paint mPaint) {
       if (enemies.size() > 0) {
           for (int i = enemies.size() - 1; i >=0; i--) {
               AbstractEnemy enemy = enemies.get(i);
               enemy.move();
               if (base.hittingBase(enemy)) {
                   enemy.enemyReachedBase(i);
                   gameState.removeLives();
               }
               drawEnemy(mCanvas, mPaint, enemy);
           }
       } else {
           spawnWave = !spawnWave;
           gameState.passedWave();
           gameState.incrementWave();
       }
    }
    public void spawnWave() {
        for (int i = 0; i < gameState.getWave(); i++) {
            int chooseEnemy = rand.nextInt(3);
            if (chooseEnemy <= 1)
                enemies.add(new Enemy(context, this, gameState, blockSize, new Point(-i, 0)));
            if (chooseEnemy > 1)
                enemies.add(new SlowEnemy(context, this, gameState, blockSize, new Point(-i, 0)));
        }
        spawnWave = !spawnWave;
    }

    public void newTowerLocation(int x, int y) {
        Point p = new Point(x, y);
        if (!base.hittingBase(p) && !path.hittingPath(p)) {
            towers.add(new DefenseTower(context, p, gameState, enemies, blockSize));
        } else {
            System.out.println("Tower can't be placed on base.");
        }
    }

}
