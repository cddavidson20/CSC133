package csc133.towerdefense.game.gameobject.tower;

public class MachineGunTowerBuilder implements ITowerBuilder {
    private Tower tower;

    public void newTower() {
        this.tower = new Tower();
    }

    public void setSize() {
        this.tower.width = 80;
        this.tower.height = 80;
    }

    public void setPosition(float x, float y) {
        this.tower.x = x;
        this.tower.y = y;
    }

    public void setAttackSpeed() {
        this.tower.attackSpeed = 10;
    }

    public void setBulletStats() {
        this.tower.bulletSize = 5;
        this.tower.bulletSpeed = 10;
        this.tower.bulletDamage = 5;
        this.tower.bulletHealth = 1;
    }

    public void setAttackRadius() {
        this.tower.attackRadius = 150;
    }

    public void setBuyPrice() {
        this.tower.buyPrice = 100;
    }

    public void setBitmap() {
        tower.initBitmap("machine_gun");
    }

    public void setSellPrice() {
        this.tower.sellPrice = 25;
    }

    public Tower getTower() {
        return tower;
    }
}