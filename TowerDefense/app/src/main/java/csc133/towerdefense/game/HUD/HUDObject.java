package csc133.towerdefense.game.HUD;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import csc133.towerdefense.game.GameEngine;
import csc133.towerdefense.game.IDrawable;

public class HUDObject implements IDrawable {

    public float x;
    public float y;
    public float width;
    public float height;
    public String tag;
    public String image;

    Bitmap bitmap;

    public HUDObject(float x, float y, float width, float height, String tag, String image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.tag = tag;
        this.image = image;

        init();
    }

    public void init() {
        Context c = GameEngine.context;

        // Make a resource id out of the string of the file name
        int resID = c.getResources().getIdentifier(image,
                "drawable", c.getPackageName());

        bitmap = BitmapFactory.decodeResource(c.getResources(), resID);

        bitmap = Bitmap
                .createScaledBitmap(bitmap,
                        (int)width,
                        (int)height,
                        false);
    }

    @Override
    public void draw(Canvas canvas) {
        // draw the bitmap
        canvas.drawBitmap(bitmap, x, y, null);
    }

}
