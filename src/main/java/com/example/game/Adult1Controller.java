package com.example.game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import static javafx.scene.control.ButtonType.*;

public class Adult1Controller implements Initializable {
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    @FXML
    private ImageView dust1, dust10, dust11, dust12, dust13, dust14, dust15, dust2, dust3, dust4, dust5, dust6, dust7, dust8, dust9;

    @FXML
    private Label CounterLabel;

    @FXML
    private Label dustMsg;

    @FXML
    private int counter = 0;
    private final int maxCounter = 15;

    private boolean done;

    @FXML
    void hide(MouseEvent event) {
        if (counter < maxCounter) {
            ImageView clicked = (ImageView) event.getSource();
            clicked.setVisible(false);
            counter++;
            updateCounterLabel();
        } if (counter == maxCounter) {
            done = true;
            System.out.println("Adult1Controller: "+this);
            System.out.println("from controller, hasCompleted(): " + hasCompleted());
            showCompletionDialog();
        }
    }

    public boolean hasCompleted() {
        return done;
    }

    private void updateCounterLabel() {
        CounterLabel.setText(counter + " / " + maxCounter);
    }

    private void showCompletionDialog() {
        dustMsg.setVisible(true);

    }


}

