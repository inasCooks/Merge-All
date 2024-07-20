package com.example.game;

import java.io.Serializable;

public class GameState implements Serializable {
    private final String playerName;
    private final int currentObjectIndex;
    private final double[] chickenPosition;

    public GameState(String playerName, int currentObjectIndex, double[] chickenPosition) {
        this.playerName = playerName;
        this.currentObjectIndex = currentObjectIndex;
        this.chickenPosition = chickenPosition;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getCurrentObjectIndex() {
        return currentObjectIndex;
    }

    public double[] getChickenPosition() {
        return chickenPosition;
    }
}