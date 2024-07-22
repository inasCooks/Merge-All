// package com.example.game;

// import javafx.animation.AnimationTimer;
// import javafx.beans.binding.BooleanBinding;
// import javafx.beans.property.BooleanProperty;
// import javafx.beans.property.SimpleBooleanProperty;
// import javafx.scene.image.ImageView;
// import javafx.scene.input.KeyCode;
// import javafx.scene.layout.AnchorPane;
// import javafx.geometry.Bounds;

// public class ChickenChild extends Chicken {
//     private BooleanProperty wPressed = new SimpleBooleanProperty();
//     private BooleanProperty aPressed = new SimpleBooleanProperty();
//     private BooleanProperty sPressed = new SimpleBooleanProperty();
//     private BooleanProperty dPressed = new SimpleBooleanProperty();
//     private BooleanBinding keyPressed = wPressed.or(aPressed).or(sPressed).or(dPressed);

//     private AnchorPane sceneAnchorPane;
//     private ImageView sprite;
//     private ImageView background;
//     private Runnable onObjectCollected;

//     private double minX;
//     private double minY;

//     private ImageView[] objects;
//     private String playerName;
//     private boolean isPaused = false;

//     public void setwPressed(boolean bool){
//         super.setwPressed(bool);
//     }
//     public void setaPressed(boolean bool){
//         super.setaPressed(bool);
//     }
//     public void setsPressed(boolean bool){
//         super.setsPressed(bool);
//     }
//     public void setdPressed(boolean bool){
//         super.setdPressed(bool);
//     }

    
//     public ChickenChild(ImageView sprite, AnchorPane sceneAnchorPane, ImageView background, Runnable onObjectCollected){
//         super(sprite, sceneAnchorPane);
//         this.sprite = sprite;
//         this.sceneAnchorPane = sceneAnchorPane;
//         this.background = background;
//         this.onObjectCollected = onObjectCollected;
//         System.out.println("runnable: " + onObjectCollected);
//         //boundaries
//         minX = sceneAnchorPane.getLayoutX();
//         minY = sceneAnchorPane.getLayoutY() + 200;
//         System.out.println("Chicken Child has been born");
//     }

//     public void setPlayerName(String playerName) {
//         this.playerName = playerName;
//         System.out.println("Player name set.");
//         System.out.println("Name: " + playerName);
//     }

//     //use in Stage 3
//     public String getPlayerName(){
//         return super.getPlayerName();
//     }

//     @Override
//     public void makeMovable(){
//         super.movementSetup();
//         System.out.println("makeMovable called " + this);
        
//             keyPressed.addListener(((observableValue, aBoolean, t1) -> {
//                 try{
//                     if(!aBoolean){
//                         timerChild.start();
//                         System.out.println("timer start");
//                     } else {
//                         timerChild.stop();
//                         System.out.println("timer Stop");
//                     }
//                 } catch (Exception e){
//                     e.printStackTrace();
//                 }
//             }));
//             System.out.println("makeMovable finished");
//     }

//     protected void movementSetup() {
//         super.movementSetup();
//     }

//     AnimationTimer timerChild = new AnimationTimer() {
//         @Override
//         public void handle(long timestamp) {
//             System.out.println("Timer child is called");
//             if (isPaused) return;
//             int movementVariable = 3;

//             double newX = sprite.getLayoutX();
//             double newY = sprite.getLayoutY();

//             if (wPressed.get()) {
//                 newY -= movementVariable;
//             }

//             if (sPressed.get()) {
//                 newY += movementVariable;
//             }

//             if (aPressed.get()) {
//                 newX -= movementVariable;
//             }

//             if (dPressed.get()) {
//                 newX += movementVariable;
//             }

//             if (newX >= minX && newX + sprite.getFitWidth() <= background.getFitWidth()) {
//                 sprite.setLayoutX(newX);
//             }
//             if (newY >= minY && newY + sprite.getFitHeight() <= background.getFitHeight() - 200) {
//                 sprite.setLayoutY(newY);
//             }

//             checkObjectCollection();
//         }
//     };

//     public void setObjects(ImageView[] objects) {
//         this.objects = objects;
//         System.out.println("objects " + objects);
//     }
//     private void checkObjectCollection() {
//         System.out.println("checking object collection");
//         if (objects == null || onObjectCollected == null) {
//             System.out.println("something is null: " + objects + " " + onObjectCollected);
//             return;
//         }

//         for (ImageView object : objects) {
//             if (object.isVisible()) {
//                 System.out.println("visible object detected "+object);
//                 Bounds chickenBounds = sprite.getBoundsInParent();
//                 Bounds objectBounds = object.getBoundsInParent();
//                 if (chickenBounds.intersects(objectBounds)) {
//                     onObjectCollected.run();
//                     System.out.println("should be running onOBjectCollected already");
//                     break;
//                 }
//             }
//         }
//     }

//     // protected void timerStart() {
//     //     super.timerStart(timerChild);
//     // }

//     // protected void timerStop() {
//     //     super.timerStop(timerChild);
//     // }

//     @Override
//     public void pauseMovement() {
//         isPaused = true;
//         timerChild.stop();
//     }

//     @Override
//     public void resumeMovement() {
//         isPaused = false;
//         timerChild.start();
//     }
//     public double[] getSpritePosition() {
//         return new double[]{sprite.getLayoutX(), sprite.getLayoutY()};
//     }

//     public void setSpritePosition(double[] position) {
//         if (position != null && position.length == 2) {
//             sprite.setLayoutX(position[0]);
//             sprite.setLayoutY(position[1]);
//         }
//     }
// }

