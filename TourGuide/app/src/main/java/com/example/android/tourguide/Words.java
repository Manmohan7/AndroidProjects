package com.example.android.tourguide;

/**
 * Created by manmohan on 18/5/17.
 */

public class Words {

    private String mPlace;
    private int mImageId;

    public Words(String place, int imageId)
    {
        this.mImageId = imageId;
        this.mPlace = place;
    }

    public String getPlace()
    {
        return this.mPlace;
    }

    public int getImageId()
    {
        return this.mImageId;
    }
}
