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
    private ArrayList<HUDObject> buttons;
    public RectF hitbox;
    public HUD() {
        buttons = new ArrayList<>();
        init();
    }

    private void init() {
        buttons.add(new HUDObject(250, 50, 200, 200, "reset", "reset"));
        buttons.add(new ToggleHUDObject(500, 75, 200, 150, "pause", "play_button",
                "pause_button", true));
        buttons.add(new HUDObject(750, 50, 200, 200, "machineguntower", "machine_gun"));
        buttons.add(new HUDObject(1000, 50, 200, 200, "bouncingbettytower", "bouncing_betty"));
        buttons.add(new HUDObject(1250, 50, 200, 200, "snipertower", "sniper"));
        hitbox = new RectF(0, 0, GameEngine.size.x, 250);
    }

    public HUDObject getButtonByTag(String tag) {
        for (int i = 0; i < buttons.size(); ++i) {
            if (buttons.get(i).tag.equals(tag)) return buttons.get(i);
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
        // HUD bar
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);
        paint.setAlpha(255 / 2);
        canvas.drawRect(hitbox, paint);
        for (int i = 0; i < buttons.size(); ++i) {
            buttons.get(i).draw(canvas);
        }
        // turret info
        paint.setAlpha(255);
        paint.setColor(Color.YELLOW);
        paint.setTextSize(25);
        canvas.drawText("100G", 825, 250, paint);
        canvas.drawText("150G", 1075, 250, paint);
        canvas.drawText("200G", 1325, 250, paint);
    }
}
