package com.sportsmanager;

import com.sportsmanager.football.FootballLeague;
import com.sportsmanager.framework.League;
import com.sportsmanager.volleyball.VolleyballLeague;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SportSelectionController {

    @FXML
    private void handleSelectFootball(ActionEvent event) throws IOException {

        FootballLeague league = new FootballLeague("Süper Lig", 1);
        setupAndNavigate(event, league);
    }

    @FXML
    private void handleSelectVolleyball(ActionEvent event) throws IOException {

        VolleyballLeague league = new VolleyballLeague("Sultanlar Ligi", 1);
        setupAndNavigate(event, league);
    }

    private void setupAndNavigate(ActionEvent event, League league) throws IOException {
        
        league.generatePlayer();
        league.generateTeam();
        league.generateFullSeasonFixture();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TeamSelection.fxml"));
        Parent root = loader.load();


        TeamSelectionController controller = loader.getController();
        controller.initData(league);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
    }

    @FXML
    private void handleBackButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainMenu.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }
}

