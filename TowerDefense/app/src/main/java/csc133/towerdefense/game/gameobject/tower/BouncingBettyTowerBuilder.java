package csc133.towerdefense.game.gameobject.tower;

public class BouncingBettyTowerBuilder implements ITowerBuilder {
    private Tower tower;

    public void newTower() {
        this.tower = new BettyTower();
    }

    public void setSize() {
        this.tower.width = 70;
        this.tower.height = 70;
    }

    public void setPosition(float x, float y) {
        this.tower.x = x;
        this.tower.y = y;
    }

    public void setTag() {
        this.tower.tag = "BouncingBettyTower";
    }

    public void setAttackSpeed() {
        this.tower.attackSpeed = 4;
    }

    public void setBulletStats() {
        this.tower.bulletSize = 8;
        this.tower.bulletSpeed = 15;
        this.tower.bulletDamage = 4;
        this.tower.bulletHealth = 2;
    }

    public void setAttackRadius() {
        this.tower.attackRadius = 150;
    }

    public void setBuyPrice() {
        this.tower.buyPrice = 100;
    }

    public void setSellPrice() {
        this.tower.sellPrice = 25;
    }

    public Tower getTower() {
        return tower;
    }
}