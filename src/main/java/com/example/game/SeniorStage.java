package com.example.game;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SeniorStage extends AnchorPane implements MiniStageOpener {

    private Stage kettleStage, primaryStage, albumStage,seniorStage;
    private Scene seniorScene;
    private KettleStageCont kettleStageCont;
    private SeniorCont seniorCont;
    private AlbumStageCont albumStageCont;
    private MiniStageOpener miniStageOpener;

    public SeniorStage(Stage primaryStage) throws IOException  { //instance is created in SceneManager
        super(new AnchorPane()); // Example size
        kettleStageCont = new KettleStageCont();
        albumStageCont = new AlbumStageCont();
        this.primaryStage=primaryStage;
        // this.levelOpener=levelOpener;

        //Load FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("senior-mainStage.fxml"));
        this.seniorCont= new SeniorCont(this, this, kettleStageCont, albumStageCont );
        loader.setController(this.seniorCont);
        Parent root = loader.load();
        seniorStage = primaryStage;
        seniorScene = new Scene(root);
        seniorScene.setFill(new Color(0, 0, 0, 0.75)); 
        seniorStage.setScene(seniorScene);

        //To set if main window is closed, then the mini popup windows will close too.
        primaryStage.setOnCloseRequest(event -> {
            if (kettleStage!=null)
                kettleStage.close();
            if (albumStage!=null)
                albumStage.close();
        });

        Platform.runLater(() -> {  // Schedule focus request after scene is shown
            root.setFocusTraversable(true);
            root.requestFocus();
        });
    }

    public void closeKettleStage(){
        kettleStage.close();
    }

    //open popup challenge 1
    @Override
    public void openKettleStage(){
        FXMLLoader loaderKettleStage = new FXMLLoader(getClass().getResource("senior1-popup.fxml"));
        try{
            loaderKettleStage.setController(kettleStageCont);
            AnchorPane kettleRoot = loaderKettleStage.load();
            Scene kettleScene = new Scene(kettleRoot);
            kettleScene.setOnKeyPressed(kettleStageCont::handleKeyPressed);
            // This is needed to handle spacebar key pressing event
            
            if(kettleStage==null){
                kettleStage = new Stage();
                kettleStage.setTitle("The Kettle Challenge");
                // Image icon = new Image(getClass().getClassLoader().getResource("sprite/senior/grand_chicken.png").toExternalForm());
                // kettleStage.getIcons().add(icon);
                kettleStage.setScene(kettleScene);
            }
            else{
                kettleStage.setOnHiding(e -> {
                    System.out.println("hiding detected");
                    //call a void from Level3Controller
                    this.seniorCont.checkWinStatus();
                });
            }

            kettleStage.show();
            Platform.runLater(() -> {  // Schedule focus request after scene is shown
            kettleStageCont.myRootPane.setFocusTraversable(true);
            kettleStageCont.myRootPane.requestFocus();
            });
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //open popup challenge 2
    @Override
    public void openAlbumStage(){
        FXMLLoader loaderAlbumStage = new FXMLLoader(getClass().getResource("senior2-popup.fxml"));
        try{
            loaderAlbumStage.setController(albumStageCont);
            BorderPane albumRoot = loaderAlbumStage.load();
            Scene albumScene = new Scene(albumRoot);
            // kettleScene.setOnKeyPressed(controller::handleKeyPressed);
            // This is needed to handle spacebar key pressing event
            
            
            if(albumStage==null){
                albumStage = new Stage();
                albumStage.setTitle("The Album Game");
                albumStage.setScene(albumScene);
            }
            albumStage.show();
            if (albumStage != null) {
                albumStage.setOnHiding(e -> {
                    System.out.println("hiding detected");
                    //call a void from Level3Controller
                    this.seniorCont.checkWinStatus();
                });
            }
            System.out.println("open album stage: "+ albumStage);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void openAdult1Stage() {
        miniStageOpener.openAdult1Stage();
    }

    @Override
    public void openAdult2Stage() {
        miniStageOpener.openAdult2Stage();
    }

    @Override
    public void openAdult3Stage() {
        miniStageOpener.openAdult3Stage();
    }
}
