package csc133.towerdefense.game.gameobject.enemy;

import java.util.Random;

import csc133.towerdefense.game.movepath.MovePath;

public class MidGradeAlienBuilder implements IEnemyBuilder {
    static final float SIZE = 55;
    static final float HEALTH_MAX = 50;
    static final float SPEED = 2;
    private Enemy enemy;

    public MidGradeAlienBuilder() {
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

    public void setGoldValue() {
        this.enemy.goldValue = 10;
    }

    public void setSpeed() {
        this.enemy.speed = SPEED;
    }

    public void setSize() {
        this.enemy.width = SIZE;
        this.enemy.height = SIZE;
    }

    // explained in basic alien builder
    public void setOffset() {
        Random random = new Random();
        float randX = (random.nextBoolean() ? 1 : -1) * ((float) Math.random() * this.enemy.path.width / 2 - SIZE / 2);
        float randY = (random.nextBoolean() ? 1 : -1) * ((float) Math.random() * this.enemy.path.width / 2 - SIZE / 2);

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
