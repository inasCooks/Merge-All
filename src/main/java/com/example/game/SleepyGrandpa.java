package com.example.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.util.Random;


public class SleepyGrandpa {

    private KettleStageCont controller; 
    private Timeline awakeTiming;
    private Timeline sleepAnim;
    private ImageView grandpa;
    private int maxFrame;
    private int frameNo = 0;
    private long frameDuration = 40; // 40ms per frame
    private Image[] frames = new Image[24]; // Array of all sleepAnimitionn frames
    private Random random = new Random(); //grandpa falls to random degree of angle
    private boolean forward = true; //direction of grandpa falling.
    public static boolean grandpaIsAngry=false;

    //fields for "zzz" effect
    Timeline snoozeEffect;
    private ImageView zzz;
    private Image[] framesZZZ = new Image[8];
    private int frameNoZZZ = 0;

    public SleepyGrandpa(String imagePath, ImageView imageView, String zzzImagePath, ImageView zzzImageView,KettleStageCont controller) {
        this.controller=controller;
        this.grandpa = imageView;
        this.zzz = zzzImageView;

        // Load sleepAnim frames for grandpa
        for (int i = 0; i < frames.length; i++) {
            frames[i] = new Image(getClass().getResourceAsStream(imagePath + (i + 1) + ".png"));
        }

        //load sleepAnim frames for zzz effect
        for (int i = 0; i < framesZZZ.length; i++) {
            framesZZZ[i] = new Image(getClass().getResourceAsStream(zzzImagePath + (i + 1) + ".png"));
        }

        // Set up the Timeline for the sleepAnim
        sleepAnim = new Timeline(new KeyFrame(Duration.millis(frameDuration), e -> updateFrame()));
        sleepAnim.setCycleCount(Timeline.INDEFINITE);

        //setup timeline for zzz effect
        snoozeEffect = new Timeline(new KeyFrame(Duration.millis(frameDuration*2.5), e -> updateFrameZZZ()));
        snoozeEffect.setCycleCount(Timeline.INDEFINITE);
    }

    //to start this whole animation process
    public void startSleepyGrandpa(){
        System.out.println("start sleepy grandpa");
        grandpa.setImage(frames[0]);
        startAwakeTiming();
    }

    private void startAwakeTiming() {
        // Random duration between 500ms to 3000ms (0.5s to 3s)
        Duration awakeDuration = Duration.millis(random.nextInt(4500) + 500);
        System.out.println("starting awake timing");
        frameNo=0; //set grandpa to fully standup
        // Initialize awake timing with random duration
        if (awakeTiming==null){
            awakeTiming = new Timeline(new KeyFrame(awakeDuration, e -> {
                if (failGrandpa()){
                    controller.endChallenge();
                } else {
                    maxFrame = random.nextInt(17-13+1) + 13; // Random max frame between 14-18png 
                    startAnimation();
                    // startSnoozeEffect();
                    grandpaIsAngry=false;
                }   
            }));
        }
        awakeTiming.play();
    }

    private void updateFrame() {
        // Update the image in the ImageView
        grandpa.setImage(frames[frameNo]);
        // Update the frame number based on the direction
        frameNo += forward? 1: -1; 

        //if grandpa is at certain angle, he's gonna fall.
        if (frameNo>=17){
            System.out.println("Gradnpa is falling");
            forward =true;
            if (frameNo ==23){ // Once grandpa starts falling, set him to totally lie down on the floor
                stopAnimation();
                grandpa.setImage(frames[23]); //Set grandpa to totally lie down on floor.
                zzz.setImage(framesZZZ[7]); //Say Grr 
                controller.replay = true;
                controller.endChallenge();
                
            }
        } else {
            if (frameNo >= maxFrame) {
                forward = false;
                
            } else if (frameNo <= 0) {
                grandpa.setImage(frames[0]);// Ensure Grandpa fully stands up.
                sleepAnim.stop();
                stopSnoozeEffect();
                startAwakeTiming(); //restart the awakeTiming again
            }
        }     
    }

    public void startAnimation() {
        // Reset frame number and start the falling sleepAnim
        grandpaIsAngry=false;
        frameNo = 0;
        forward = true;
        sleepAnim.play();
        startSnoozeEffect();
    }

    public void stopAnimation() {
        awakeTiming.stop(); 
        stopSnoozeEffect();
        sleepAnim.stop();
        snoozeEffect.stop();
    }

    public void wakeUp(){
        stopAnimation(); // Stops any ongoing sleepAnim
        // add a 1s timer 
        // if (frameNo <= 1 && frameNo >= 0){
        //     zzz.setImage(framesZZZ[7]); //last frame is a picture saying "Grrr" instead of "ZZZ", only happens when he's angry that u click when he's awake.
        //     grandpaIsAngry=true;
        // } else {
        //     grandpaIsAngry = false; // Reset anger if clicked while awake
        // }
        grandpa.setImage(frames[0]); // Set Grandpa to the first frame (awake state)
        System.out.println("wake up");
        startAwakeTiming();
        // Restart the awake timing
        // awakeTiming.stop(); // Stop any ongoing awakeTiming
        // awakeTiming = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> startAwakeTiming()));
        
    }

    public boolean failGrandpa(){
        return (frameNo==frames.length-1)? true: false;
    }

    private void updateFrameZZZ(){
        zzz.setImage(framesZZZ[frameNoZZZ]);
        frameNoZZZ++;
        if (frameNoZZZ >= framesZZZ.length-1) { // Check if last frame reached
            frameNoZZZ = 0;
        }
    }

    private void startSnoozeEffect(){
        System.out.println("Snoozing ");
        frameNoZZZ=0;
        zzz.setImage(framesZZZ[0]); //start the zzz back at first frame.
        snoozeEffect.play();
    }

    private void stopSnoozeEffect(){
        snoozeEffect.stop(); 
        frameNoZZZ=0;
        zzz.setImage(framesZZZ[0]); //start the zzz back at first frame.
    }
}
