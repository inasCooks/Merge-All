package com.example.game;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class main extends Application {

    private SceneManager sceneManager;
    private AdultController adultController;
    private Adult1Controller adult1Controller;
    private Adult2Controller adult2Controller;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("The Chicken Game");
        primaryStage.show();
        
        adult1Controller = new Adult1Controller();
        adult2Controller = new Adult2Controller();
        
        sceneManager = new SceneManager(primaryStage, adultController, adult1Controller, adult2Controller);
        sceneManager.switchToAdultLevel();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
