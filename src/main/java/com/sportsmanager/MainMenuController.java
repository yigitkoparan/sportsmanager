package com.sportsmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private Button btnNewGame;

    @FXML
    private Button btnExitGame;

    @FXML
    private void newGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/SportSelection.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void exitGame(){
        Platform.exit();
    }
}
