
package com.example.game;

import java.io.File;

public class GameStateManager {
    private static final String SAVE_FILE = "gamestate.dat";

    public static void deleteGameState() {
        File file = new File(SAVE_FILE);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Saved game state deleted.");
            } else {
                System.out.println("Failed to delete saved game state.");
            }
        } else {
            System.out.println("No saved game state to delete.");
        }
    }
}

