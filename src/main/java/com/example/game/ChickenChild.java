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
    private final BooleanProperty wPressed = new SimpleBooleanProperty();
    private final BooleanProperty aPressed = new SimpleBooleanProperty();
    private final BooleanProperty sPressed = new SimpleBooleanProperty();
    private final BooleanProperty dPressed = new SimpleBooleanProperty();
    private final BooleanBinding keyPressed = wPressed.or(aPressed).or(sPressed).or(dPressed);

    private AnchorPane sceneAnchorPane;
    private ImageView chickenSprite;
    private ImageView background;
    private Runnable onObjectCollected;

    private double minX;
    private double minY;

    private ImageView[] objects;
    private String playerName;
    private boolean isPaused = false;

    
    public ChickenChild(ImageView chickenSprite, AnchorPane sceneAnchorPane, ImageView background, Runnable onObjectCollected){
        super(chickenSprite, sceneAnchorPane);
        // this.sceneAnchorPane = new AnchorPane();
        // this.chickenSprite = new ImageView();
        this.background = background;
        this.onObjectCollected = onObjectCollected;
        System.out.println("runnable: " + onObjectCollected);
        //boundaries
        minX = sceneAnchorPane.getLayoutX();
        minY = sceneAnchorPane.getLayoutY() + 200;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        System.out.println("Player name set.");
        System.out.println("Name: " + playerName);
    }

    //use in Stage 3
    public String getPlayerName(){
        return playerName;
    }

    public void makeMovable(){
        super.makeMovable();
    }

    protected void movementSetup() {
        super.movementSetup();
    }

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
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
    }

    // private void checkObjectCollection() {
    //     if (objects == null || onObjectCollected == null) {
    //         return;
    //     }

    //     for (ImageView object : objects) {
    //         if (object.isVisible()) {
    //             Bounds chickenBounds = chickenSprite.getBoundsInParent();
    //             Bounds objectBounds = object.getBoundsInParent();
    //             if (chickenBounds.intersects(objectBounds)) {
    //                 onObjectCollected.run();
    //                 break;
    //             }
    //         }
    //     }
    // }
    private void checkObjectCollection() {
        if (objects == null || onObjectCollected == null) {
            return;
        }

        for (ImageView object : objects) {
            if (object.isVisible()) {
                Bounds chickenBounds = chickenSprite.getBoundsInParent();
                Bounds objectBounds = object.getBoundsInParent();
                if (chickenBounds.intersects(objectBounds)) {
                    onObjectCollected.run();
                    break;
                }
            }
        }
    }

    public void pauseMovement() {
        super.pauseMovement();
    }

    public void resumeMovement() {
        super.resumeMovement();
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