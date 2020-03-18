package csc133.towerdefense.GameObjects.towers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import csc133.towerdefense.R;

public class DefenseTower extends AbstractTower {

    private Bitmap bitmap;
    private int positionX;
    private int positionY;


    public DefenseTower() {
        super();
    }

    public DefenseTower(Context context, Point position) {
        super(context, position);
        positionX = position.x;
        positionY = position.y;

        bitmap = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.defense_tower);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmap, positionX, positionY, paint);
    }

}
