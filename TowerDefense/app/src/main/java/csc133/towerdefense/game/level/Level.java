package csc133.towerdefense.game.level;

public class Level {
    // If you want to build a new level then extend this class
    public Wave[] waves;

    public Level(Wave[] waves) {
        this.waves = waves;
    }
}
