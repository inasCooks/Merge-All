package com.example.game;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdultStage extends AnchorPane implements MiniStageOpener {

    private MiniStageOpener miniStageOpener;
    private LevelOpener levelOpener;
    private AdultController adultController;
    private Adult1Controller adult1Controller;
    private Adult2Controller adult2Controller;
    private Adult3Controller adult3Controller;
    private Stage primaryStage, adultStage,sortDocStage, officeTableStage, dustStage;
    private Scene adultScene, sortDocScene, dustScene, officeTableScene;

    // @FXML
    // private AnchorPane rootAnchorPane;
    public AdultStage(Stage primaryStage) throws IOException{
    // public AdultStage(Stage primaryStage, AdultController adultController, Adult1Controller adult1Controller, Adult2Controller adult2Controller, Adult3Controller adult3Controller) throws IOException{
        super(new AnchorPane());
        adult1Controller = new Adult1Controller();
        adult2Controller = new Adult2Controller();
        adult3Controller = new Adult3Controller();
        this.primaryStage = primaryStage;
        // this.adultController=adultController;
        // this.adult1Controller = adult1Controller;
        // this.adult2Controller = adult2Controller;
        // this.adult3Controller = adult3Controller;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adult-mainStage.fxml"));
        try{
            this.adultController= new AdultController(this ,this.adult1Controller, this.adult2Controller, this.adult3Controller);
            loader.setController(this.adultController);
            Parent root = loader.load();
            adultStage = primaryStage;
            adultScene = new Scene(root);
            adultStage.setScene(adultScene);
        } catch (IOException e){
            e.printStackTrace();
        }
        
        primaryStage.setOnCloseRequest(event -> {
            if (dustStage!=null)
                dustStage.close();
            if (sortDocStage!=null)
                sortDocStage.close();
        });

        Platform.runLater(() -> {  // Schedule focus request after scene is shown
            this.adultController.scene.setFocusTraversable(true);
            this.adultController.scene.requestFocus();
        });
    }

    @Override
    public void openAdult1Stage(){
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("adult1-popup.fxml"));
        try{
            loader1.setController(adult1Controller);
            Parent dustStageRoot = loader1.load();
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
            System.out.println("adult1cont from adultstage: " + adult1Controller);

        }catch (IOException e){
            e.printStackTrace();
            System.out.println("somethin wrong with popup 1 loaaaader");
        }
    }

    @Override
    public void openAdult2Stage(){
        System.out.println("poppin up doc sort stage");
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("adult2-popup.fxml"));
        try{
            loader2.setController(adult2Controller);
            Parent sortDocRoot = loader2.load();
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
            System.out.println("adult2cont from adultstage: " + adult2Controller);

        }catch (IOException e){
            e.printStackTrace();
            System.out.println("somethin wrong with popup 2 loaaaader");
        }  
    }

    @Override
    public void openAdult3Stage(){
        System.out.println("poppin up THIRD POPUP stage");
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("adult3-popup.fxml"));
        try{
            loader3.setController(adult3Controller);
            Parent officeRoot = loader3.load();
            officeTableScene = new Scene(officeRoot); 
            

            if (officeTableStage==null){
                officeTableStage = new Stage();
                officeTableStage.setTitle("Office Table");
                officeTableStage.setScene(officeTableScene);
            } else {
                officeTableStage.setOnHiding(e -> {
                    System.out.println("popup 3 is closed");
                    this.adultController.checkWinStatus();
                });
            }
            officeTableStage.show();
            System.out.println("adult3cont from adultstage: " + adult3Controller);

        }catch (IOException e){
            e.printStackTrace();
            System.out.println("somethin wrong with popup 3 loaaaader");
        }  
    }

    @Override
    public void openKettleStage() {
        miniStageOpener.openKettleStage();  
    }

    @Override
    public void openAlbumStage() {
        miniStageOpener.openAlbumStage();
    }
}
