package com.example.igor.footballnews;

import android.graphics.Bitmap;

/**
 * Created by Igor on 25.01.2015.
 */
public class Team {
    String name;
    String bitmapImg;
    String games;
    String score;

    Team(String name, String bitmapImg,String games,String score){
        this.bitmapImg = bitmapImg;
        this.name = name;
        this.games = games;
        this.score = score;
    }

}
