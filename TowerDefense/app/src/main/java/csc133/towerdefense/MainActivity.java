package csc133.towerdefense;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import csc133.towerdefense.game.GameEngine;

public class MainActivity extends Activity {
    GameEngine gameEngine;
    int numberHorizontalPixels;
    int numberVerticalPixels;
    int blockSize;
    int gridWidth = 1;
    int gridHeight;

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

        gameEngine = new GameEngine(this, size);

        setContentView(gameEngine);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameEngine.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameEngine.resume();
    }

}
