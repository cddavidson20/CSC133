package csc133.towerdefense.game.HUD;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;

import csc133.towerdefense.game.GameEngine;
import csc133.towerdefense.game.IDrawable;
import csc133.towerdefense.game.helpers.Functions;

public class HUD implements IDrawable {
    ArrayList<HUDObject> buttons;
    public RectF hitbox;
    public HUD() {
        buttons = new ArrayList<>();
        init();
    }
    void init() {
        buttons.add(new HUDObject(50, 50, 200, 200, "reset", "bullet"));
        buttons.add(new ToggleHUDObject(300, 50, 200, 200, "pause", "bullet", "defense_tower", true));
        buttons.add(new HUDObject(500, 50, 200, 200, "machineguntower", "bullet"));
        buttons.add(new HUDObject(750, 50, 200, 200, "bouncingbettytower", "bullet"));
        buttons.add(new HUDObject(1000, 50, 200, 200, "snipertower", "bullet"));
        hitbox = new RectF(0, 0, GameEngine.size.x, 250);
    }

    public HUDObject getButtonByTag(String tag) {
        for (int i = 0; i < buttons.size(); ++i) {
            if (buttons.get(i).tag == tag) return buttons.get(i);
        }
        return null;
    }

    public String checkPressed(float x, float y) {
        for (HUDObject obj : buttons) {
            if (Functions.pointInRect(x, y, obj.x + obj.width / 2, obj.y + obj.height / 2, obj.width, obj.height)) {
                return obj.tag;
            }
        }
        return "";
    }


    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);
        canvas.drawRect(hitbox, paint);
        for (int i = 0; i < buttons.size(); ++i) {
            buttons.get(i).draw(canvas);
        }
    }
}
