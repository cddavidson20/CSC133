package csc133.towerdefense.game.gameobject.tower;

public interface ITowerBuilder {
    void newTower();

    void setSize();

    void setPosition(float x, float y);

    void setAttackSpeed();

    void setBulletStats();

    void setAttackRadius();

    void setBuyPrice();

    void setSellPrice();

    Tower getTower();
}
