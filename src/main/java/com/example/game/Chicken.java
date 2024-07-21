package com.example.game;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;


public class Chicken {
    private BooleanProperty wPressed = new SimpleBooleanProperty();
    private BooleanProperty aPressed = new SimpleBooleanProperty();
    private BooleanProperty sPressed = new SimpleBooleanProperty();
    private BooleanProperty dPressed = new SimpleBooleanProperty();
    private BooleanBinding keyPressed = wPressed.or(aPressed).or(sPressed).or(dPressed);

    public boolean getaPressed(){
        return aPressed.get();
    }
    public void setwPressed(boolean bool){
         wPressed.set(bool);
    }
    public void setaPressed(boolean bool){
        aPressed.set(bool);
    }
    public void setsPressed(boolean bool){
        sPressed.set(bool);
    }
    public void setdPressed(boolean bool){
        dPressed.set(bool);
    }

    private int movementVariable = 2;
    private ImageView chickenSprite;
    private AnchorPane sceneAnchorPane;
    private String playerName;
    private boolean isPaused = false;


    public Chicken(ImageView chickenImageView, AnchorPane sceneAnchorPane){
        this.chickenSprite = chickenImageView;
        this.sceneAnchorPane = sceneAnchorPane;
    }

    public String getPlayerName(){
        return playerName;
    }

    public void makeMovable(){
        movementSetup();
        System.out.println("makeMovable called " + this);
        
            keyPressed.addListener(((observableValue, aBoolean, t1) -> {
                try{
                    if(!aBoolean){
                        
                        timer.start();
                        // timerStart(this.timer);
                        System.out.println("timer start");
                    } else {
                        timer.stop();
                        // timerStop(this.timer);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }));
            System.out.println("makeMovable finished");
    }

    protected void movementSetup() {
        System.out.println("movementSetup called, isPaused " + isPaused);
        if (isPaused) return;
        sceneAnchorPane.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.W) {
                wPressed.set(true);
            }

            if(e.getCode() == KeyCode.A) {
                aPressed.set(true);
            }

            if(e.getCode() == KeyCode.S) {
                sPressed.set(true);
            }

            if(e.getCode() == KeyCode.D) {
                dPressed.set(true);
            }
        });

        sceneAnchorPane.setOnKeyReleased(e ->{
            if(e.getCode() == KeyCode.W) {
                wPressed.set(false);
            }

            if(e.getCode() == KeyCode.A) {
                aPressed.set(false);
            }

            if(e.getCode() == KeyCode.S) {
                sPressed.set(false);
            }

            if(e.getCode() == KeyCode.D) {
                dPressed.set(false);
            }
        });
    }
    
    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            System.out.println("timer biasa is called");
            if (isPaused) return;

            if(wPressed.get()) {
                chickenSprite.setLayoutY(chickenSprite.getLayoutY() - movementVariable);
            }

            if(sPressed.get()){
                chickenSprite.setLayoutY(chickenSprite.getLayoutY() + movementVariable);
            }

            if(aPressed.get()){
                chickenSprite.setLayoutX(chickenSprite.getLayoutX() - movementVariable);
            }

            if(dPressed.get()){
                chickenSprite.setLayoutX(chickenSprite.getLayoutX() + movementVariable);
            }
        }
    };

    public void pauseMovement() {
        isPaused = true;
        timer.stop();
        // timerStop(this.timer);
        System.out.println("Movement paused.");     
    }

    public void resumeMovement() {
        isPaused = false;
        timer.start();
        // timerStart(this.timer);
        System.out.println("Movement resumed.");
    }

    // protected void timerStart(AnimationTimer timer){
    //     timer.start();
    // }

    // protected void timerStop(AnimationTimer timer){
    //     timer.stop();
    // }
}