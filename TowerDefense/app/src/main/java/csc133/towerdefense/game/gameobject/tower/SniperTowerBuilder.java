package csc133.towerdefense.game.gameobject.tower;

public class SniperTowerBuilder implements ITowerBuilder {
    private Tower tower;

    public void newTower() {
        this.tower = new Tower();
    }

    public void setSize() {
        this.tower.width = 100;
        this.tower.height = 100;
    }

    public void setPosition(float x, float y) {
        this.tower.x = x;
        this.tower.y = y;
    }

    public void setAttackSpeed() {
        this.tower.attackSpeed = 1.2f;
    }

    public void setBulletStats() {
        this.tower.bulletSize = 14;
        this.tower.bulletSpeed = 100;
        this.tower.bulletDamage = 50;
        this.tower.bulletHealth = 4;
    }

    public void setAttackRadius() {
        this.tower.attackRadius = 500;
    }

    public void setBuyPrice() {
        this.tower.buyPrice = 200;
    }

    public void setSellPrice() {
        this.tower.sellPrice = 50;
    }

    public Tower getTower() {
        return tower;
    }
}