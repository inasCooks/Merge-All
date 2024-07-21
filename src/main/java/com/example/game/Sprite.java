package com.example.game;

import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite {
    
    private ImageView sprite;
    private String defaultImagePath;
    private Image nonActive;
    private Image active;

    public Sprite(String defaultImagePath, ImageView sprite){
        this.sprite = sprite;
        this.defaultImagePath = defaultImagePath;
        InputStream nonActiveStream = getClass().getResourceAsStream(defaultImagePath + 1 + ".png");
        InputStream activeStream = getClass().getResourceAsStream(defaultImagePath + 2 + ".png");

        if (nonActiveStream != null && activeStream != null) {
            nonActive = new Image(nonActiveStream);
            active = new Image(activeStream);
        } else {
            System.err.println("Image not found");
        }
    }

    public void showWarning(){
        sprite.setImage(active);
    }

    public void hideWarning(){
        sprite.setImage(nonActive);
    }
}
