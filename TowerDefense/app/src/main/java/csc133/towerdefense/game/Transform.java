package csc133.towerdefense.game;

import android.graphics.PointF;
import android.graphics.RectF;

class Transform {
    private RectF mCollider;
    private PointF mLocation;
    private boolean mFacingRight = true;
    private boolean mHeadingUp = false;
    private boolean mHeadingDown = false;
    private boolean mHeadingLeft = false;
    private boolean mHeadingRight = false;
    private float mSpeed;
    private float mObjectHeight;
    private float mObjectWidth;
    private static PointF mScreenSize;
    Transform(float speed, float objectWidth,
              float objectHeight,
              PointF startingLocation,
              PointF screenSize){
        mCollider = new RectF();
        mSpeed = speed;
        mObjectHeight = objectHeight;
        mObjectWidth = objectWidth;
        mLocation = startingLocation;
        mScreenSize = screenSize;
    }
}