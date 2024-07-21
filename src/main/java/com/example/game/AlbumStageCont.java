package com.example.game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AlbumStageCont implements Initializable{
    @FXML
    private ImageView pic1, pic2, pic3, pic4, pic5, pic6;
    @FXML
    private ImageView album1, album2, album3, album4, album5, album6;
    @FXML
    private Label congratulationsLabel;
    @FXML
    private AnchorPane seniorStage2;
    @FXML
    void handleDragDone(DragEvent event) {}

    private static boolean hasCompleted=false;
    private Dragdrop dragDrop = new Dragdrop();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dragDrop.initializeAlbum(pic1, album1);
        dragDrop.initializeAlbum(pic2, album2);
        dragDrop.initializeAlbum(pic3, album3);
        dragDrop.initializeAlbum(pic4, album4);
        dragDrop.initializeAlbum(pic5, album5);
        dragDrop.initializeAlbum(pic6, album6);
        dragDrop.initializeMessage(congratulationsLabel);
    }

    public boolean albumHasCompleted(){
        return Dragdrop.hasCompleted;
    }
}
