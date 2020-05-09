package csc133.towerdefense.game.gameobject;

import android.graphics.Canvas;

import csc133.towerdefense.game.helpers.Functions;

public abstract class GameObject {
    public float x;
    public float y;
    public float width;
    public float height;
    public boolean toDestroy;

    public GameObject(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.toDestroy = false;
    }

    public GameObject(float x, float y, float size) {
        this(x, y, size, size);
    }

    abstract public void draw(Canvas canvas);

    abstract public void update();
}
