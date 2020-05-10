package csc133.towerdefense.game.gameobject.enemy;

import csc133.towerdefense.game.movepath.MovePath;

public interface IEnemyBuilder {
    void newEnemy();

    void setStart(float x, float y);

    void setHealth();

    void setSpeed();

    void setOffset();

    void setSize();

    void setPath(MovePath path);

    Enemy getEnemy();
}
