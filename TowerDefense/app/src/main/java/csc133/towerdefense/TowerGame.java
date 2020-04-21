package csc133.towerdefense;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import csc133.towerdefense.GameObjects.GameObject;

public class TowerGame extends SurfaceView implements Runnable, GameEngineBroadcaster {

    // Objects for the game loop/thread
    private Thread mThread = null;
    // Control pausing between updates
    private long mNextFrameTime;
    // Is the game currently playing and or paused?

    private ArrayList<InputObserver> inputObservers = new ArrayList();
    UIController uiController;

    // Objects for drawing
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;

    // Is the game currently playing and or paused?
    private GameState gameState;
    private HUD hud;

    private GameObject gameObjects;

    private Background background;
    private String mBitMapName;

    public TowerGame(Context context, Point size) {
        super(context);

        uiController = new UIController(this);
        mSurfaceHolder = getHolder();
        mPaint = new Paint();
        gameState = new GameState(context);
        hud = new HUD(size);
        gameObjects = new GameObject(context, size);

        mBitMapName = "background";
        background = new Background();
        background.initialize(context, mBitMapName, size);

    }

    public void draw(){
        if (mSurfaceHolder.getSurface().isValid()) {
            mCanvas = mSurfaceHolder.lockCanvas();

            // Fill the screen with a color
            background.draw(mCanvas, mPaint);

            // Set the size and color of the mPaint for the text
            mPaint.setColor(Color.argb(255, 255, 255, 255));
            mPaint.setTextSize(120);

            gameObjects.draw(mCanvas, mPaint);
            hud.draw(mCanvas, mPaint, gameState);

            // Unlock the mCanvas and reveal the graphics for this frame
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    // Called to start a new game
    public void newGame() {
        gameObjects.newGame();
        // Setup mNextFrameTime so an update can triggered
        mNextFrameTime = System.currentTimeMillis();
    }

    // For the game engine broadcaster interface
    public void addObserver(InputObserver o) {
        inputObservers.add(o);
    }

    // Handles the game loop
    @Override
    public void run() {
        while (gameState.getThreadRunning()) {
            if (!gameState.getPaused()) {
                // Update 10 times a second
                if (updateRequired()) {
                    update();
                }
            }

           draw();
        }
    }

    public void update() {
        gameObjects.update();
    }

    // Check to see if it is time for an update
    public boolean updateRequired() {

        // Run at 10 frames per second
        final long TARGET_FPS = 10;
        // There are 1000 milliseconds in a second
        final long MILLIS_PER_SECOND = 1000;

        // Are we due to update the frame
        if (mNextFrameTime <= System.currentTimeMillis()) {
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            mNextFrameTime = System.currentTimeMillis()
                    + MILLIS_PER_SECOND / TARGET_FPS;

            // Return true so that the update and draw
            // methods are executed
            return true;
        }

        return false;
    }

    // Stop the thread
    public void pause() {
        gameState.stopEverything();
        try {
            mThread.join();
        } catch (InterruptedException e) {
            System.out.println("Pause Error");
        }
    }


    // Start the thread
    public void resume() {
        gameState.startThread();
        mThread = new Thread(this);
        try {
            mThread.start();
        } catch (IllegalThreadStateException e) {
            System.out.println("Resume Error");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        final int actionPreformed = motionEvent.getAction();

        switch(actionPreformed){
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                for (InputObserver o : inputObservers) {
                    o.handleInput(motionEvent, gameState, gameObjects, HUD.getControls());
                    break;
                }
            default:
                break;
        }

        return true;
    }
}
