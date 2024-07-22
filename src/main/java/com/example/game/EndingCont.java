package com.example.game;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class EndingCont  implements Initializable{

    @FXML
    private ImageView albumCover;

    @FXML
    private Label name;

    public void initialize(URL location, ResourceBundle resources){
        name.setText(ChildCont.playerName);
    }
}
