package com.example.game;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Ending extends AnchorPane {

    private EndingCont endingCont;
    private Scene endingScene;
    private Stage endingStage;
    
    public Ending(Stage primaryStage) throws IOException{
        super(new AnchorPane());
        endingCont = new EndingCont(); 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ending-scene.fxml"));
        try{
            loader.setController(this.endingCont);
            Parent root = loader.load();
            endingScene = new Scene(root);
            endingStage = primaryStage;
            endingStage.setScene(endingScene);
        } catch(IOException e){
            System.out.println("Error in Ending()");
        }
        
    }
}
