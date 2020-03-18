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
    private int mScreenHeight;
    private int mScreenWidth;

    private Rect baseHUD;

    static int PAUSE = 0;
    static int TOWER = 0;

    HUD(Point size){
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.x / 50;

        prepareControls();
    }

    private void prepareControls(){
        int buttonWidth = mScreenWidth / 14;
        int buttonHeight = mScreenHeight / 12;
        int buttonPadding = mScreenWidth / 90;

        baseHUD = new Rect(
                buttonPadding,
                0,
                mScreenWidth - buttonPadding,
                buttonHeight);

        Rect pause = new Rect(buttonPadding, 0, buttonWidth, buttonHeight);
        Rect tower = new Rect(buttonPadding, 0, buttonWidth, buttonHeight);

        controls = new ArrayList<>();
        controls.add(PAUSE, pause);
        controls.add(TOWER, tower);
    }

    void draw(Canvas c, Paint p, GameState gs){

        // Draw the HUD
        p.setColor(Color.argb(255,255,255,255));
        p.setTextSize(mTextFormatting);

        if(gs.getGameOver()){
            p.setTextSize(mTextFormatting * 5);
            c.drawText("PRESS PLAY", mScreenWidth /4, mScreenHeight /2 ,p);
        }

        if(gs.getPaused() && !gs.getGameOver()){
            p.setTextSize(mTextFormatting * 5);
            c.drawText("PAUSED", mScreenWidth /3, mScreenHeight /2 ,p);
        }
        drawHUD(c, p);
    }

    private void drawHUD(Canvas c, Paint p){
        p.setColor(Color.argb(100,255,255,255));
        c.drawRect(baseHUD, p);

        p.setColor(Color.argb(100,64,64,64));
        for(Rect r : controls){
            c.drawRect(r.left, r.top, r.right, r.bottom, p);
        }

        // Set the colors back
        p.setColor(Color.argb(255,255,255,255));
    }

    static ArrayList<Rect> getControls(){
        return controls;
    }
}
