package csc133.towerdefense.game;

import android.util.Pair;

import java.util.ArrayList;

import csc133.towerdefense.game.gameobject.Bullet;
import csc133.towerdefense.game.gameobject.GameObject;
import csc133.towerdefense.game.gameobject.enemy.Enemy;
import csc133.towerdefense.game.gameobject.tower.Tower;
import csc133.towerdefense.game.helpers.Functions;

class PhysicsEngine {
    // This signature and much more will
    //change later in the project
    boolean update(long fps, ArrayList<GameObject> gameObjects, GameState gs){
        if(gos.mIsRunning){
            gos.update(fps);
        }
        return false;
    }

    // Collision detection method will go here
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
}