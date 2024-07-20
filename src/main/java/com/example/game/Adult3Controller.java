package com.example.game;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.shape.Rectangle;
import java.net.URL;
import java.util.ResourceBundle;

public class Adult3Controller implements Initializable {

    @FXML
    private ImageView are, cat, duck, empty, excel, heart, how, meeting;

    @FXML
    private ImageView shadow1, shadow2, shadow3, shadow4;

    @FXML
    private ImageView take, care, of, yourself;

    @FXML
    private Label completeLabel;

    @FXML
    void handleDragDone(DragEvent event) {
    }

    private double mouseAnchorX, mouseAnchorY;

    Drag drag1 = new Drag();
    Drag drag2 = new Drag();
    Drag drag3 = new Drag();
    Drag drag4 = new Drag();


    public void initialize(URL location, ResourceBundle resources) {
        drag1.makeDraggableSnap(take,shadow1);
        drag2.makeDraggableSnap(care,shadow2);
        drag3.makeDraggableSnap(of, shadow3);
        drag4.makeDraggableSnap(yourself, shadow4);

        drag1.makeDraggable(are);
        drag1.makeDraggable(cat);
        drag1.makeDraggable(duck);
        drag1.makeDraggable(empty);
        drag1.makeDraggable(excel);
        drag1.makeDraggable(heart);
        drag1.makeDraggable(how);
        drag1.makeDraggable(meeting);

        startCheckingCompletion();
        System.out.println("completeLabel: " + this.completeLabel);
    }
    private void startCheckingCompletion() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                stageCompleted();
            }
        }.start();
    }

    private void stageCompleted() {
        if (drag1.isSnap && drag2.isSnap && drag3.isSnap && drag4.isSnap) {
            completeLabel.setVisible(true);
        } 
    }

    public boolean hasCompleted(){
        System.out.println("completeLabel: " + this.completeLabel);
        if (completeLabel!=null)
            return completeLabel.isVisible();
        else
            return false;
    }
}
