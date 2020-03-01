package com.example.snake;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

public abstract class AppleBuilder {

    // The location of the goodApple on the grid
    // Not in pixels
    private PointP location = new PointP();

    // The range of values we can choose from
    // to spawn an goodApple
    private PointP mSpawnRange;

    AppleBuilder(PointP sr){
        // Make a note of the passed in spawn range
        mSpawnRange = sr;

        // Hide the goodApple off-screen until the game starts
        location.x = -10;
    }


    // This is called every time an apple is eaten
    public void spawn() {
        // Choose two random values and place the apple
        Random random = new Random();
        location.x = random.nextInt(mSpawnRange.x) + 1;
        location.y = random.nextInt(mSpawnRange.y - 1) + 1;
    }

    // This is called every time a bad apple is eaten
    public void despawn() {
        location.x = -10;
        location.y = -10;
    }

    // Let SnakeGame know where the apple is
    // SnakeGame can share this with the snake
    public PointP getLocation() {
        return location;
    }

    // Draw the apple
    abstract void draw(Canvas canvas, Paint paint);

}
