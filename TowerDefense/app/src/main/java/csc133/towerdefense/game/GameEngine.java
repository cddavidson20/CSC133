package csc133.towerdefense.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import csc133.towerdefense.game.HUD.HUD;
import csc133.towerdefense.game.HUD.ToggleHUDObject;
import csc133.towerdefense.game.gameobject.tower.BouncingBettyTowerBuilder;
import csc133.towerdefense.game.gameobject.tower.MachineGunTowerBuilder;
import csc133.towerdefense.game.gameobject.tower.SniperTowerBuilder;
import csc133.towerdefense.game.gameobject.tower.Tower;
import csc133.towerdefense.game.gameobject.tower.TowerCreator;
import csc133.towerdefense.game.helpers.Functions;
import csc133.towerdefense.game.level.LevelManager;

public class GameEngine extends SurfaceView implements Runnable {
    public static final int MAX_FPS = 60;
    public static final int MILLIS_IN_SECOND = 1000;
    public static final int FRAME_PERIOD = MILLIS_IN_SECOND / MAX_FPS;

    public static long framesRan = 0;

    boolean playing;
    boolean paused;
    long FPS;
    Thread gameThread;
    Game game;
    SurfaceHolder holder;
    LevelManager levelManager;
    HUD hud;

    boolean gamePaused;

    public static Context context;
    public static Point size;
    public static SoundEngine soundEngine;

