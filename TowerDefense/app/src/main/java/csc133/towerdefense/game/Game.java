package csc133.towerdefense.game;

import android.graphics.Canvas;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Random;

import csc133.towerdefense.game.gameobject.Bullet;
import csc133.towerdefense.game.gameobject.enemy.AlienCreator;
import csc133.towerdefense.game.gameobject.enemy.BasicAlienBuilder;
import csc133.towerdefense.game.gameobject.enemy.Enemy;
import csc133.towerdefense.game.gameobject.enemy.MidGradeAlienBuilder;
import csc133.towerdefense.game.gameobject.tower.BouncingBettyTowerBuilder;
import csc133.towerdefense.game.gameobject.tower.MachineGunTowerBuilder;
import csc133.towerdefense.game.gameobject.tower.SniperTowerBuilder;
import csc133.towerdefense.game.gameobject.tower.Tower;
import csc133.towerdefense.game.gameobject.tower.TowerCreator;
import csc133.towerdefense.game.helpers.Functions;
import csc133.towerdefense.game.level.Wave;
import csc133.towerdefense.game.movepath.MovePath;

// bro im lazy this is the renderer, game state, and the physics engine
public class Game {
    ArrayList<Enemy> enemies;
    ArrayList<Bullet> bullets;
    ArrayList<Tower> towers;
    ArrayList<Pair<Enemy, Bullet>> enemyBulletCollisions;
    ArrayList<Pair<Enemy, Tower>> enemyTowerCollisions;
    public int gold;
    public int lives;

    public int getEnemies() {
        return enemies.size();
    }


    public Game() {
        init();

    }

    public void init() {
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        towers = new ArrayList<>();
        enemyBulletCollisions = new ArrayList<>();
        enemyTowerCollisions = new ArrayList<>();
        newWave();
    }

    public void reset() {
        this.gold = 100;
        this.lives = 10;
        enemies.clear();
        bullets.clear();
        towers.clear();
        enemyBulletCollisions.clear();
        enemyTowerCollisions.clear();
    }

    public void newWave() {
        this.gold = 100;
        enemies.clear();
        bullets.clear();
        towers.clear();
        enemyBulletCollisions.clear();
        enemyTowerCollisions.clear();
    }

    public void update() {
        enemyBulletCollisions.clear();
        enemyTowerCollisions.clear();

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

        checkCollisions();

        handleCollisions();

        for (int i = 0; i < enemies.size(); ++i) {
            enemies.get(i).checkDeath();
            if (enemies.get(i).toDestroy) {
                gold += enemies.get(i).goldValue;
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

    }

    private void checkCollisions() {
        for (int i = 0; i < enemies.size(); ++i) {
            Enemy enemy = enemies.get(i);
            // check if any enemies are colliding with bullets
            for (int j = 0; j < bullets.size(); ++j) {
                Bullet bullet = bullets.get(j);
                if (Functions.circleInRect(bullet.x, bullet.y, bullet.width / 2,
                        enemy.x, enemy.y, enemy.width, enemy.height)) {
                    enemyBulletCollisions.add(new Pair<>(enemy, bullet));
                } else if (Functions.lineInRect(bullet.prevX, bullet.prevY, bullet.x, bullet.y,
                        enemy.x, enemy.y, enemy.width, enemy.height)) {
                    enemyBulletCollisions.add(new Pair<>(enemy, bullet));
                }
            }
            // then check if they are colliding with towers
            for (int j = 0; j < towers.size(); ++j) {
                Tower tower = towers.get(j);
                if (Functions.circleInRect(tower.x, tower.y, tower.attackRadius, enemy.x, enemy.y, enemy.width, enemy.height)) {
                    enemyTowerCollisions.add(new Pair<>(enemy, tower));
                }
            }
        }
    }

    private void handleCollisions() {
        // handle enemy and bullet collisions
        for (int i = 0; i < enemyBulletCollisions.size(); ++i) {
            Pair p = enemyBulletCollisions.get(i);
            Enemy enemy = (Enemy)p.first;
            Bullet bullet = (Bullet)p.second;

            // check if bullet is alive to do dmg (for the line checks specifically)
            if (bullet.health > 0) {
                bullet.health--;
                enemy.health -= bullet.damage;
            }
        }

        // handle tower (attack radius) and enemy collisions
        for (int i = 0; i < enemyTowerCollisions.size(); ++i) {
            Pair p = enemyTowerCollisions.get(i);
            Enemy enemy = (Enemy)p.first;
            Tower tower = (Tower)p.second;

            tower.setTarget(enemy);
        }

        // lazy code
        for (Enemy enemy : enemies) {
            MovePath path = enemy.path;
            if (Functions.rectInRect(enemy.x, enemy.y, enemy.width, enemy.height, path.endX, path.endY, path.width, path.width)) {
                enemy.toDestroy = true;
                --lives;
            }
        }
    }

    public void draw(Canvas canvas) {
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
