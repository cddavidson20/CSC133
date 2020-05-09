package csc133.towerdefense;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import csc133.towerdefense.game.GameLoop;

/**
 *
 */
public class MainActivity extends Activity {
    GameLoop gameLoop;
    Bitmap blankBitmap;
    int numberHorizontalPixels, numberVerticalPixels, blockSize,
            gridWidth = 1, gridHeight;
    Canvas canvas;
    Paint paint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        numberHorizontalPixels = size.x;
        numberVerticalPixels = size.y;
        blockSize = numberHorizontalPixels / gridWidth;
        gridHeight = numberVerticalPixels / blockSize;
        blankBitmap = Bitmap.createBitmap(numberHorizontalPixels, numberVerticalPixels, Bitmap.Config.ARGB_8888);

        canvas = new Canvas(blankBitmap);
        paint = new Paint();

        gameLoop = new GameLoop(this, blockSize, size);

        setContentView(gameLoop);

    }

    @Override
    protected void onPause() {
        super.onPause();
        gameLoop.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameLoop.resume();
    }

}
