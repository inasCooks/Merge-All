package com.example.game;

import java.io.IOException;
import javafx.stage.Stage;


public class SceneManager {

    Stage primaryStage;
    private AdultStage adultStage;
    private AdultController adultController;
    private Adult1Controller adult1Controller;
    private Adult2Controller adult2Controller;
    private Adult3Controller adult3Controller;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void switchToChildStage() throws IOException{
        
    }

    public void switchToAdultLevel() throws IOException{
        // adultStage = new AdultStage(primaryStage, adultController, adult1Controller, adult2Controller, adult3Controller);
        adultStage = new AdultStage(primaryStage);

    }
    // public void switchToLevel3() throws IOException {
    //     this.level3 = new Level3(primaryStage, level3Controller, this.kettleStageController, this.albumStageController);
    //     level3.initStage3();
    //     System.out.println("scene manager: " + kettleStageController);
    // }

    // Add methods for other stages (Stage1, Stage2, etc.) as needed
}

