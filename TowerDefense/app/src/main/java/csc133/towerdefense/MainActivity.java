package csc133.towerdefense;

import android.app.Activity;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import csc133.towerdefense.game.GameEngine;

public class MainActivity extends Activity {
    GameEngine gameLoop;
    Paint paint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        paint = new Paint();

        gameLoop = new GameEngine(this, size);

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
