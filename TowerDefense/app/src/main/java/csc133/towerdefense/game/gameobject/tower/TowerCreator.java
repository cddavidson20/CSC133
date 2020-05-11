package csc133.towerdefense.game.gameobject.tower;

public class TowerCreator {
    ITowerBuilder towerBuilder;

    public TowerCreator(ITowerBuilder towerBuilder) {
        this.towerBuilder = towerBuilder;
    }

    public Tower createTower(float x, float y) {
        towerBuilder.newTower();

        towerBuilder.setSize();
        towerBuilder.setPosition(x, y);
        towerBuilder.setAttackSpeed();
        towerBuilder.setBulletStats();
        towerBuilder.setAttackRadius();
        towerBuilder.setBuyPrice();
        towerBuilder.setSellPrice();
        towerBuilder.setTag();

        return towerBuilder.getTower();
    }
}
