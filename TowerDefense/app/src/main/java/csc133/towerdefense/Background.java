package csc133.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Background {

    private Bitmap mBitmap;

    public void initialize(Context c, String s, Point objectSize) {

        // Make a resource id out of the string of the file name
        int resID = c.getResources().getIdentifier(s,
                "drawable", c.getPackageName());

        mBitmap = BitmapFactory.decodeResource(c.getResources(), resID);

        mBitmap = Bitmap
                .createScaledBitmap(mBitmap,
                        objectSize.x,
                        objectSize.y,
                        false);
    }

    public void draw(Canvas canvas, Paint paint) {

        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();

        // For the regular bitmap
        Rect fromRect1 = new Rect(0, 0, width, height);
        Rect toRect1 = new Rect(0, 0, width, height);

        //draw the two background bitmaps
        canvas.drawBitmap(mBitmap, fromRect1, toRect1, paint);
    }
}
