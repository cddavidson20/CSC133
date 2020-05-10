package csc133.towerdefense.game.gameobject.enemy;

import csc133.towerdefense.game.movepath.MovePath;

public class AlienCreator {
    IEnemyBuilder enemyBuilder;
    public AlienCreator(IEnemyBuilder enemyBuilder) {
        this.enemyBuilder = enemyBuilder;
    }
    public Enemy createAlien(float x, float y, MovePath path, boolean haveOffset) {
        enemyBuilder.newEnemy();
        enemyBuilder.setStart(x, y);
        enemyBuilder.setHealth();
        enemyBuilder.setSpeed();
        enemyBuilder.setSize();
        enemyBuilder.setPath(path);
        if (haveOffset) enemyBuilder.setOffset();
        return enemyBuilder.getEnemy();
    }
}