    public GameEngine(Context context, Point size) {
        super(context);
        GameEngine.context = context;
        GameEngine.size = size;

        holder = getHolder();
        game = new Game();

        levelManager = new LevelManager();
        hud = new HUD();
        soundEngine = SoundEngine.getSoundEngine();

        setPaused(true);

        gameThread = new Thread(this);
        gameThread.start();
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
        while (playing) {
            long frameStartTime = System.currentTimeMillis();
            // 1. update objects
            if (!paused && !gamePaused) {
                update();
                ++framesRan;
            }

            // 2. draw animation
            draw();

            long timeThisFrame = System.currentTimeMillis() - frameStartTime;
            if (timeThisFrame > 0) {
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

    boolean youWon = false;
    public static boolean youLost = false;

    private void update() {
        if (game.lives <= 0) {
            System.out.println("Congratulations, you LOST");
            // play music
            if (!youLost) soundEngine.playLose();
            youLost = true;
        }
        if (game.getEnemies() == 0 && levelManager.getEnemiesRemaining() == 0) {
            if (!youLost) {
                levelManager.next();

                if (levelManager.atFinalStage()) {
                    System.out.println("Congratulations, you won");
                    if (!youWon) soundEngine.playWin();
                    youWon = true;
                } else {
                    if (levelManager.currentWave == 0) {
                        game.towers.clear();
                        game.gold = 100;
                    }
                    game.newWave();
                    setPaused(true);
                }
                this.towerBeingPlaced = null;
                this.placingTower = false;
            }
        }
        levelManager.update(game.enemies);
        game.update();
    }

    private void draw() {
        if (!holder.getSurface().isValid()) {
            return;
        }

        Canvas canvas = holder.lockCanvas();

        Paint paint = new Paint();

        // path and background draw
        levelManager.draw(canvas);

        // game draw
        game.draw(canvas);

        // hud
        hud.draw(canvas);

        if (towerBeingPlaced != null) {
            Paint matrixPaint = new Paint();
            ColorMatrix cm = new ColorMatrix();

            if (towerIsPlaceable(towerBeingPlaced)) {
                cm.setScale(0f, .8f, 0f, .7f);
            } else {
                cm.setScale(.8f, 0f, 0f, .7f);
            }

            ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
            matrixPaint.setColorFilter(f);
            towerBeingPlaced.draw(canvas, matrixPaint);
        }

        // FPS text
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(35);
        paint.setColor(Color.RED);
        canvas.drawText("FPS: " + FPS, 5, 40, paint);

        // HUD text
        paint.setColor(Color.YELLOW);
        canvas.drawText("Gold: " + (game.gold), 405, 40, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText("Lives: " + (game.lives), 605, 40, paint);
        canvas.drawText("Level: " + (levelManager.currentLevel + 1), 805, 40, paint);
        canvas.drawText("Wave: " + (levelManager.currentWave + 1), 1005, 40, paint);

        if (youLost) {
            paint.setTextSize(100);
            paint.setColor(Color.RED);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("YOU LOST", GameEngine.size.x / 2, GameEngine.size.y / 2, paint);
        } else if (youWon) {
            paint.setTextSize(100);
            paint.setColor(Color.BLUE);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("YOU WON", GameEngine.size.x / 2, GameEngine.size.y / 2, paint);

        }

        holder.unlockCanvasAndPost(canvas);
    }

    boolean towerIsPlaceable(Tower tower) {
        // check if tower is on top of other towers
        for (Tower other : game.towers) {
            if (Functions.circleInCircle(tower.x, tower.y, tower.width / 2, other.x, other.y, other.width / 2)) {
                return false;
            }
        }

        // check if tower is on top of path
        for (RectF rect : levelManager.getCurrPath().rects) {
            if (Functions.circleInRect(tower.x, tower.y, tower.width / 2,
                    (rect.left + rect.right) / 2, (rect.bottom + rect.top) / 2,
                    (rect.right - rect.left), rect.bottom - rect.top)) {
                return false;
            }
        }

        // check if tower is on hud
        RectF rect = hud.hitbox;
        return !Functions.circleInRect(tower.x, tower.y, tower.width / 2,
                (rect.left + rect.right) / 2, (rect.bottom + rect.top) / 2,
                (rect.right - rect.left), rect.bottom - rect.top);
    }

    boolean placingTower = false;
    Tower towerBeingPlaced = null;

    void setPaused(boolean bool) {
        gamePaused = bool;
        ToggleHUDObject pauseButton = (ToggleHUDObject) hud.getButtonByTag("pause");
        pauseButton.setBool(bool);
        draw();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (hud == null) {
            return false;
        }
        // x and y of a tap on screen
        float x = motionEvent.getX();
        float y = motionEvent.getY();

        // when finger up while holding a tower, place tower.
        if (motionEvent.getAction() == MotionEvent.ACTION_UP && placingTower) {
            if (towerIsPlaceable(towerBeingPlaced) && game.gold >= towerBeingPlaced.buyPrice) {
                game.gold -= towerBeingPlaced.buyPrice;
                towerBeingPlaced.x = x;
                towerBeingPlaced.y = y;
                game.towers.add(towerBeingPlaced);
                soundEngine.playTowerPlaced();
            } else {
                soundEngine.playNotPlaceable();
            }
            towerBeingPlaced = null;
            placingTower = false;
        }

        // hold tower in hand while deciding to place tower
        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE && placingTower) {
            towerBeingPlaced.x = x;
            towerBeingPlaced.y = y;
        }

        // on screen tap, decide what happens
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            String buttonPressed = hud.checkPressed(x, y);
            // click noise if screen is clicked.
            soundEngine.playClick();

            switch (buttonPressed) {
                case "reset":
                    this.towerBeingPlaced = null;
                    this.placingTower = false;
                    levelManager.reset();
                    game.reset();
                    setPaused(true);
                    youWon = false;
                    youLost = false;

                    draw();
                    break;

                case "pause":
                    setPaused(!gamePaused);

                    break;

                case "machineguntower":
                    placingTower = true;
                    TowerCreator tc = new TowerCreator(new MachineGunTowerBuilder());
                    towerBeingPlaced = tc.createTower(x, y);
                    break;

                case "bouncingbettytower":
                    placingTower = true;
                    TowerCreator tc2 = new TowerCreator(new BouncingBettyTowerBuilder());
                    towerBeingPlaced = tc2.createTower(x, y);
                    break;

                case "snipertower":
                    placingTower = true;
                    TowerCreator tc3 = new TowerCreator(new SniperTowerBuilder());
                    towerBeingPlaced = tc3.createTower(x, y);
                    break;

                default:
                    System.err.println("Not button on HUD" + buttonPressed);
            }
        }

        return true;
    }
}
