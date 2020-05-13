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

    public void setAttackSpeed() {
        this.tower.attackSpeed = 4;
    }

    public void setBulletStats() {
        this.tower.bulletSize = 8;
        this.tower.bulletSpeed = 15;
        this.tower.bulletDamage = 3;
        this.tower.bulletHealth = 1;
    }

    public void setAttackRadius() {
        this.tower.attackRadius = 150;
    }

    public void setBuyPrice() {
        this.tower.buyPrice = 150;
    }

    public void setBitmap() {
        tower.initBitmap("bouncing_betty");
    }


    public void setSellPrice() {
        this.tower.sellPrice = 25;
    }

    public Tower getTower() {
        return tower;
    }
}