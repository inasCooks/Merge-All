package com.example.game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Adult2Controller implements Initializable {

    @FXML
    private ImageView callfile1, callfile2, callfile3, callfile4;
    @FXML
    private ImageView moneyfile1, moneyfile2, moneyfile3, moneyfile4;
    @FXML
    private ImageView callfolder;
    @FXML
    private ImageView moneyfolder;
    @FXML
    private Label completeMsg;
    @FXML
    private AnchorPane scene;
    @FXML
    void handleDragDone(DragEvent event) {}

    private boolean done;

    Dragdrop draggable = new Dragdrop();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        draggable.initializeFolder(callfile1, callfolder);
        draggable.initializeFolder(callfile2, callfolder);
        draggable.initializeFolder(callfile3, callfolder);
        draggable.initializeFolder(callfile4, callfolder);

        draggable.initializeFolder(moneyfile1, moneyfolder);
        draggable.initializeFolder(moneyfile2, moneyfolder);
        draggable.initializeFolder(moneyfile3, moneyfolder);
        draggable.initializeFolder(moneyfile4, moneyfolder);

        draggable.initializeMessage(completeMsg);
    }

    public boolean hasCompleted(){
        done = draggable.isCurCompleted();
        return done;
    }
}