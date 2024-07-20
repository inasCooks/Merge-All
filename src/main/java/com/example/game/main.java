package com.example.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import com.example.game.MainMenuCont;
import com.example.game.AdultController;
import com.example.game.Adult1Controller;
import com.example.game.Adult2Controller;
import com.example.game.Adult3Controller;
import com.example.game.SceneManager;   

public class Main extends Application {

    private SceneManager sceneManager;
    private AdultController adultController;
    private Adult1Controller adult1Controller;
    private Adult2Controller adult2Controller;
    private Adult3Controller adult3Controller;
    private MainMenuCont mainMenuCont;

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-menu.fxml"));
        mainMenuCont = new MainMenuCont();
        fxmlLoader.setController(mainMenuCont);
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("The Chicken Game");
        primaryStage.show();
        
        // adult1Controller = new Adult1Controller();
        // adult2Controller = new Adult2Controller();
        // adult3Controller = new Adult3Controller();
        sceneManager = new SceneManager(primaryStage);
        // sceneManager = new SceneManager(primaryStage, adultController, adult1Controller, adult2Controller, adult3Controller);
        // sceneManager.switchToAdultLevel();

    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
