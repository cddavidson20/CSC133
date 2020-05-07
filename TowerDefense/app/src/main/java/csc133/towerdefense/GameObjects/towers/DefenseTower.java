package csc133.towerdefense.GameObjects.towers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

import csc133.towerdefense.GameObjects.enemies.AbstractEnemy;
import csc133.towerdefense.GameState;
import csc133.towerdefense.R;

public class DefenseTower extends AbstractTower {

    private Bitmap bitmap;
    private ArrayList<AbstractEnemy> enemies;
    private Rect range;
    private int blockSize;
    private boolean towerClicked = false;

    private final Point sizeOfDefenseTowerImage = new Point(27,27);
    private Point centerOfTowerImage;
    private GameState gameState;
    private static int defenceTowerCost = 100;
    private static int damagePerShot = 3;

    public DefenseTower() {
        super();
    }

    public DefenseTower(Context context, Point position, GameState gs, ArrayList<AbstractEnemy> enemies, int bs) {
        super(context, position);
        this.enemies = enemies;
        this.blockSize = bs;
        int positionX = position.x;
        int positionY = position.y;
        int offsetX = (positionX - sizeOfDefenseTowerImage.x /2);
        int offsetY = (positionY - sizeOfDefenseTowerImage.y /2);
        centerOfTowerImage = new Point(offsetX, offsetY);

        range = new Rect(centerOfTowerImage.x - 100, centerOfTowerImage.y - 100,
                centerOfTowerImage.x + 100, centerOfTowerImage.y + 100
        );

        bitmap = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.defense_tower);
        this.gameState = gs;
        gameState.spentGold(DefenseTower.cost());
    }

    public static int cost() {
        return defenceTowerCost;
    }

    public Point getPosition() { return centerOfTowerImage; }

    public void draw(Canvas canvas, Paint paint) {

        shoot(canvas, paint);
        paint.setAlpha(255);
        canvas.drawBitmap(bitmap, centerOfTowerImage.x - sizeOfDefenseTowerImage.x,
                        centerOfTowerImage.y - sizeOfDefenseTowerImage.y, paint);
    }

    private void shoot(Canvas canvas, Paint paint) {
        boolean enemyInRange = false;
        for (int i = enemies.size() - 1; i >= 0; i--) {
            AbstractEnemy enemy = enemies.get(i);
            Point centerOfEnemy = new Point(enemy.centerOfEnemy());
            if (enemyInRange(centerOfEnemy) && !enemyInRange) {
                enemyInRange = true;
                paint.setColor(Color.rgb(255, 255, 0));
                canvas.drawLine(centerOfTowerImage.x, centerOfTowerImage.y,
                        centerOfEnemy.x, centerOfEnemy.y, paint);
                //make enemies not shootable on pause
                if(!gameState.getPaused()) {
                    enemy.enemyShot(damagePerShot, i);
                }
            }
        }

        if (enemyInRange) {
                paint.setColor(Color.argb(50, 255, 0, 0));
                canvas.drawRect(range, paint);
        } else if (towerClicked){
                paint.setColor(Color.argb(50, 255, 255, 255));
                canvas.drawRect(range, paint);
        }
    }

    //check if an enemy is in range
    private boolean enemyInRange(Point centerOfEnemy) {
            return range.contains((centerOfEnemy.x), (centerOfEnemy.y));
    }

    public Rect rangeOfTower() {
        return range;
    }
}
