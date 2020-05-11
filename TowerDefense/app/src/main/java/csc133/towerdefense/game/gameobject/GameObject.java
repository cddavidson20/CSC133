package csc133.towerdefense.game.gameobject;

import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;

import csc133.towerdefense.game.engine.Transform;
import csc133.towerdefense.game.engine.interfaces.IGraphicsComponent;
import csc133.towerdefense.game.engine.interfaces.IUpdateComponent;

public class GameObject {
    public float x;
    public float y;
    public float width;
    public float height;
    public boolean toDestroy;
    public String tag;
    private Transform transform;
    IGraphicsComponent graphicsComponent;
    IUpdateComponent updateComponent;

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

    public GameObject() {
    }

    public void draw(Canvas canvas, Paint paint, Camera camera) {
        graphicsComponent.draw(canvas, paint, transform, camera);
    }

    public void update(long fps, Transform focusTransform) {
        updateComponent.update(fps, transform, focusTransform);
    }

    void setTransform(Transform t) {
        transform = t;
    }

    public Transform getTransform() {
        return transform;
    }
}
