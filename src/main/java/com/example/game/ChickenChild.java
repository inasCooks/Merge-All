package com.example.game;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.geometry.Bounds;

public class ChickenChild extends Chicken {
    private BooleanProperty wPressed = new SimpleBooleanProperty();
    private BooleanProperty aPressed = new SimpleBooleanProperty();
    private BooleanProperty sPressed = new SimpleBooleanProperty();
    private BooleanProperty dPressed = new SimpleBooleanProperty();
    private BooleanBinding keyPressed = wPressed.or(aPressed).or(sPressed).or(dPressed);

    private AnchorPane sceneAnchorPane;
    private ImageView chickenSprite;
    private ImageView background;
    private Runnable onObjectCollected;

    private double minX;
    private double minY;

    private ImageView[] objects;
    private String playerName;
    private boolean isPaused = false;

    public void setwPressed(boolean bool){
        super.setwPressed(bool);
    }
    public void setaPressed(boolean bool){
        super.setaPressed(bool);
    }
    public void setsPressed(boolean bool){
        super.setsPressed(bool);
    }
    public void setdPressed(boolean bool){
        super.setdPressed(bool);
    }

    
    public ChickenChild(ImageView chickenSprite, AnchorPane sceneAnchorPane, ImageView background, Runnable onObjectCollected){
        super(chickenSprite, sceneAnchorPane);
        this.chickenSprite = chickenSprite;
        this.sceneAnchorPane = sceneAnchorPane;
        this.background = background;
        this.onObjectCollected = onObjectCollected;
        System.out.println("runnable: " + onObjectCollected);
        //boundaries
        minX = sceneAnchorPane.getLayoutX();
        minY = sceneAnchorPane.getLayoutY() + 200;
        System.out.println("Chicken Child has been born");
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        System.out.println("Player name set.");
        System.out.println("Name: " + playerName);
    }

    //use in Stage 3
    public String getPlayerName(){
        return super.getPlayerName();
    }

    @Override
    public void makeMovable(){
        super.movementSetup();
        System.out.println("makeMovable called " + this);
        
            keyPressed.addListener(((observableValue, aBoolean, t1) -> {
                try{
                    if(!aBoolean){
                        timerChild.start();
                        System.out.println("timer start");
                    } else {
                        timerChild.stop();
                        System.out.println("timer Stop");
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }));
            System.out.println("makeMovable finished");
    }

    protected void movementSetup() {
        super.movementSetup();
    }

    AnimationTimer timerChild = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            System.out.println("Timer child is called");
            if (isPaused) return;
            int movementVariable = 3;

            double newX = chickenSprite.getLayoutX();
            double newY = chickenSprite.getLayoutY();

            if (wPressed.get()) {
                newY -= movementVariable;
            }

            if (sPressed.get()) {
                newY += movementVariable;
            }

            if (aPressed.get()) {
                newX -= movementVariable;
            }

            if (dPressed.get()) {
                newX += movementVariable;
            }

            if (newX >= minX && newX + chickenSprite.getFitWidth() <= background.getFitWidth()) {
                chickenSprite.setLayoutX(newX);
            }
            if (newY >= minY && newY + chickenSprite.getFitHeight() <= background.getFitHeight() - 200) {
                chickenSprite.setLayoutY(newY);
            }

            checkObjectCollection();
        }
    };

    public void setObjects(ImageView[] objects) {
        this.objects = objects;
        System.out.println("objects " + objects);
    }
    private void checkObjectCollection() {
        System.out.println("checking object collection");
        if (objects == null || onObjectCollected == null) {
            System.out.println("something is null: " + objects + " " + onObjectCollected);
            return;
        }

        for (ImageView object : objects) {
            if (object.isVisible()) {
                System.out.println("visible object detected "+object);
                Bounds chickenBounds = chickenSprite.getBoundsInParent();
                Bounds objectBounds = object.getBoundsInParent();
                if (chickenBounds.intersects(objectBounds)) {
                    onObjectCollected.run();
                    System.out.println("should be running onOBjectCollected already");
                    break;
                }
            }
        }
    }

    // protected void timerStart() {
    //     super.timerStart(timerChild);
    // }

    // protected void timerStop() {
    //     super.timerStop(timerChild);
    // }

    @Override
    public void pauseMovement() {
        isPaused = true;
        timerChild.stop();
    }

    @Override
    public void resumeMovement() {
        isPaused = false;
        timerChild.start();
    }
    public double[] getSpritePosition() {
        return new double[]{chickenSprite.getLayoutX(), chickenSprite.getLayoutY()};
    }

    public void setSpritePosition(double[] position) {
        if (position != null && position.length == 2) {
            chickenSprite.setLayoutX(position[0]);
            chickenSprite.setLayoutY(position[1]);
        }
    }
}