package com.example.game;

import java.io.IOException;
import java.util.random.RandomGenerator.LeapableGenerator;

import javafx.stage.Stage;


public class SceneManager implements LevelOpener{

    Stage primaryStage;
    private AdultStage adultStage;
    private SeniorStage seniorStage;
    private AdultController adultController;
    private Adult1Controller adult1Controller;
    private Adult2Controller adult2Controller;
    private Adult3Controller adult3Controller;
    private LevelOpener levelOpener;
    

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public SceneManager getSceneManager(){ return this; } // getSceneManager

    public void switchToChildStage() throws IOException{
        
    }

    public void switchToAdultLevel() throws IOException{
        // adultStage = new AdultStage(primaryStage, adultController, adult1Controller, adult2Controller, adult3Controller);
        adultStage = new AdultStage(primaryStage, this);

    }
    @Override
    public void switchToSeniorLevel() throws IOException {
        this.seniorStage = new SeniorStage(primaryStage);
    }

    // Add methods for other stages (Stage1, Stage2, etc.) as needed
}

