package com.example.game;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class Drag {

    private double positionX, positionY;
    public boolean isSnap;

        public void makeDraggableSnap(ImageView picture, ImageView album) {
        picture.setOnMousePressed(mouseEvent -> {
            positionX = mouseEvent.getX();
            positionY = mouseEvent.getY();
        });

        picture.setOnMouseDragged(mouseEvent -> {
            picture.setLayoutX(mouseEvent.getSceneX() - positionX);
            picture.setLayoutY(mouseEvent.getSceneY() - positionY);
        });

        picture.setOnMouseReleased(mouseEvent -> {
            if (picture.getBoundsInParent().intersects(album.getBoundsInParent())) {
                picture.setLayoutX(album.getLayoutX());
                picture.setLayoutY(album.getLayoutY()-50);
                System.out.println("isSnap = true");
                isSnap = true;

            }
        });
    }

    public void makeDraggable(ImageView picture) {
        picture.setOnMousePressed(mouseEvent -> {
            positionX = mouseEvent.getX();
            positionY = mouseEvent.getY();
        });

        picture.setOnMouseDragged(mouseEvent -> {
            picture.setLayoutX(mouseEvent.getSceneX() - positionX);
            picture.setLayoutY(mouseEvent.getSceneY() - positionY);
        });
    }

}