package com.example.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    private AdultController adultController;
    private Adult1Controller adult1Controller;
    private Adult2Controller adult2Controller;
    private Adult3Controller adult3Controller;
    private MainMenuCont mainMenuCont;

    @Override
    public void start(Stage primaryStage) throws IOException {

        GameStateManager.deleteGameState();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-menu.fxml"));
        mainMenuCont = new MainMenuCont();
        // fxmlLoader.setController(mainMenuCont);
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("The Chicken Game");
        
        primaryStage.show();
    
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
