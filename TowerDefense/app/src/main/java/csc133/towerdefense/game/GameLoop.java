package csc133.towerdefense.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * The name says it all, there's a hole in the wall.
 */
public class GameLoop extends SurfaceView implements Runnable {
    public static final int MAX_FPS = 60;
    public static final int MILLIS_IN_SECOND = 1000;
    public static final int FRAME_PERIOD = MILLIS_IN_SECOND / MAX_FPS;

    public static long framesRan = 0;

    boolean playing;
    boolean paused;
    long FPS;
    int blockSize;
    Thread gameThread;
    Game game;
    SurfaceHolder holder;

    private Background background;

    public GameLoop(Context context, int blockSize, Point size) {
        super(context);

        this.blockSize = blockSize;
        holder = getHolder();
        game = new Game();

        //idk why background takes in a point size
        background = new Background();
        background.initialize(context, "background", size);

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
            if (!paused) {
                update();
                ++framesRan;
            }

            // 2. draw animation
            draw();

            // 3. touch me mommy
            long timeThisFrame = System.currentTimeMillis() - frameStartTime;
            if (timeThisFrame > 0) {
                FPS = MILLIS_IN_SECOND / timeThisFrame;
            }

            // wait for next frame
            sleepTime = (FRAME_PERIOD - timeThisFrame);

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void update() {
        game.update();
    }

    private void draw() {
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
    }
}
