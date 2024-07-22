package com.example.game;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;



public class SeniorCont implements Initializable{

    private SeniorStage seniorStage;
    private MiniStageOpener miniStageOpener;
    private KettleStageCont kettleStageCont;
    private AlbumStageCont albumStageCont;
    private Chicken chicken;

    @FXML
    private AnchorPane scene;

    @FXML
    private Rectangle topB,leftB,rightB, bottomB;

    @FXML
    private ImageView bgImage, chickenSprite, npc, album;
    private Sprite albumSprite,npcSprite;

    @FXML
    private Label dialogLabel;
    private int currentDialog=0;
    private String[] dialogList = {
        "You: Finally left that office, here looks like a living room huh", //PART 1: after clicking npc
        "You: Smells of coffee here.. Seems like someone is brewing coffee.",
        "You: An old chicken here, but he's dozing off... Maybe I should just look after the kettle.",
        "Grandpa: That's nice of you, kid. ", //PART 2
        "Also.. My photos fell off the album, but my hands(legs) are too shaky to sort them out.." 
    };

    private boolean part2=false; //part2=true when kettleStage.hasCompleted() is true
    private boolean finished=false; // true when albumStageCont.albumHasCompleted() is true

    AnimationTimer collisionTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            checkCollision(chickenSprite, topB, bottomB, rightB, leftB);
        }
    };

    public SeniorCont(SeniorStage seniorStage, MiniStageOpener miniStageOpener, KettleStageCont kettleStageCont, AlbumStageCont albumStageCont) { //where is this consturctor declared?
    this.seniorStage = seniorStage;
    this.kettleStageCont = kettleStageCont; 
    this.albumStageCont=albumStageCont;
    this.miniStageOpener=miniStageOpener;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //setup dialog
        dialogLabel.setText(dialogList[0]);

        //setup sprites
        chicken = new Chicken(chickenSprite, scene, bgImage);
        npcSprite = new Sprite("/sprite/senior/npc_grandpa/", npc);
        albumSprite = new Sprite("/sprite/senior/album_sprite/", album);
        npcSprite.showWarning();
        albumSprite.hideWarning();
        chicken.makeMovable();
        collisionTimer.start();    }
    
    @FXML
    private void clickNPC(){
        if(!part2 && !dialogLabel.isVisible()){ //if currently doing part 1 &&  part 1 dialog has been covered
            miniStageOpener.openKettleStage();
        }
    }

    @FXML
    private void clickAlbum(){
        if (part2 && !finished && !dialogLabel.isVisible()){ 
            miniStageOpener.openAlbumStage();
        }
    }  

    @FXML
    private void clickAnywhere(){
        if(!part2){
            if(currentDialog+1<3){
                currentDialog++;
                dialogLabel.setText(dialogList[currentDialog]);
            } else {
                if (dialogLabel.isVisible())
                    dialogLabel.setVisible(false);
                miniStageOpener.openKettleStage();
            }
        } else if(part2 && !finished){
            if (currentDialog+1<4){
                currentDialog++;
                dialogLabel.setText(dialogList[currentDialog]);
            } else {
                if (dialogLabel.isVisible())
                    dialogLabel.setVisible(false);
                miniStageOpener.openAlbumStage();
            }
        }
    }

    public void checkWinStatus(){
        if (!part2){
            System.out.println("checking win status for kettle stage");
            if (kettleStageCont.hasWonKettleStage()) {
                System.out.println("kettle stage (stage 1) success! ");

                //setup sprite & dialog logics for part 2 now
                part2 = true;
                albumSprite.showWarning();
                npcSprite.hideWarning();
                clickAnywhere();
                dialogLabel.setVisible(true);
            }
        } else if (part2 && !finished){
            System.out.println("checking win status");
            if (albumStageCont.albumHasCompleted()){
                System.out.println("album stage (stage 2) success! ");
                finished=true;
                albumSprite.hideWarning();
            }
        }
        
    }

    public void checkCollision(ImageView chickenPlayer, Rectangle top, Rectangle bottom,  Rectangle right, Rectangle left){
        if (chickenPlayer.getBoundsInParent().intersects(top.getBoundsInParent())) {
            chicken.setwPressed(false);
        }
        if (chickenPlayer.getBoundsInParent().intersects(right.getBoundsInParent())) {
            chicken.setdPressed(false);
        }
        if (chickenPlayer.getBoundsInParent().intersects(left.getBoundsInParent())) {
            chicken.setaPressed(false);
        }
    }

}