package com.example.game;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class KettleStageCont implements Initializable {

    @FXML
    public AnchorPane myRootPane; // referring to the anchor pane in scene builder.
    
    @FXML
    private ImageView playBtn,replayBtn,endingScene, grandpaImageView,snoozeImageView, kettleImageView;
    private MyButton playButton,replayButton;

    private SleepyGrandpa sleepyGrandpa;
    private KettleBoiling kettleBoiling;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label progressLabel;

    double progress=0.0;
    Timeline progressTimer;

    @FXML
    private Rectangle wrapper ,marker, greenArea, transparentRect;

    double initMarker;
    double minX; 
    double maxX;
    Random random = new Random();
    int countDown=5;
    Timeline countDownTimer;
    boolean stopCountDown;
    public static boolean gameRunning=false;
    public static boolean winChallenge=false;
    public boolean replay = true;
    
    public void initialize(URL location, ResourceBundle resources) {

        initMarker = marker.getLayoutX();
        minX = transparentRect.getLayoutX(); //marker's min boundary
        maxX = transparentRect.getLayoutX()+ transparentRect.getWidth()- marker.getWidth(); //marker's max boundary

        progressTimer = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            if (gameRunning){
                autoDecreaseMarker();
                increaseProgress();
            }
        }));
        progressTimer.setCycleCount(Timeline.INDEFINITE);

        sleepyGrandpa = new SleepyGrandpa("/sprite/senior/sleepy_grandpa/", grandpaImageView, "/sprite/senior/zzz_animation/", snoozeImageView, this);
        kettleBoiling = new KettleBoiling("/sprite/senior/kettle_animation/", kettleImageView);
        playButton = new MyButton( "/sprite/senior/buttons/play/1.png", "/sprite/senior/buttons/play/2.png" ,playBtn);
        replayButton = new MyButton( "/sprite/senior/buttons/replay/1.png", "/sprite/senior/buttons/replay/2.png", replayBtn);
        
        //replay button is removed by default.
        endingScene.setVisible(false);
        replayBtn.setVisible(false);
    }

    public void startChallenge(){ 
        countDown=5;
        progress=0;
        wrapper.setOpacity(0.85);
        marker.setLayoutX(initMarker);
        autoDecreaseMarker();
        progressTimer.play();
        sleepyGrandpa.startSleepyGrandpa();
        kettleBoiling.startBoiling();
    }

    public void endChallenge(){
        if (replay){
            replayBtn.setVisible(true);
        }
        gameRunning=false;
        progressTimer.stop();
        sleepyGrandpa.stopAnimation();
        kettleBoiling.stopBoiling();
        wrapper.setVisible(true);
    }

    public boolean hasWonKettleStage(){
        return winChallenge;
    }

    private void playEnding(){
        if (winChallenge){
            wrapper.setOpacity(1.0);
            endingScene.setVisible(true);
        }
    }
    
    @FXML
    private void clickStart(){ //click start/click replay, same method
        System.out.println("kettle challenge started !");   
        if (replayBtn.isVisible())
            replayBtn.setVisible(false);
        else if (playBtn.isVisible())
            playBtn.setVisible(false);

        wrapper.setVisible(false);
        gameRunning=true;
        startChallenge();
    }

    @FXML
    private void hoverButton(){ //this is just a deco effect
        // if (replayBtn.visibleProperty().get())
            replayButton.hoverButton();
        // if (playBtn.visibleProperty().get())
            playButton.hoverButton();
    }

    @FXML
    private void exitButton(){ //this is also a deco effect
        if (replayBtn.isVisible())
            replayButton.exitButton();
        if (playBtn.isVisible())
            playButton.exitButton();
    }

    //grandpa
    @FXML
    private void clickGrandpa(){
        if (gameRunning)
            sleepyGrandpa.wakeUp();
    }

    //progressBar's Logic is controlled in this class because its easier to manipulate the FXML elements directly here.
    private void increaseProgress(){
        if (checkMarkerRange()){
            progress += 0.005;
            progressBar.setProgress(progress);
            if (progress<1)
                progressLabel.setText(String.format("%.0f%%", progress * 100));
            else{
                replay=false; //progreesss = 1 means progress is 100%
                winChallenge=true;
                endChallenge();
                playEnding();
                System.out.println("you passed the kettle challenge !");
            }
                
        } else {
            decreaseProgress();
            // startCountdown();
        }
    }

    private void decreaseProgress(){
        if (progress-0.005>0.0){
            progress -= 0.005;
            progressBar.setProgress(progress);
            progressLabel.setText(String.format("%.0f%%", progress * 100));
        } else if (progress <= 0){

        }
    }

    //Marker's Logic
    public void handleKeyPressed(KeyEvent event) { //press space bar to increase marker
        if (gameRunning)
            switch (event.getCode()) {
                case SPACE:
                if(marker.getLayoutX()>=minX && marker.getLayoutX()<=maxX)
                    moveMarker(random.nextInt(10-5+1)+5); // Move marker 5-10 units to the right
                break;
                default:
                break;
            }
    } 

    private void autoDecreaseMarker(){
        if (marker.getLayoutX()>minX)
            moveMarker(-(random.nextInt(4-2+1)+2)); //setting marker to go fall back to bottom in random speed.
        if (marker.getLayoutX()<=minX){
            winChallenge=false;
            replay=true;
            endChallenge();
        }
        if(SleepyGrandpa.grandpaIsAngry)
                moveMarker(-4); //the marker is gonna fall back down if player makes grandpa angry.
    }
    

    private void moveMarker(double deltaX) {
        if (marker.getLayoutX()<=minX){
            winChallenge=false;
            replay=true;
            endChallenge();
        }
        marker.setLayoutX(marker.getLayoutX() + deltaX);
    }

    private boolean checkMarkerRange(){
        return (marker.getBoundsInParent().intersects(greenArea.getBoundsInParent()))? true: false;
    }

    
    
}