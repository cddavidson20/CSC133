package csc133.towerdefense.game;

import android.graphics.Canvas;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Random;

import csc133.towerdefense.game.gameobject.Bullet;
import csc133.towerdefense.game.gameobject.enemy.Enemy;
import csc133.towerdefense.game.helpers.Functions;
import csc133.towerdefense.game.movepath.MovePath;

public class Game {
    ArrayList<Enemy> enemies;
    ArrayList<Bullet> bullets;
    MovePath[] paths;
    public int level;
    ArrayList<Pair<Enemy, Bullet>> collisions;

    public Game() {
        init();
        reset();

        Random random = new Random();
        for (int i = 0; i < 10; ++i) {
            float randX = (random.nextBoolean() ? 1 : -1 ) * ((float)Math.random() * paths[level].width / 2 - Enemy.ENEMY_SIZE / 2);
            float randY = (random.nextBoolean() ? 1 : -1 ) * ((float)Math.random() * paths[level].width / 2 - Enemy.ENEMY_SIZE / 2);
            enemies.add(new Enemy(-30 * i, paths[level].width / 2, paths[level], randX, randY));
        }

        enemies.add(new Enemy( 1000, 1000, paths[level]));
        bullets.add(new Bullet(500, 20, 21, 90));
        bullets.add(new Bullet(400, 40, 102, 90));
        bullets.add(new Bullet(800, 40, 100, 100));

    }

    public void init() {
        enemies = new ArrayList<Enemy>();
        bullets = new ArrayList<Bullet>();
        collisions = new ArrayList<Pair<Enemy, Bullet>>();
        paths = Paths.Paths;
    }

    public void reset() {
        level = 0;
    }

    public void update() {
        collisions.clear();

        for (int i = 0; i < enemies.size(); ++i) {
            enemies.get(i).update();
        }
        for (int i = 0; i < bullets.size(); ++i) {
            bullets.get(i).update();
        }

        for (int i = 0; i < bullets.size(); ++i) {
            Bullet bullet = bullets.get(i);
            for (int j = 0; j < enemies.size(); ++j) {
                Enemy enemy = enemies.get(j);
                if (Functions.rectInCircle(bullet.x, bullet.y, bullet.width / 2,
                        enemy.x, enemy.y, enemy.width, enemy.height)) {
                    collisions.add(new Pair<>(enemy, bullet));
                }
            }
        }

        handleCollisions();

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

    }

    private void handleCollisions() {
        for (int i = 0; i < collisions.size(); ++i) {
            Pair p = collisions.get(i);
            Enemy enemy = (Enemy)p.first;
            Bullet bullet = (Bullet)p.second;
            if (enemy == null || bullet == null) continue;

            bullet.toDestroy = true;
            enemy.health -= 1;
        }
    }

    public void draw(Canvas canvas) {
        paths[level].draw(canvas);

        for (int i = 0; i < enemies.size(); ++i) {
            enemies.get(i).draw(canvas);
        }
        for (int i = 0; i < bullets.size(); ++i) {
            bullets.get(i).draw(canvas);
        }
    }
}
