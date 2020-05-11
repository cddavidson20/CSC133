package csc133.towerdefense.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import java.util.ArrayList;
import java.util.Random;

import csc133.towerdefense.game.gameobject.Bullet;
import csc133.towerdefense.game.gameobject.GameObject;
import csc133.towerdefense.game.gameobject.enemy.AlienCreator;
import csc133.towerdefense.game.gameobject.enemy.BasicAlienBuilder;
import csc133.towerdefense.game.gameobject.enemy.Enemy;
import csc133.towerdefense.game.gameobject.enemy.MidGradeAlienBuilder;
import csc133.towerdefense.game.gameobject.tower.BouncingBettyTowerBuilder;
import csc133.towerdefense.game.gameobject.tower.MachineGunTowerBuilder;
import csc133.towerdefense.game.gameobject.tower.SniperTowerBuilder;
import csc133.towerdefense.game.gameobject.tower.Tower;
import csc133.towerdefense.game.gameobject.tower.TowerCreator;
import csc133.towerdefense.game.movepath.MovePath;

class GameObjectSystem {
    ArrayList<Enemy> enemies;
    ArrayList<Bullet> bullets;
    ArrayList<Tower> towers;

    AlienCreator alienCreator;
    TowerCreator towerCreator;

    float mDuration;
    ArrayList<GameObject> mParticles;
    Random random = new Random();
    boolean mIsRunning = false;
    MovePath[] paths;

    public MovePath getCurrPath() {
        return paths[level];
    }

    void init() {
        enemies = new ArrayList<Enemy>();
        bullets = new ArrayList<Bullet>();
        towers = new ArrayList<Tower>();
        paths = Paths.Paths;

        // Create the particles
        float startY = getCurrPath().getStartingPoint().y;

        Random random = new Random();
        for (int i = 0; i < 500; ++i) {
            if (random.nextInt(100) > 80) {
                alienCreator = new AlienCreator(new BasicAlienBuilder());
            } else {
                alienCreator = new AlienCreator((new MidGradeAlienBuilder()));
            }
            Enemy enemy = alienCreator.createAlien(-30 * i, startY, getCurrPath(), true);
            enemies.add(enemy);
        }
        alienCreator = new AlienCreator(new MidGradeAlienBuilder());
        enemies.add(alienCreator.createAlien(1000, 1000, getCurrPath(), true));

        towerCreator = new TowerCreator(new BouncingBettyTowerBuilder());
        towers.add(towerCreator.createTower(200, 200));
        towerCreator = new TowerCreator(new SniperTowerBuilder());
        towers.add(towerCreator.createTower(1300, 700));
        towerCreator = new TowerCreator(new MachineGunTowerBuilder());
        towers.add(towerCreator.createTower(600, 200));
    }

    void update(long fps){
        mDuration -= (1f/fps);

        for (int i = 0; i < enemies.size(); ++i) {
            enemies.get(i).update();
        }
        for (int i = 0; i < bullets.size(); ++i) {
            bullets.get(i).update();
        }
        for (int i = 0; i < towers.size(); ++i) {
            towers.get(i).tryToFire(bullets);
            towers.get(i).update();
        }

        for (int i = 0; i < enemies.size(); ++i) {
            enemies.get(i).checkDeath();
            if (enemies.get(i).toDestroy) {
                enemies.remove(i);
                --i;
            }
        }
        for (int i = 0; i < bullets.size(); ++i) {
            if (bullets.get(i).toDestroy) {
                bullets.remove(i);
                --i;
            }
        }
        for (int i = 0; i < towers.size(); ++i) {
            if (towers.get(i).toDestroy) {
                towers.remove(i);
                --i;
            }
        }


        if (mDuration < 0)
        {
            mIsRunning = false;
        }
    }

    void emitParticles(PointF startPosition){
        mIsRunning = true;
        mDuration = 1f;
        for(Particle p : mParticles){
            p.setPosition(startPosition);
        }
    }

    void draw(Canvas canvas, Paint paint){
        paths[level].draw(canvas);


        for (int i = 0; i < enemies.size(); ++i) {
            enemies.get(i).draw(canvas);
        }
        for (int i = 0; i < bullets.size(); ++i) {
            bullets.get(i).draw(canvas);
        }
        for (int i = 0; i < towers.size(); ++i) {
            towers.get(i).draw(canvas);
        }
    }
}
