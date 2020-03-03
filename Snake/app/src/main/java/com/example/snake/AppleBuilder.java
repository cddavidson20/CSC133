package com.example.snake;

import android.content.Context;

class AppleBuilder {

    // An image to represent the goodApple
    private Context context;
    private PointP location;
    private PointP mSpawnRange;
    private int mSize;
    private int appleID;

    AppleBuilder(Context context, PointP sr, int s, PointP location, int appleID) {
        this.context = context; //required
        this.mSpawnRange = sr;
        this.mSize = s;
        this.location = location;
        this.appleID = appleID;
    }

    //not needed right now but may be needed in future applications
    void setAppleId(int appleID) {
        this.appleID = appleID;
    }

    //not needed right now but may be needed in future applications
    void setLocation(PointP location) {
        this.location = location;
    }

    //not needed right now but may be needed in future applications
    void setSpawnRange(PointP mSpawnRange) {
        this.mSpawnRange = mSpawnRange;
    }

    //not needed right now but may be needed in future applications
    void setSize(int mSize) {
        this.mSize = mSize;
    }

    Apple build() {
        return new Apple(context, mSpawnRange, mSize, location, appleID);
    }

}
