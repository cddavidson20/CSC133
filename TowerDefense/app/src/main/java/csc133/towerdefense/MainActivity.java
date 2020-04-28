package csc133.towerdefense;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class MainActivity extends Activity {

    TowerGame mTowerGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        mTowerGame = new TowerGame(this, size);

        setContentView(mTowerGame);
    }

    // Start the thread in towerGame
    @Override
    protected void onResume() {
        super.onResume();
        mTowerGame.resume();
    }

    // Stop the thread in towerGame
    @Override
    protected void onPause() {
        super.onPause();
        mTowerGame.pause();
    }
}
