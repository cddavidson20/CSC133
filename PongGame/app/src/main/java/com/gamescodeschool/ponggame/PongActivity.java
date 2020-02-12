package com.gamescodeschool.ponggame;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class PongActivity extends Activity {
    private RunPongGame mPongGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        mPongGame = new RunPongGame(this, size.x, size.y);
        setContentView(mPongGame);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // More code here later in the chapter
        mPongGame.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // More code here later in the chapter
        mPongGame.pause();
    }
}