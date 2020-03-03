package com.example.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

class Snake extends GameObject {

    // How big is the entire grid
    private PointP mMoveRange;

    // For tracking movement Heading
    enum Heading {
        UP, RIGHT, DOWN, LEFT
    }

    private SnakeHead snakeHead;
    private SnakeBody snakeBody;

    // Where is the centre of the screen
    // horizontally in pixels?
    private int halfWayPoint;

    // Start by heading to the right
    private Heading heading = Heading.RIGHT;

    Snake(){}

    Snake(Context context, PointP mr, int ss) {

        // Initialize the segment size and movement
        // range from the passed in parameters
        mMoveRange = mr;

        snakeHead = new SnakeHead(context, ss);
        snakeBody = new SnakeBody(context, ss);

        // The halfway point across the screen in pixels
        // Used to detect which side of screen was pressed
        halfWayPoint = mr.x * ss / 2;

    }

    // Get the snake ready for a new game
    void reset(int w, int h) {

        // Reset the heading
        heading = Heading.RIGHT;

        // Delete the old contents of the ArrayList
        snakeBody.clearBodySegments();

        snakeHead.clearHead(w, h);
        snakeHead.addHead( new PointP(w / 2, h / 2));
    }


    void move() {

        PointP p = snakeHead.headLocation;

        // Move it appropriately
        switch (heading) {
            case UP:
                p.y--;
                break;

            case RIGHT:
                p.x++;
                break;

            case DOWN:
                p.y++;
                break;

            case LEFT:
                p.x--;
                break;
        }

        snakeBody.move(p);

    }

    boolean detectDeath() {
        // Has the snake died?
        boolean dead = false;

        // Hit any of the screen edges
        if (snakeHead.headLocation.x == -1 ||
                snakeHead.headLocation.x > mMoveRange.x ||
                snakeHead.headLocation.y == -1 ||
                snakeHead.headLocation.y > mMoveRange.y) {

            dead = true;
        }

        PointP p = snakeHead.headLocation;
        // Eaten itself?
        boolean eatenSelf = snakeBody.hasEatenBody(p);

        return dead || eatenSelf;
    }

    boolean checkDinner(PointP l) {
        //if (snakeXs[0] == l.x && snakeYs[0] == l.y) {
        if (snakeHead.headLocation.x == l.x &&
                snakeHead.headLocation.y == l.y) {

            // Add a new Point to the list
            // located off-screen.
            // This is OK because on the next call to
            // move it will take the position of
            // the segment in front of it
            snakeBody.addBodySegments(new PointP());
            return true;
        }
        return false;
    }

    void draw(Canvas canvas, Paint paint) {
        // Don't run this code if ArrayList has nothing in it
        if (!(snakeHead.headLocation == null)) {
            snakeHead.draw(canvas, paint,heading, snakeHead.headLocation);
            snakeBody.draw(canvas, paint);
        }
    }

    // Handle changing direction
    void switchHeading(MotionEvent motionEvent) {

        // Is the tap on the right hand side?
        if (motionEvent.getX() >= halfWayPoint) {
            switch (heading) {
                // Rotate right
                case UP:
                    heading = Heading.RIGHT;
                    break;
                case RIGHT:
                    heading = Heading.DOWN;
                    break;
                case DOWN:
                    heading = Heading.LEFT;
                    break;
                case LEFT:
                    heading = Heading.UP;
                    break;

            }
        } else {
            // Rotate left
            switch (heading) {
                case UP:
                    heading = Heading.LEFT;
                    break;
                case LEFT:
                    heading = Heading.DOWN;
                    break;
                case DOWN:
                    heading = Heading.RIGHT;
                    break;
                case RIGHT:
                    heading = Heading.UP;
                    break;
            }
        }
    }

}
