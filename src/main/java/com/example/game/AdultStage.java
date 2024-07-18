package com.example.game;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdultStage extends AnchorPane implements MiniStageOpener {
    private Stage primaryStage;
    private AdultController adultController;
    private Adult1Controller adult1Controller;
    private Adult2Controller adult2Controller;
    private MiniStageOpener miniStageOpener;
    private Stage sortDocStage;
    private Stage adultStage;
    private Stage dustStage;
    private Scene adultScene;
    private Scene sortDocScene;
    private Scene dustScene;

    @FXML
    private AnchorPane rootAnchorPane;

    public AdultStage(Stage primaryStage, AdultController adultController, Adult1Controller adult1Controller, Adult2Controller adult2Controller) throws IOException{
        super(new AnchorPane());
        this.primaryStage = primaryStage;
        this.adultController=adultController;
        this.adult1Controller = adult1Controller;
        this.adult2Controller = adult2Controller;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdultStage.fxml"));
        try{
            this.adultController= new AdultController(this, this.adult1Controller, this.adult2Controller);
            loader.setController(this.adultController);
            Parent root = loader.load();
            adultStage = primaryStage;
            adultScene = new Scene(root);
            adultStage.setScene(adultScene);
        } catch (IOException e){
            e.printStackTrace();
        }
        

    }

    @Override
    public void openAdult1Stage(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adult1-popup.fxml"));
        try{
            loader.setController(adult1Controller);
            Parent dustStageRoot = loader.load();
            dustScene = new Scene(dustStageRoot); 
            

            if (dustStage==null){
                dustStage = new Stage();
                dustStage.setTitle("Office Lounge");
                dustStage.setScene(dustScene);
            } 
            if (dustStage!=null) {
                dustStage.setOnHiding(e -> {
                    adultController.checkWinStatus();
                });
            }
            dustStage.show();

        }catch (IOException e){
            e.printStackTrace();
            System.out.println("somethin wrong with popup 1 loaaaader");
        }
    }

    @Override
    public void openAdult2Stage(){
        System.out.println("poppin up doc sort stage");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adult2-popup.fxml"));
        try{
            this.adult2Controller= new Adult2Controller();
            loader.setController(this.adult2Controller);
            Parent sortDocRoot = loader.load();
            sortDocStage = primaryStage;
            sortDocScene = new Scene(sortDocRoot); 
            

            if (sortDocStage==null){
                sortDocStage = new Stage();
                sortDocStage.setTitle("Document Section");
                sortDocStage.setScene(sortDocScene);
            } else {
                sortDocStage.setOnHiding(e -> {
                    this.adultController.checkWinStatus();
                });
            }
            sortDocStage.show();

        }catch (IOException e){
            e.printStackTrace();
            System.out.println("somethin wrong with popup 2 loaaaader");
        }  
    }

    public void openAdult3Stage(){
        System.out.println("poppin up doc sort stage");
        //do the same as stage 1 & 2
    }
}
