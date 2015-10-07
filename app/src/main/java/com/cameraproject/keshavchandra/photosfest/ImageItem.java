package com.cameraproject.keshavchandra.photosfest;

import android.graphics.Bitmap;

/**
 * Created by Keshav Chandra on 01-10-2015.
 */
public class ImageItem {
    public String image_name;
    public Bitmap bitmap;
    public int like_count;
    public int dislike_count;
    public ImageItem(String image_name,int like_count,int dislike_count,Bitmap bitmap){
        this.image_name=image_name;
        this.like_count=like_count;
        this.dislike_count=dislike_count;
        this.bitmap=bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
