package csc133.towerdefense;

import android.graphics.Rect;
import android.view.MotionEvent;


import java.util.ArrayList;

import csc133.towerdefense.GameObjects.*;

class UIController implements InputObserver {

    private float xAxis = 0f;
    private float yAxis = 0f;

    private float lastXAxis = 0f;
    private float lastYAxis = 0f;

    UIController(GameEngineBroadcaster b){
        b.addObserver(this);
    }

    @Override
    public void handleInput(MotionEvent event, GameState gameState, GameObject gameObject, ArrayList<Rect> buttons) {

        final int actionPreformed = event.getAction();

        switch(actionPreformed) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_DOWN: {
                final float x = event.getX();
                final float y = event.getY();
                if (lastXAxis != 0f && lastYAxis != 0f) {
                    if (buttons.get(HUD.TOWER).contains((int) lastXAxis, (int) lastYAxis)) {
                        towerPressed(gameObject, x, y);
                    }
                }

                lastXAxis = x;
                lastYAxis = y;
                if (buttons.get(HUD.PAUSE).contains((int) x, (int) y)) {
                    pausePressed(gameState);
                }
                if (buttons.get(HUD.RESET).contains((int) x, (int) y)) {
                    resetPressed(gameObject);
                }

                break;
            }

            case MotionEvent.ACTION_MOVE: {
                final float x = event.getX();
                final float y = event.getY();

                final float dx = x - lastXAxis;
                final float dy = y - lastYAxis;

                xAxis += dx;
                yAxis += dy;

                break;
            }
        }

    }

    private void pausePressed(GameState gameState) {
        // Player pressed the pause button
        // Respond differently depending upon the game's state

        // If the game is not paused
        if (!gameState.getPaused()) {
            // Pause the game
            gameState.pause();
        }

        // If game is over start a new game
        else if (gameState.getGameOver()) {

            gameState.startNewGame();
        }

        // Paused and not game over
        else if (gameState.getPaused()
                && !gameState.getGameOver()) {

            gameState.resume();
        }
    }

    private void resetPressed(GameObject gameObject) {
        gameObject.newGame();
    }

    private void towerPressed(GameObject gameObject, float x, float y) {
        gameObject.newTowerLocation( (int) x, (int) y);
    }

}
