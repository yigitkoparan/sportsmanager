package com.sportsmanager;

import com.sportsmanager.football.FootballLeague;
import com.sportsmanager.football.FootballTeam;
import com.sportsmanager.framework.League;
import com.sportsmanager.framework.Team;
import com.sportsmanager.volleyball.VolleyballLeague;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TeamSelectionController {
    @FXML private ListView<String> teamListView;
    private League league;

    public void initData(League league) {
        this.league = league;
        teamListView.getItems().clear();

        // Get teams via casting to access the specific List type from each league
        List<? extends Team> teams;
        if (league instanceof FootballLeague) {
            teams = ((FootballLeague) league).getTeams();
        } else {
            teams = ((VolleyballLeague) league).getTeams();
        }

        for (Team team : teams) {
            teamListView.getItems().add(team.getTeamName());
        }
    }

    @FXML
    private void handleConfirmSelection(ActionEvent event) throws IOException {
        String selectedName = teamListView.getSelectionModel().getSelectedItem();
        if (selectedName != null) {

            // Find the team object in the polymorphic league
            Team userTeam = null;
            if (league instanceof FootballLeague) {
                userTeam = ((FootballLeague) league).getTeams().stream()
                        .filter(t -> t.getTeamName().equals(selectedName))
                        .findFirst().orElse(null);
            } else if (league instanceof VolleyballLeague) {
                userTeam = ((VolleyballLeague) league).getTeams().stream()
                        .filter(t -> t.getTeamName().equals(selectedName))
                        .findFirst().orElse(null);
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainDashboard.fxml"));
            Parent root = loader.load();

            MainDashboardController controller = loader.getController();
            controller.initDate(league, userTeam);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.show();
        }
    }
}
