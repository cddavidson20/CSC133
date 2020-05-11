package csc133.towerdefense.game.engine.interfaces;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import csc133.towerdefense.game.Camera;
import csc133.towerdefense.game.engine.Transform;

public interface IGraphicsComponent {
    // Added int mPixelsPerMetre to
    // scale the bitmap to the camera
    void initialize(Context c, GameObjectSpec spec,
                    PointF objectSize, int pixelsPerMetre);

    // Updated from the last project
    // to take a reference to a Camera
    void draw(Canvas canvas, Paint paint,
              Transform t, Camera cam);
}