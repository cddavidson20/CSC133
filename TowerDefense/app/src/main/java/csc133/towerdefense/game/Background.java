package csc133.towerdefense.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Background {

    private Bitmap bitmap;

    public void initialize(Context c, String background, Point objectSize) {

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

    public void draw(Canvas canvas, Paint paint) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // For the regular bitmap
        Rect fromRect1 = new Rect(0, 0, width, height);
        Rect toRect1 = new Rect(0, 0, width, height);

        // draw the bitmap
        canvas.drawBitmap(bitmap, fromRect1, toRect1, paint);
    }
}