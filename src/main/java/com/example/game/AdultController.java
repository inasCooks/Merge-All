package com.example.game;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AdultController implements Initializable {

    private Chicken chicken;
    private MiniStageOpener miniStageOpener;
    private Stage dustStage;
    private Adult1Controller adult1Controller;
    private Adult2Controller adult2Controller;

    // @FXML
    // private Button button;

    @FXML
    private ImageView bgImage;

    @FXML
    private ImageView openedDoor;

    @FXML
    private ImageView chickenPlayer;

    @FXML
    private AnchorPane scene;

    @FXML
    private Rectangle topB;

    @FXML
    private Rectangle bottomB;

    @FXML
    private Rectangle leftB;

    @FXML
    private Rectangle rightB;

    @FXML Rectangle floor;

    @FXML
    private ImageView docSection;

    @FXML
    private ImageView warningIcon;

    @FXML
    private Label dialogLabel;
    private String[] dialogList = {
            "You: *Cough cough* It’s so dusty here",
            "Chicken NPC: Can you help clean it?"
    };
    
    private boolean done1 = false; //done 1 means completed & won popup stage 1
    private boolean done2 = false;
    private boolean done3 = false;
    private int spawnPosX = 730;
    private int spawnPosY = 660;
    private int iconPosX_1 = 926;
    private int iconPosY_1 = 96; 

    AnimationTimer collisionTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            checkCollision(chickenPlayer, topB, bottomB, rightB, leftB);
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        warningIcon.setVisible(true);
        chicken = new Chicken(chickenPlayer,scene);
        chicken.makeMovable();
        collisionTimer.start();
    }

    public AdultController(MiniStageOpener miniStageOpener, Adult1Controller adult1Controller, Adult2Controller adult2Controller){
        this.miniStageOpener=miniStageOpener;
        this.adult1Controller = adult1Controller;
        this.adult2Controller = adult2Controller;
        System.out.println("adulut1controller in adult controller ..." + adult1Controller );
    }
    
    // @FXML
    // void handleButtonAction(ActionEvent event) {
    //     miniStageOpener.openAdult1Stage();
    // }

    @FXML
    void clickIcon(){
        miniStageOpener.openAdult1Stage();
    }

    public void checkCollision(ImageView chickenPlayer, Rectangle top, Rectangle bottom, Rectangle right, Rectangle left){
        if (chickenPlayer.getBoundsInParent().intersects(top.getBoundsInParent())) {
            chicken.setwPressed(false);
            if (openedDoor.isVisible() && chickenPlayer.getBoundsInParent().intersects(openedDoor.getBoundsInParent())){
                chicken.setwPressed(true);
                nextStage();
            }
                
        }
        if (chickenPlayer.getBoundsInParent().intersects(bottom.getBoundsInParent())) {
            chicken.setsPressed(false);
        }
        if (chickenPlayer.getBoundsInParent().intersects(right.getBoundsInParent())) {
            chicken.setdPressed(false);
        }
        if (chickenPlayer.getBoundsInParent().intersects(left.getBoundsInParent())) {
            chicken.setaPressed(false);
        }

    }

    //nextStage gets triggered upon entering the door to respawn sprite & update appearance
    public void nextStage(){
        //to respawn
        chickenPlayer.setLayoutX(spawnPosX);
        chickenPlayer.setLayoutY(spawnPosY);
        openedDoor.setVisible(false);
        //control appearance of next office parts
        if (done1 && !done2 && !done3){
            warningIcon.setVisible(true);
            docSection.setVisible(true);
            //documentSpace.setVisibile(true);  //add the office table sprite pwis
        } else if (done1 && done2 && !done3){
            //officeTable.setVisibile(true);
        } else if (done1 && done2 && done3){
            //can exit adult level
        }
    }

    
    
    //gets triggered everytime popup window is closed.
    public void checkWinStatus() {
        System.out.println("popup closed detected");
        if (!done1){
            if(adult1Controller.hasCompleted()){
                System.out.println("stage 1 is completed");
                done1 = true;
                // button.setVisible(false); //hide button cz game has been completed
                unlockDoor();
            }
        } else if (done1 && !done2){
            if(adult2Controller.hasCompleted()){
                System.out.println("stage 2 is completed");
                done2 = true;
                warningIcon.setVisible(false); //hide button cz game has been completed
                unlockDoor();
            }
        } else if (done1 && done2 && !done3){

        }
        
    }
    
    public void unlockDoor() {
        openedDoor.setVisible(true);
    }


    
}

