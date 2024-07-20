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
    private Adult3Controller adult3Controller;

    @FXML
    private Button button;

    @FXML
    public AnchorPane scene; //made public so that adulstage can access key pressing focus mechanism.

    @FXML
    private Rectangle topB,bottomB,leftB,rightB;

    @FXML
    private ImageView chickenPlayer,bgImage,openedDoor,docSection,warningIcon, officeTable;

    @FXML
    private Label dialogLabel;
    private String[] dialogList = {
            "You: *Cough cough* It’s so dusty here",
            "Chicken NPC: Can you help clean it?"
    };
    
    private boolean done1 = false; //done 1 means completed & won popup stage 1
    private boolean done2 = false;
    private boolean done3 = false;
    private double spawnPosX,spawnPosY;
    private double iconPosX_1 = 928.0;
    private double iconPosY_1 = 111.0; 
    private double iconPosX_2 = 960.0;
    private double iconPosY_2 = 425.0;

    AnimationTimer collisionTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            checkCollision(chickenPlayer, topB, bottomB, rightB, leftB);
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        chicken = new Chicken(chickenPlayer,scene);
        spawnPosX = chickenPlayer.getLayoutX();
        spawnPosY = chickenPlayer.getLayoutY();
        chicken.makeMovable();
        collisionTimer.start();

    }

    public AdultController(MiniStageOpener miniStageOpener, Adult1Controller adult1Controller, Adult2Controller adult2Controller, Adult3Controller adult3Controller){
        this.miniStageOpener= miniStageOpener;
        this.adult1Controller = adult1Controller;
        this.adult2Controller = adult2Controller;
        this.adult3Controller = adult3Controller;
        System.out.println("adulut1controller in adult controller ..." + adult1Controller );
    }

    @FXML
    void clickIcon(){
        if (!done1)
            miniStageOpener.openAdult1Stage();
        else if (done1 && !done2 && !done3){
            miniStageOpener.openAdult2Stage();
            System.out.println("shd have opened sec popup");
        } 
        else if (done1 && done2 && !done3)
            miniStageOpener.openAdult3Stage();
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

    private void changeSpritePos(ImageView sprite,double newPosX,double newPosY){
        sprite.setLayoutX(newPosX);
        sprite.setLayoutY(newPosY);
    }

    // nextStage gets triggered upon entering the door to respawn sprite & update appearance
    public void nextStage(){
        System.out.println("popup closed detected: " +done1 + " " + done2 + " " + done3);
        //to respawn
        changeSpritePos(chickenPlayer, spawnPosX, spawnPosY);
        doorLocked(true); //lock the door again

        if (done1 && !done2 && !done3){
            System.out.println("set up for next stage");
            changeSpritePos(warningIcon, iconPosX_1, iconPosY_1);
            System.out.println("Position: "+warningIcon.getLayoutX()+" "+warningIcon.getLayoutY());
            warningIcon.setVisible(true);
            docSection.setVisible(true);
        } else if (done1 && done2 && !done3){
            changeSpritePos(warningIcon, iconPosX_2, iconPosY_2);
            warningIcon.setVisible(true);
            officeTable.setVisible(true);
        } else if (done1 && done2 && done3){
            //can exit adult level
        }
    }

    
    
    //gets triggered everytime popup window is closed.
    public void checkWinStatus() {
        System.out.println("popup closed detected: " +done1 + " " + done2 + " " + done3);
        System.out.println("winStatus: " +adult1Controller.hasCompleted() + " " + adult2Controller.hasCompleted());
        if (!done1){
            if(adult1Controller.hasCompleted()){
                System.out.println("stage 1 is completed");
                done1 = true;
                warningIcon.setVisible(false); //hide button cz game has been completed
                doorLocked(false);
            }
        } else if (done1 && !done2 && !done3){
            if(adult2Controller.hasCompleted()){
                System.out.println("stage 2 is completed");
                done2 = true;
                warningIcon.setVisible(false); //hide button cz game has been completed
                doorLocked(false);
            }
        } else if (done1 && done2 && !done3){
            if(adult3Controller.hasCompleted()){
                System.out.println("stage 2 is completed");
                done2 = true;
                warningIcon.setVisible(false); //hide button cz game has been completed
                doorLocked(false);
            }
        }
        
    }
    
    public void doorLocked(boolean bool) {
        if(!bool)
            openedDoor.setVisible(true); //unlock Door
        else
            openedDoor.setVisible(false); //lock Door

    }


    
}

