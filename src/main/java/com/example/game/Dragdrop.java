package com.example.game;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.Map;

public class Dragdrop {
    @FXML
    private ImageView pic;

    @FXML
    private ImageView album;

    @FXML
    private Label msg;

    private Map<ImageView, ImageView> imageToAlbumMap = new HashMap<>();
    private Map<ImageView, Double[]> originalPositions = new HashMap<>();

    private final double ALBUM_WIDTH = 100;
    private final double ALBUM_HEIGHT = 100;

    public static boolean hasCompleted = false;

    //for senior
    @FXML
    public void initializeAlbum(ImageView pic, ImageView album) {
        setupImageView(pic);
        setupAlbum(album);
        imageToAlbumMap.put(pic, album);
        savePosition(pic);
        setAlbumSize(album);
    }
    //for adult
    @FXML
    public void initializeFolder(ImageView pic, ImageView album) {
        setupImageView(pic);
        setupFolder(album);
        imageToAlbumMap.put(pic, album);
        savePosition(pic);
        setAlbumSize(album);
    }

    public void initializeMessage(Label msg) {
        this.msg = msg;
    }

    private void savePosition(ImageView imageView) {
        originalPositions.put(imageView, new Double[]{imageView.getLayoutX(), imageView.getLayoutY()});
    }

    private void setAlbumSize(ImageView album) {
        album.setFitWidth(ALBUM_WIDTH);
        album.setFitHeight(ALBUM_HEIGHT);
    }

    private void setupImageView(ImageView imageView) {
        imageView.setOnDragDetected(event -> {
            Dragboard db = imageView.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putImage(imageView.getImage());
            db.setContent(content);
            event.consume();
        });
        imageView.setFitWidth(ALBUM_WIDTH);
        imageView.setFitHeight(ALBUM_HEIGHT);
    }
    //for senior: change album image
    private void setupAlbum(ImageView album) {
        album.setOnDragOver(event -> {
            if (event.getGestureSource() != album && event.getDragboard().hasImage()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        album.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasImage()) {
                ImageView draggedImageView = (ImageView) event.getGestureSource();
                if (imageToAlbumMap.get(draggedImageView) == album) {
                    Image droppedImage = db.getImage();
                    album.setImage(droppedImage);
                    album.setFitWidth(ALBUM_WIDTH);
                    album.setFitHeight(ALBUM_HEIGHT);
                    draggedImageView.setVisible(false);
                    success = true;
                    if (isCurCompleted()) {
                        showCompletionMessage();
                    }
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    //for adult: doesnt change album image
    private void setupFolder(ImageView album) {
        album.setOnDragOver(event -> {
            if (event.getGestureSource() != album && event.getDragboard().hasImage()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        album.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasImage()) {
                ImageView draggedImageView = (ImageView) event.getGestureSource();
                if (imageToAlbumMap.get(draggedImageView) == album) {
                    draggedImageView.setVisible(false);
                    success = true;
                    if (isCurCompleted()) {
                        showCompletionMessage();
                        hasCompleted();
                        System.out.println("msg: " + msg);
                    }
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    private void revertPosition(ImageView imageView) {
        Double[] position = originalPositions.get(imageView);
        imageView.setLayoutX(position[0]);
        imageView.setLayoutY(position[1]);
    }

    @FXML
    protected void handleDragDone(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        if (imageView.isVisible()) {
            revertPosition(imageView);
        }
    }
    //change to public
    public boolean isCurCompleted() {
        for (Map.Entry<ImageView, ImageView> entry : this.imageToAlbumMap.entrySet()) {
            if (entry.getKey().isVisible()) {
                return false;
            }
        }
        return true;
    }

    public boolean hasCompleted(){
        return hasCompleted;
    }

    private void showCompletionMessage() {
        // Display a "Congratulations" message on the current window
        msg.setVisible(true);
        hasCompleted=true;
    }
}