package com.example.game;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.geometry.Bounds;

public class Chicken {
    private final BooleanProperty wPressed = new SimpleBooleanProperty();
    private final BooleanProperty aPressed = new SimpleBooleanProperty();
    private final BooleanProperty sPressed = new SimpleBooleanProperty();
    private final BooleanProperty dPressed = new SimpleBooleanProperty();
    private final BooleanBinding keyPressed = wPressed.or(aPressed).or(sPressed).or(dPressed);

    private final AnchorPane scene;
    private final ImageView sprite;
    private final ImageView background;
    private final Runnable onObjectCollected;

    private final double minX;
    private final double minY;

    private ImageView[] objects;
    private String playerName;
    private boolean isPaused = false;

    //this is for child stage only
    public Chicken(ImageView sprite, AnchorPane scene, ImageView background, Runnable onObjectCollected){
        this.scene = scene;
        this.sprite = sprite;
        this.background = background;
        this.onObjectCollected = onObjectCollected;

        //boundaries
        minX = scene.getLayoutX();
        minY = scene.getLayoutY() + 200;
    }

    //adult & senior stage
    public Chicken(ImageView sprite, AnchorPane scene, ImageView background){
        this.sprite = sprite;
        this.scene = scene;
        this.background = background;
        this.onObjectCollected = null;

        minX = scene.getLayoutX();
        minY = scene.getLayoutY()+scene.getHeight();
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        System.out.println("Player name set.");
        System.out.println("Name: " + playerName);
    }

    //use in Stage 3
    public String getPlayerName(){
        return playerName;
    }

    public boolean getaPressed(){
        return aPressed.get();
    }
    public void setwPressed(boolean bool){
        wPressed.set(bool);
    }
    public void setaPressed(boolean bool){
        aPressed.set(bool);
    }
    public void setsPressed(boolean bool){
        sPressed.set(bool);
    }
    public void setdPressed(boolean bool){
        dPressed.set(bool);
    }

    public void makeMovable(){
        movementSetup();

        keyPressed.addListener(((observableValue, aBoolean, t1) -> {
            if(!aBoolean){
                timer.start();
                System.out.println("Timer started.");
            }
            else
            {
                timer.stop();
            }
        }));
    }

    private void movementSetup() {
        scene.setOnKeyPressed(e -> {
            if (isPaused) return;
            if(e.getCode() == KeyCode.W) {
                wPressed.set(true);
            }

            if(e.getCode() == KeyCode.A) {
                aPressed.set(true);
            }

            if(e.getCode() == KeyCode.S) {
                sPressed.set(true);
            }

            if(e.getCode() == KeyCode.D) {
                dPressed.set(true);
            }
        });

        scene.setOnKeyReleased(e ->{
            if(e.getCode() == KeyCode.W) {
                wPressed.set(false);
            }

            if(e.getCode() == KeyCode.A) {
                aPressed.set(false);
            }

            if(e.getCode() == KeyCode.S) {
                sPressed.set(false);
            }

            if(e.getCode() == KeyCode.D) {
                dPressed.set(false);
            }
        });
    }

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            System.out.println("keys pressed started");
            if (isPaused) return;
            int movementVariable = 3;

            if (onObjectCollected!=null){ //this is spepcial for child stage cz chld stage requires collecting object, and has a diff logic
                double newX = sprite.getLayoutX();
                double newY = sprite.getLayoutY();
    
                if (wPressed.get()) {
                    newY -= movementVariable;
                }
    
                if (sPressed.get()) {
                    newY += movementVariable;
                }
    
                if (aPressed.get()) {
                    newX -= movementVariable;
                }
    
                if (dPressed.get()) {
                    newX += movementVariable;
                }
    
                if (newX >= minX && newX + sprite.getFitWidth() <= background.getFitWidth()) {
                    sprite.setLayoutX(newX);
                }
                if (newY >= minY && newY + sprite.getFitHeight() <= background.getFitHeight() - 200) {
                    sprite.setLayoutY(newY);
                }
                    checkObjectCollection();
            } else {

                if(wPressed.get()) {
                    sprite.setLayoutY(sprite.getLayoutY() - movementVariable);
                }
    
                if(sPressed.get()){
                    sprite.setLayoutY(sprite.getLayoutY() + movementVariable);
                }
    
                if(aPressed.get()){
                    sprite.setLayoutX(sprite.getLayoutX() - movementVariable);
                }
    
                if(dPressed.get()){
                    sprite.setLayoutX(sprite.getLayoutX() + movementVariable);
                }
            }

            
        }
    };

    public void setObjects(ImageView[] objects) {
        this.objects = objects;
    }

    private void checkObjectCollection() {
        if (objects == null || onObjectCollected == null) {
            return;
        }

        for (ImageView object : objects) {
            if (object.isVisible()) {
                Bounds chickenBounds = sprite.getBoundsInParent();
                Bounds objectBounds = object.getBoundsInParent();
                if (chickenBounds.intersects(objectBounds)) {
                    onObjectCollected.run();
                    break;
                }
            }
        }
    }

    public void pauseMovement() {
        isPaused = true;
        timer.stop();
        System.out.println("Movement paused.");
    }

    public void resumeMovement() {
        isPaused = false;
        timer.start();
        System.out.println("Movement paused.");
    }

    public double[] getSpritePosition() {
        return new double[]{sprite.getLayoutX(), sprite.getLayoutY()};
    }

    public void setSpritePosition(double[] position) {
        if (position != null && position.length == 2) {
            sprite.setLayoutX(position[0]);
            sprite.setLayoutY(position[1]);
        }
    }
}