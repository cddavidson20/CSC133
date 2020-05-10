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
import csc133.towerdefense.game.movepath.MovePath;

public class Game {
    ArrayList<Enemy> enemies;
    ArrayList<Bullet> bullets;
    ArrayList<Tower> towers;
    MovePath[] paths;
    public int level;
    ArrayList<Pair<Enemy, Bullet>> enemyBulletCollisions;
    ArrayList<Pair<Enemy, Tower>> enemyTowerCollisions;

    AlienCreator alienCreator;
    TowerCreator towerCreator;

    public MovePath getCurrPath() {
        return paths[level];
    }

    public Game() {
        init();
        reset();

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

    public void init() {
        enemies = new ArrayList<Enemy>();
        bullets = new ArrayList<Bullet>();
        towers = new ArrayList<Tower>();
        enemyBulletCollisions = new ArrayList<Pair<Enemy, Bullet>>();
        enemyTowerCollisions = new ArrayList<Pair<Enemy, Tower>>();
        paths = Paths.Paths;
    }

    public void reset() {
        level = 0;
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
    }

    public void draw(Canvas canvas) {
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
