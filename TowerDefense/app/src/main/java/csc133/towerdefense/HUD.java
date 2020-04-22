package csc133.towerdefense;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

class HUD {
    private static ArrayList<Rect> controls;
    private int mTextFormatting;
    private int mHUDText;
    private int mScreenHeight;
    private int mScreenWidth;

    private Rect baseHUD;

    static int PAUSE = 0;
    static int RESET = 1;
    static int TOWER = 2;

    HUD(Point size) {
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.x / 50;
        mHUDText = size.x / 70;

        prepareControls();
    }

    private void prepareControls() {
        int buttonWidth = mScreenWidth / 14;
        int buttonHeight = mScreenHeight / 12;
        int buttonPadding = mScreenWidth / 90;

        baseHUD = new Rect(
                buttonPadding,
                0,
                mScreenWidth - buttonPadding,
                buttonHeight);

        Rect pause = new Rect(buttonPadding, 0, buttonWidth, buttonHeight);
        Rect reset = new Rect(pause.right + buttonPadding, 0, pause.right + buttonWidth, buttonHeight);
        Rect tower = new Rect(reset.right + buttonPadding, 0, reset.right + buttonWidth, buttonHeight);

        controls = new ArrayList<>();
        controls.add(PAUSE, pause);
        controls.add(RESET, reset);
        controls.add(TOWER, tower);
    }

    void draw(Canvas c, Paint p, GameState gs) {

        // Draw the HUD
        p.setColor(Color.argb(255,255,255,255));
        p.setTextSize(mTextFormatting);
        c.drawText("Wave: " + gs.getWave(), mScreenWidth - (mScreenWidth/10), mScreenHeight/30, p);
        c.drawText("Gold: " + gs.getGold(), mScreenWidth - (mScreenWidth/10), mScreenHeight/14, p);
        c.drawText("Lives: " + gs.getLives(), mScreenWidth - (mScreenWidth/5), mScreenHeight/30, p);

        if(gs.getGameOver()) {
            p.setTextSize(mTextFormatting * 5);
            c.drawText("PRESS PLAY", mScreenWidth /4, mScreenHeight /2 ,p);
        }

        if(gs.getPaused() && !gs.getGameOver()) {
            p.setTextSize(mTextFormatting * 5);
            c.drawText("PAUSED", mScreenWidth /3, mScreenHeight /2 ,p);
        }

        drawHUD(c, p);
    }

    private void drawHUD(Canvas c, Paint p) {
        p.setColor(Color.argb(100,255,255,255));
        c.drawRect(baseHUD, p);

        p.setColor(Color.argb(100,64,64,64));
        for(Rect r : controls) {
            c.drawRect(r.left, r.top, r.right, r.bottom, p);
        }

        p.setColor(Color.argb(225,0,0,0));
        p.setTextSize(mHUDText);
        c.drawText("PAUSE", controls.get(PAUSE).right / 3.6f,
                controls.get(PAUSE).bottom / 1.6f, p);
        c.drawText("RESET", controls.get(RESET).right - (controls.get(RESET).left / 1.6f),
                (controls.get(RESET).top + controls.get(RESET).bottom / 1.6f) , p);
        c.drawText("TOWER", controls.get(TOWER).right - (controls.get(TOWER).left / 3.0f),
                   (controls.get(TOWER).top + controls.get(TOWER).bottom / 1.6f) , p);


        // Set the colors back
        p.setColor(Color.argb(255,255,255,255));
    }

    static ArrayList<Rect> getControls() {
        return controls;
    }
}
