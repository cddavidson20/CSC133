package csc133.towerdefense.game.gameobject.tower;

import java.util.ArrayList;

import csc133.towerdefense.game.GameEngine;
import csc133.towerdefense.game.SoundEngine;
import csc133.towerdefense.game.gameobject.Bullet;

public class BettyTower extends Tower {
    public BettyTower() {
        super();
    }

    @Override
    public void tryToFire(ArrayList<Bullet> bullets) {
        if (willFire && target != null) {
            int shots = 8;
            for (int i = 0; i < shots; ++i) {
                if (i == 0) {
                    SoundEngine.getSoundEngine().playMachineGun();
                }
                float rad = (float) ((i * ((2 * Math.PI) / shots)) + (Math.PI * rotation / 180));
                float xx = x + (float) Math.cos(rad);
                float yy = y + (float) Math.sin(rad);

                float xt = x + (float) Math.cos(rad) * 2;
                float yt = y + (float) Math.sin(rad) * 2;

                bullets.add(new Bullet(xx, yy, xt, yt, bulletSize, bulletSpeed, bulletDamage, bulletHealth));
            }

            lastFired = GameEngine.framesRan;
        }
    }
}
