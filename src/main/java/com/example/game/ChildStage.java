package com.example.game;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChildStage {
    private Stage childStage, primaryStage;
    private Scene childScene;
    private ChildCont childCont;
    private LevelOpener levelOpener;

    //load new game
    public ChildStage(Stage primaryStage) {
        initChildStage(primaryStage, null);
    }

    //load saved game
    public ChildStage(Stage primaryStage, GameState gameState) {
        initChildStage(primaryStage, gameState);
    }

    private void initChildStage(Stage primaryStage, GameState gameState) {

        childCont = new ChildCont();
        FXMLLoader loaderChild = new FXMLLoader(getClass().getResource("child-stage.fxml"));
        try {
            loaderChild.setController(childCont);
            Parent root = loaderChild.load();
            childScene = new Scene(root);
            childStage = primaryStage;
            childStage.setTitle("Child Stage");
            childStage.setScene(childScene);

            // childCont = loaderChild.getController();
            //if there is saved game, load game state
            if (gameState != null) {
                childCont.initGameState(gameState);
            }
            //if no, start new game
            else {
                childCont.startNewGame();
            }

            
            
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

