package com.example.game;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.Optional;

public class ChildCont {

    //scene setup
    @FXML
    private AnchorPane scene;
    @FXML
    public ImageView background;
    @FXML
    private ImageView chickenSprite;

    //object setup
    @FXML
    private ImageView object1,object2,object3,object4,object5;
    
    @FXML
    private Label progressLabel;

    //pause
    @FXML
    private AnchorPane pauseOverlay;
    public Button pauseButton, resumeButton, saveButton, returnButton;

    private ChickenChild chickenChild;
    private ImageView[] objects;
    private int currentObjectIndex = 0;
    private String playerName;
    private boolean isPaused = false;
    private boolean isNewGame = true;
    private boolean isGameStateLoaded = false;

    //general game setup
    @FXML
    public void initialize() {

        System.out.println("ChildController initialized.");
        System.out.println("isNewGame: " + isNewGame);
        System.out.println("isGameStateLoaded: " + isGameStateLoaded);
        objects = new ImageView[]{object1, object2, object3, object4, object5};
        chickenChild = new ChickenChild(chickenSprite, scene, background, this::onObjectCollected);
        chickenChild.setObjects(objects);
        System.out.println("objects " + objects);
        
        for (int i = 1; i < objects.length; i++) {
            objects[i].setVisible(false);
        }

        updateProgress();
        scene.requestFocus();

    }

    @FXML
    void clickNPC(){
        System.out.println("meow");
    }

    //setup for new game
    public void startNewGame(){
        System.out.println("Starting New Game.");
        openNamePrompt();
        showStart();
        chickenChild.setPlayerName(playerName);
        chickenChild.makeMovable();
        System.out.println("chicken makemovable called from startNewGame");
    }

    //setup for saved game
    public void initGameState(GameState gameState) {
        if (gameState != null) {
            isNewGame = false;
            isGameStateLoaded = true;
            playerName = gameState.getPlayerName();
            currentObjectIndex = gameState.getCurrentObjectIndex();
            chickenChild.setSpritePosition(gameState.getChickenPosition());
            chickenChild.setPlayerName(playerName);

            System.out.println("Loading game state: " + playerName);

            for (int i = 0; i < objects.length; i++) {
                objects[i].setVisible(i == currentObjectIndex);
            }

            chickenChild.makeMovable();
            updateProgress();
        }

    }

    //player name prompt
    private void openNamePrompt() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Player Name");
        dialog.setHeaderText("Enter Your Name:");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> playerName = name);
    }

    //change the visibility of the objects
    public void onObjectCollected() {
        System.out.println("Object Collected: " + currentObjectIndex);
        objects[currentObjectIndex].setVisible(false);
        currentObjectIndex++;

        if (currentObjectIndex < objects.length ) {
            objects[currentObjectIndex].setVisible(true);
        }

        //update index when object is picked up
        updateProgress();

        //indication for stage cleared
        if (currentObjectIndex == objects.length ) {
            showStageClearedPopup();
        }
    }

    //update index text
    private void updateProgress() {
        progressLabel.setText("Objects Collected: " + currentObjectIndex + "/" + objects.length);
    }

    //stage start
    private void showStart() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Game Start!");
        alert.setHeaderText(null);
        alert.setContentText("A distressed young chick has broken its toy! Pick up the pieces to fix them!");
        alert.showAndWait();
    }

    //stage clear
    private void showStageClearedPopup() {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Stage Clear!");
            alert.setHeaderText(null);
            alert.setContentText("You manage to fix the toy and give it back to the chick. It thanks you happily.");
            alert.showAndWait();
        });
    }

    //pause button
    @FXML
    private void handlePauseButtonAction() {
        if (isPaused) {
            resumeGame();
        }
        else {
            pauseGame();
        }
    }

    //pause the game
    private void pauseGame() {
        isPaused = true;
        pauseOverlay.setVisible(true);
        chickenChild.pauseMovement();
    }

    //resume the game
    @FXML
    private void resumeGame() {
        isPaused = false;
        pauseOverlay.setVisible(false);
        chickenChild.resumeMovement();
        scene.requestFocus();
    }

    //save the game
    @FXML
    private void handleSaveButtonAction(){
        saveGameState();
    }

    private void saveGameState() {
        GameState gameState = new GameState(chickenChild.getPlayerName(), currentObjectIndex, chickenChild.getSpritePosition());

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("gamestate.dat"))) {
            oos.writeObject(gameState);
            System.out.println("Game state saved.");
            showSaveConfirmation();
        }
        catch (IOException e) {
            System.out.println("Error saving game state.");
        }
    }

    //save confirmation
    private void showSaveConfirmation() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Game Saved");
        alert.setHeaderText(null);
        alert.setContentText("Your game has been saved successfully.");
        alert.showAndWait();
    }

    //return main menu
    @FXML
    private void returnToMainMenu() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent mainMenuRoot = fxmlLoader.load();
            Scene mainMenuScene = new Scene(mainMenuRoot);
            Stage stage = (Stage) scene.getScene().getWindow();
            resetGameState();
            System.out.println("Returning to main menu.");
            stage.setScene(mainMenuScene);
            stage.setTitle("The Chicken Game");
        }
        catch (IOException e) {
            System.out.println("Error in returnToMainMenu().");
        }
    }

    public void resetGameState() {
        System.out.println("Resetting game state.");
        playerName = null;
        currentObjectIndex = 0;
        chickenChild.setSpritePosition(new double[]{0, 0});
        isNewGame = true;
        for (ImageView object : objects) {
            object.setVisible(false);
        }
        objects[0].setVisible(true);
        updateProgress();
    }
}

