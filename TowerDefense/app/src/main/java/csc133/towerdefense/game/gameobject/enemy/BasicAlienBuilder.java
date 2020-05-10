package csc133.towerdefense.game.gameobject.enemy;

import java.util.Random;

import csc133.towerdefense.game.movepath.MovePath;

public class BasicAlienBuilder implements IEnemyBuilder {
    static final float SIZE = 35;
    static final float HEALTH_MAX = 10;
    static final float SPEED = 5;
    private Enemy enemy;
    public BasicAlienBuilder() {
        this.enemy = new Enemy();
    }
    public void newEnemy() {
        this.enemy = new Enemy();
    }
    public void setStart(float x, float y) {
        this.enemy.x = x;
        this.enemy.y = y;
    }
    public void setHealth() {
        this.enemy.healthMax = HEALTH_MAX;
        this.enemy.health = HEALTH_MAX;
    }
    public void setSpeed() {
        this.enemy.speed = SPEED;
    }
    public void setSize() {
        this.enemy.width = SIZE;
        this.enemy.height = SIZE;
    }
    public void setOffset() {
        Random random = new Random();
        float randX = (random.nextBoolean() ? 1 : -1 ) * ((float)Math.random() * this.enemy.path.width / 2 - SIZE / 2);
        float randY = (random.nextBoolean() ? 1 : -1 ) * ((float)Math.random() * this.enemy.path.width / 2 - SIZE / 2);

        this.enemy.offsetX = randX;
        this.enemy.offsetY = randY;
    }
    public void setPath(MovePath path) {
        this.enemy.setPath(path);
    }
    public Enemy getEnemy() {
        return enemy;
    }



}
