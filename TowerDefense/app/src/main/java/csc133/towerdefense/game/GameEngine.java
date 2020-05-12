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


    public GameEngine(Context context, Point size) {
        super(context);
        this.context = context;
        this.size = size;

        holder = getHolder();
        game = new Game();

        levelManager = new LevelManager();
        hud = new HUD();
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

    private void update() {
        if (game.lives <= 0) {
            System.out.println("Congratulations, you LOST");
        }
        if (game.getEnemies() == 0 && levelManager.getEnemiesRemaining() == 0) {
            levelManager.next();

            if (levelManager.atFinalStage()) {
                System.out.println("Congratulations, you won");
            } else {
                game.newWave();
                this.towerBeingPlaced = null;
                this.placingTower = false;
                setPaused(true);

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
            Paint pain = new Paint();
            ColorMatrix cm = new ColorMatrix();

            if (towerIsPlaceable(towerBeingPlaced)) {
                cm.setScale(0f, .8f, 0f, .7f);
            } else {
                cm.setScale(.8f, 0f, 0f, .7f);
            }

            ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
            pain.setColorFilter(f);
            towerBeingPlaced.draw(canvas, pain);
        }

        // FPS text
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(35);

        paint.setColor(Color.RED);

        canvas.drawText("FPS: " + FPS, 5, 40, paint);


        // FPS text
        paint.setColor(Color.YELLOW);
        canvas.drawText("Gold: " + (game.gold), 405, 40, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText("Lives: " + (game.lives), 605, 40, paint);
        canvas.drawText("Level: " + (levelManager.currentLevel + 1), 805, 40, paint);
        canvas.drawText("Wave: " + (levelManager.currentWave + 1), 1005, 40, paint);


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
        if (Functions.circleInRect(tower.x, tower.y, tower.width / 2,
                (rect.left + rect.right) / 2, (rect.bottom + rect.top) / 2,
                (rect.right - rect.left), rect.bottom - rect.top)) {
            return false;
        }
        return true;
    }

    boolean placingTower = false;
    Tower towerBeingPlaced;

    void setPaused(boolean bool) {
        gamePaused = bool;
        ToggleHUDObject pauseButton = (ToggleHUDObject) hud.getButtonByTag("pause");
        pauseButton.setBool(bool);
        draw();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (hud == null) { return false; }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (motionEvent.getAction() == motionEvent.ACTION_UP && placingTower) {
            if (towerIsPlaceable(towerBeingPlaced)) {
                game.gold -= towerBeingPlaced.buyPrice;
                towerBeingPlaced.x = x;
                towerBeingPlaced.y = y;
                game.towers.add(towerBeingPlaced);

            }

            towerBeingPlaced = null;
            placingTower = false;

        }

        if (motionEvent.getAction() == motionEvent.ACTION_MOVE && placingTower) {
            towerBeingPlaced.x = x;
            towerBeingPlaced.y = y;
        }

        if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
            String buttonPressed = hud.checkPressed(x, y);

            switch (buttonPressed) {
                case "reset":
                    this.towerBeingPlaced = null;
                    this.placingTower = false;
                    levelManager.reset();
                    game.reset();
                    setPaused(true);

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
                    System.err.println("the fuck is this? " + buttonPressed);
            }
        }

        return true;
    }
}
