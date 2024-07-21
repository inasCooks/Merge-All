package com.example.game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class MainMenuCont {

    private ChildCont childCont;
    private ChildStage childStage;

    @FXML
    public Button startButton;
    @FXML
    public Button continueButton;
    @FXML
    private Button exitButton;

    //start new game
    @FXML
    protected void handleStartButtonAction(ActionEvent event) {
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        childStage = new ChildStage(mainStage);
        childStage.show();
    }

    //load saved game
    @FXML
    protected void handleContinueButtonAction(ActionEvent event) {
        // try {
        //     // FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("child-stage.fxml"));
        //     // Parent newSceneRoot = fxmlLoader.load();
        //     // fxmlLoader.setController(childCont);
        //     // // ChildCont childCont = fxmlLoader.getController();
        //     // childStage.initChildStage();
        //     GameState gameState = loadGameState();

        //     if (gameState != null){
        //         childCont.initGameState(gameState);
        //     }
        //     else{
        //         System.out.println("No saved game found.");
        //         showNoSaveGame();
        //         return;
        //     }

        //     Scene newScene = new Scene(newSceneRoot);
        //     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //     stage.setScene(newScene);
        //     stage.setTitle("Stage 1");
        //     newScene.getRoot().requestFocus();
        // }
        // catch (IOException e) {
        //     System.out.println("Error in handleContinueButtonAction().");
        // }
    }

    //load game state
    private GameState loadGameState() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("gamestate.dat"))) {
            return (GameState) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading game state.");
            return null;
        }
    }

    //close window
    @FXML
    protected void handleExitButtonAction() {
        Stage primaryStage = (Stage) exitButton.getScene().getWindow();
        primaryStage.close();
    }

    private void showNoSaveGame() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("No Save File");
        alert.setHeaderText(null);
        alert.setContentText("No saved game found.");
        alert.showAndWait();
    }
}
