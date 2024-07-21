package com.example.game;

import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class KettleBoiling {
    private Timeline kettleAnim;
    private ImageView kettle;
    private int frameNo = 0;
    private long frameDuration = 80; // 80ms per frame
    private Image[] frames = new Image[8]; //this array stores all frames

    public KettleBoiling(String imagePath, ImageView kettle){
        this.kettle = kettle;

        // Load animation frames for kettle boiling animation
        for (int i = 0; i < frames.length; i++) {
            frames[i] = new Image(imagePath + (i + 1) + ".png");
        }

        kettleAnim = new Timeline(new KeyFrame(Duration.millis(frameDuration), e -> updateFrame()));
        kettleAnim.setCycleCount(Timeline.INDEFINITE);
    }

    private void updateFrame(){
        kettle.setImage(frames[frameNo]);
        frameNo = (frameNo + 1) % frames.length;
    }

    public void startBoiling(){
        kettleAnim.play();
    }

    public void stopBoiling(){
        if (kettleAnim != null) {
            kettleAnim.stop();
            kettle.setImage(frames[0]);
        }
    }  
}
