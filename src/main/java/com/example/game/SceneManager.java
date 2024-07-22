// package com.example.game;

// import java.io.IOException;
// import javafx.stage.Stage;


// public class SceneManager implements LevelOpener{

//     Stage primaryStage;
//     private AdultStage adultStage;
//     private SeniorStage seniorStage;
//     private ChildStage childStage;
//     private AdultController adultController;
//     private Adult1Controller adult1Controller;
//     private Adult2Controller adult2Controller;
//     private Adult3Controller adult3Controller;
//     private LevelOpener levelOpener;
    

//     public SceneManager(Stage primaryStage) {
//         this.primaryStage = primaryStage;
//     }

//     @Override
//     public void switchToAdultLevel() throws IOException{
//         adultStage = new AdultStage(primaryStage); 

//     }
//     @Override
//     public void switchToSeniorLevel() throws IOException {
//         this.seniorStage = new SeniorStage(primaryStage);
//     }

//     // Add methods for other stages (Stage1, Stage2, etc.) as needed
// }

