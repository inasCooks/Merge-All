package com.example.game;

import javafx.scene.Node;

public class Drag {
    private double positionX ,positionY;

    public void makeDraggable(Node node){

        node.setOnMousePressed(mouseEvent -> {
            positionX = mouseEvent.getX();
            positionY = mouseEvent.getY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutX(mouseEvent.getSceneX() - positionX);
            node.setLayoutY(mouseEvent.getSceneY() - positionY);
        });
    }

}
