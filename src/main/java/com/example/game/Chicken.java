package com.example.game;

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
    private ImageView sprite;
    private AnchorPane sceneAnchorPane;

    public Chicken(ImageView chickenImageView, AnchorPane sceneAnchorPane){
        this.sprite = chickenImageView;
        this.sceneAnchorPane = sceneAnchorPane;
    }

    public void makeMovable(){
        movementSetup();
        System.out.println("makeMovable called " + this);
        
            keyPressed.addListener(((observableValue, aBoolean, t1) -> {
                try{
                    if(!aBoolean){
                        
                        timer.start();
                        System.out.println("timer.start");
                    } else {
                        timer.stop();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }));
            System.out.println("makeMovable finished");
    }

    private void movementSetup() {
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
            if(wPressed.get()) {
                sprite.setLayoutY(sprite.getLayoutY() - movementVariable);
            }

            if(sPressed.get()){
                sprite.setLayoutY(sprite.getLayoutY() + movementVariable);
            }

            if(aPressed.get()){
                sprite.setLayoutX(sprite.getLayoutX() - movementVariable);
            }

            if(dPressed.get()){
                sprite.setLayoutX(sprite.getLayoutX() + movementVariable);
            }
        }
    };
}