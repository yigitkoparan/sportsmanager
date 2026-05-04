package com.sportsmanager;

import com.sportsmanager.football.FootballLeague;
import com.sportsmanager.framework.League;
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
    private void handleSelectFootball(ActionEvent event) throws IOException{
        FootballLeague league = new FootballLeague("Süper Lig",1);
        league.generatePlayer();
        league.generateTeam();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TeamSelection.fxml"));
        Parent root = loader.load();

        TeamSelectionController controller = loader.getController();
        controller.initData(league);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
