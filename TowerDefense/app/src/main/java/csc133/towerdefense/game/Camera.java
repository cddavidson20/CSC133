package csc133.towerdefense.game;

import android.graphics.PointF;
import android.graphics.Rect;

public class Camera {

    private PointF mCurrentCameraWorldCentre;
    private Rect mConvertedRect;
    private int mPixelsPerMetre;
    private int mScreenCentreX;
    private int mScreenCentreY;

    public Camera(int screenXResolution, int screenYResolution){
        // Locate the centre of the screen
        mScreenCentreX = screenXResolution / 2;
        mScreenCentreY = screenYResolution / 2;

        // How many metres of world space does
        // the screen width show
        // Change this value to zoom in and out
        final int pixelsPerMetreToResolutionRatio = 48;
        mPixelsPerMetre = screenXResolution /
                pixelsPerMetreToResolutionRatio;

        mConvertedRect = new Rect();
        mCurrentCameraWorldCentre = new PointF();
    }

    public int getPixelsPerMetreY(){
        return mPixelsPerMetre;
    }

    public int getyCentre(){
        return mScreenCentreY;
    }

    public float getCameraWorldCentreY(){
        return mCurrentCameraWorldCentre.y;
    }

    // Set the camera to the player. Called each frame
    public void setWorldCentre(PointF worldCentre){
        mCurrentCameraWorldCentre.x  = worldCentre.x;
        mCurrentCameraWorldCentre.y  = worldCentre.y;
    }

    public int getPixelsPerMetre(){
        return mPixelsPerMetre;
    }

    // Return a Rect of the screen coordinates relative to a world location
    Rect worldToScreen(float objectX,
                       float objectY,
                       float objectWidth,
                       float objectHeight){

        int left = (int) (mScreenCentreX -
                ((mCurrentCameraWorldCentre.x - objectX)
                        * mPixelsPerMetre));

        int top =  (int) (mScreenCentreY -
                ((mCurrentCameraWorldCentre.y - objectY)
                        * mPixelsPerMetre));

        int right = (int) (left + (objectWidth * mPixelsPerMetre));
        int bottom = (int) (top + (objectHeight * mPixelsPerMetre));
        mConvertedRect.set(left, top, right, bottom);
        return mConvertedRect;
    }

}
