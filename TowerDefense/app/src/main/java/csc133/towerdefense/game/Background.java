package csc133.towerdefense.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

public class Background {

    private Bitmap bitmap;

    public void initialize(String background) {
        Context c = GameEngine.context;
        Point objectSize = GameEngine.size;

        // Make a resource id out of the string of the file name
        int resID = c.getResources().getIdentifier(background,
                "drawable", c.getPackageName());

        bitmap = BitmapFactory.decodeResource(c.getResources(), resID);

        bitmap = Bitmap
                .createScaledBitmap(bitmap,
                        objectSize.x,
                        objectSize.y,
                        false);
    }

    public void draw(Canvas canvas) {
        // draw the bitmap
        canvas.drawBitmap(bitmap, 0, 0, null);
    }
}