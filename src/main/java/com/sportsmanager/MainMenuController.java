package com.sportsmanager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.application.Platform;

public class MainMenuController {

    @FXML
    private Button btnNewGame;

    @FXML
    private Button btnExitGame;

    @FXML
    private void newGame(){
        System.out.println("Generating League");
    }

    @FXML
    private void exitGame(){
        Platform.exit();
    }
}
