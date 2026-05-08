package com.sportsmanager;

import com.sportsmanager.football.FootballLeague;
import com.sportsmanager.football.FootballTeam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ChampionScreenController {
    @FXML private Label championName;
    @FXML private Label stats;

    private FootballLeague league;
    private FootballTeam userTeam;

    public void initData(FootballLeague league,FootballTeam userTeam){
        this.league = league;
        this.userTeam = userTeam;
        league.generateStanding();

        FootballTeam champion = league.getTeams().get(0);

        championName.setText("CHAMPION: " + champion.getTeamName() + "!");
        stats.setText("Points: " + champion.getPoints() +
                      "\nWins: " + champion.getWins() +
                      "\nGoals: " + champion.getGoalsScored());
    }

    @FXML
    private void handleNextSeason(ActionEvent event) throws Exception {
        league.setCurrentWeek(1);

        for(FootballTeam team : league.getTeams()){
            team.setPoints(0);
            team.setWins(0);
            team.setLosses(0);
            team.setDraw(0);
            team.setGoalsScored(0);
            team.setGamesPlayed(0);
        }

        league.generateFullSeasonFixture();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainDashboard.fxml"));
        Parent root = loader.load();

        MainDashboardController controller = loader.getController();
        controller.initDate(this.league, this.userTeam);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
