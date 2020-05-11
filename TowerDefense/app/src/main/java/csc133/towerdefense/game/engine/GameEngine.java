package csc133.towerdefense.game.engine;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import csc133.towerdefense.game.Background;
import csc133.towerdefense.game.BitmapStore;
import csc133.towerdefense.game.GameObjectSystem;
import csc133.towerdefense.game.ui.HUD;
import csc133.towerdefense.game.engine.interfaces.IEngineController;
import csc133.towerdefense.game.engine.interfaces.IGameEngineBroadcaster;
import csc133.towerdefense.game.engine.interfaces.IInputObserver;
import csc133.towerdefense.game.ui.UIController;

public class GameEngine extends SurfaceView implements Runnable, IGameEngineBroadcaster, IEngineController {
    public static final int MAX_FPS = 60;
    public static final int MILLIS_IN_SECOND = 1000;
    public static final int FRAME_PERIOD = MILLIS_IN_SECOND / MAX_FPS;

    public static long framesRan = 0;

    boolean playing;
    boolean paused;
    long FPS;
    Thread gameThread;
    SurfaceHolder holder;

    private ArrayList<IInputObserver>
            inputObservers = new ArrayList();

    private Background background;

    private GameState mGameState;
    private SoundEngine mSoundEngine;
    UIController mUIController;
    PhysicsEngine mPhysicsEngine;
    HUD mHUD;
    Renderer mRenderer;
    LevelManager mLevelManager;

    public GameEngine(Context context, Point size) {
        super(context);
        // Prepare the bitmap store and sound engine
        //BitmapStore bs = BitmapStore.getInstance(context);
        //SoundEngine se = SoundEngine.getInstance(context);

        // Initialize all the significant classes
        // that make the engine work
        //mHUD = new HUD(context, size);
        mGameState = new GameState(this, context);
        //mUIController = new UIController(this, size);
        mPhysicsEngine = new PhysicsEngine();
        mRenderer = new Renderer(this, size);
        mLevelManager = new LevelManager(context,this, mRenderer.getPixelsPerMetre());
    }

    public void startNewLevel() {
        // Clear the bitmap store
        BitmapStore.clearStore();
        // Clear all the observers and add the UI observer back
        // When we call buildGameObjects the
        // player's observer will be added too
        inputObservers.clear();
        mUIController.addObserver(this);
        mLevelManager.setCurrentLevel(mGameState.getCurrentLevel());
        mLevelManager.buildGameObjects(mGameState);
    }

    // For the game engine broadcaster interface
    public void addObserver(IInputObserver o) {
        inputObservers.add(o);
    }

    public void pause() {
        playing = false;
        paused = true;
        try {
            gameThread.join();
        } catch (Exception e) {
            Log.e("Error:", "joining thread");
        }
    }

    public void resume() {
        playing = true;
        paused = false;
        gameThread = new Thread(this);
        gameThread.start();
    }

    long sleepTime = 0;

    public void run() {
        while (mGameState.getThreadRunning()) {
            long frameStartTime = System.currentTimeMillis();
            // 1. update objects
            if (!mGameState.getPaused()) {
                if (mPhysicsEngine.update(FPS, gameObjectSystem)) {
                    deSpawnReSpawn();
                }
                ++framesRan;
            }

            // 2. draw animation
            draw();
            mRenderer.draw(mGameState, mHUD);

            long timeThisFrame = System.currentTimeMillis() - frameStartTime;
            if (timeThisFrame >= 1) {
                FPS = MILLIS_IN_SECOND / timeThisFrame;
            }

            // wait for next frame
            sleepTime = (FRAME_PERIOD - timeThisFrame);

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        // Handle the player's input here
        // But in a new way
        for (IInputObserver o : inputObservers) {
            o.handleInput(motionEvent, mGameState,
                    mHUD.getControls());
        }
        return true;
    }


    public void stopThread() {
        // New code here soon
        mGameState.stopEverything();
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Exception","stopThread()"
                    + e.getMessage());
        }
    }

    public void startThread() {
        // New code here soon
        mGameState.startThread();

        gameThread = new Thread(this);
        gameThread.start();
    }

    private void update() {

        //game.update();
    }

    private void draw() {
        mRenderer.draw(mGameState, mHUD, gameObjectSystem);
        /*
        if (!holder.getSurface().isValid()) {
            return;
        }

        Canvas canvas = holder.lockCanvas();

        // grey background
        canvas.drawColor(Color.argb(255, 50, 54, 57));

        Paint paint = new Paint();

        // space background
        background.draw(canvas, paint);

        // FPS text
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(35);
        canvas.drawText("FPS: " + FPS, 5, 40, paint);

        game.draw(canvas);

        holder.unlockCanvasAndPost(canvas);

         */
    }

    public void deSpawnReSpawn() {
        // Eventually this will despawn
        // and then respawn all the game objects
        enemies.clear();
        bullets.clear();
        towers.clear();
        enemyBulletCollisions.clear();
        enemyTowerCollisions.clear();
    }
}
