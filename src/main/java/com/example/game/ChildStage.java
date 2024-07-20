package com.example.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChildStage {
    private Stage childStage, primaryStage;
    private ChildCont childCont;

    //load new game
    public ChildStage(Stage primaryStage) {
        
        initChildStage(primaryStage, null);
    }

    //load saved game
    public ChildStage(Stage primaryStage, GameState gameState) {
        initChildStage(primaryStage, gameState);
    }

    private void initChildStage(Stage primaryStage, GameState gameState) {
        this.primaryStage = primaryStage;
        childCont = new ChildCont();
        FXMLLoader loaderChild = new FXMLLoader(getClass().getResource("child-stage.fxml"));
        try {
            loaderChild.setController(childCont);
            Parent root = loaderChild.load();
            Scene childScene = new Scene(root);
            childStage = primaryStage;
            childStage.setTitle("Child Stage");
            childStage.setScene(childScene);

            // ChildCont childCont = fxmlLoader.getController();
            //if there is saved game, load game state
            if (gameState != null) {
                childCont.initGameState(gameState);
            }
            //if no, start new game
            else {
                childCont.startNewGame();
            }

            childScene.getRoot().requestFocus();
        }
        catch (IOException e) {
            System.out.println("Error in initChildStage()");
        }
    }

    public void show() {
        if (childStage != null){
            childStage.show();
        }
        else{
            System.err.println("Stage is null.");
        }
    }
}

