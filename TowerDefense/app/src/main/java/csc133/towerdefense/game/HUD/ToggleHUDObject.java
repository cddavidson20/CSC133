package csc133.towerdefense.game.HUD;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import csc133.towerdefense.game.GameEngine;

// changes appearance of hud objects depending on current player action
public class ToggleHUDObject extends HUDObject {

    private boolean bool;
    private String trueImage;
    private String falseImage;
    private Bitmap trueBitmap;
    private Bitmap falseBitmap;

    public ToggleHUDObject(float x, float y, float width, float height, String tag, String trueImage, String falseImage, boolean bool) {
        super(x, y, width, height, tag, trueImage);
        this.bool = bool;
        this.trueImage = trueImage;
        this.falseImage = falseImage;
        generateBitmaps();
        this.setBool(bool);
    }

    private void generateBitmaps() {
        Context c = GameEngine.context;

        // Make a resource id out of the string of the file name
        int resID = c.getResources().getIdentifier(image,
                "drawable", c.getPackageName());

        bitmap = BitmapFactory.decodeResource(c.getResources(), resID);

        bitmap = Bitmap
                .createScaledBitmap(bitmap,
                        (int) width,
                        (int) height,
                        false);
        this.image = trueImage;
        init();
        trueBitmap = bitmap;
        this.image = falseImage;
        init();
        falseBitmap = bitmap;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
        if (bool) {
            this.bitmap = trueBitmap;
        } else {
            this.bitmap = falseBitmap;
        }
    }
}
