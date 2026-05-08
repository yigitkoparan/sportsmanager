package com.sportsmanager;

import com.sportsmanager.football.FootballLeague;
import com.sportsmanager.football.FootballTeam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

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
    private void handleLoadGame(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Your Save File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Save Files", "*.dat"));

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object loadedLeague = ois.readObject();
                Object loadedUserTeam = ois.readObject();
                
                if (loadedLeague instanceof FootballLeague) {
                    loadFootball(event, (FootballLeague) loadedLeague, (FootballTeam) loadedUserTeam);
                }else{

                }
            } catch (Exception e) {
                System.err.println("Error loading file: " + e.getMessage());
            }
        }
    }

    private void loadFootball(ActionEvent event, FootballLeague league, FootballTeam team) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainDashboard.fxml"));
        Parent root = loader.load();

        MainDashboardController controller = loader.getController();
        controller.initDate(league, team);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }


    @FXML
    private void exitGame(){
        Platform.exit();
    }
}
