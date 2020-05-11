package csc133.towerdefense.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import csc133.towerdefense.game.interfaces.IGameEngineBroadcaster;
import csc133.towerdefense.game.interfaces.IGameStarter;
import csc133.towerdefense.game.interfaces.IInputObserver;

public class GameEngine extends SurfaceView implements Runnable, IGameStarter,
        IGameEngineBroadcaster {
    public static final int MAX_FPS = 60;
    public static final int MILLIS_IN_SECOND = 1000;
    public static final int FRAME_PERIOD = MILLIS_IN_SECOND / MAX_FPS;

    public static long framesRan = 0;

    boolean playing;
    boolean paused;
    long FPS;
    Thread gameThread;
    SurfaceHolder holder;

    private ArrayList<IInputObserver> inputObservers = new ArrayList();

    private Background background;

    private GameState mGameState;
    private SoundEngine mSoundEngine;
    UIController mUIController;
    PhysicsEngine mPhysicsEngine;
    HUD mHUD;
    Renderer mRenderer;
    GameObjectSystem gameObjectSystem;
    LevelManager mLevelManager;

    public GameEngine(Context context, Point size) {
        super(context);

        mHUD = new HUD(size);
        mSoundEngine = new SoundEngine(context);
        mGameState = new GameState(this, context);
        mUIController = new UIController(this);
        mPhysicsEngine = new PhysicsEngine();
        mRenderer = new Renderer(this);

        mLevelManager = new LevelManager(context, this, mRenderer.getPixelPerMetre());

        // Even just 10 particles look good
        // But why have less when you can have more
        gameObjectSystem.init();

        holder = getHolder();

        background = new Background();
        background.initialize(context, "background", size);

        gameThread = new Thread(this);
        gameThread.start();
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
