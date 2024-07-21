package com.example.game;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MyButton extends Button{
    
    private String fxId;
    private final String defaultImagePath;
    private final String hoverImagePath;
    private ImageView button;

    public MyButton( String defaultImagePath, String hoverImagePath, ImageView button) {
        this.button = button;
        this.defaultImagePath = defaultImagePath;
        this.hoverImagePath = hoverImagePath;
        button.setImage(new Image(getClass().getResourceAsStream(defaultImagePath))); // Set default image on creation
    }

    public void hoverButton(){
        button.setImage(new Image(getClass().getResourceAsStream(hoverImagePath)));
        setOnMouseEntered(event -> button.setImage(new Image(getClass().getResourceAsStream(hoverImagePath))));
    }
    
    public void exitButton(){
        button.setImage(new Image(getClass().getResourceAsStream(defaultImagePath)));
        setOnMouseExited(event -> button.setImage(new Image(getClass().getResourceAsStream(defaultImagePath))));
    }

    public ImageView getImageView() {
        return button;
    }

    public void setImageView(ImageView buttonImageView) {
        this.button=buttonImageView;
    }

    public void setFxId(String fxId, MyButton button) {
        button.fxId = fxId;
        setUserData(fxId); // Store the fxId in user data
      }
}
