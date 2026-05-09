package com.sportsmanager;

import com.sportsmanager.football.FootballLeague;
import com.sportsmanager.framework.League;
import com.sportsmanager.framework.Team;
import com.sportsmanager.volleyball.VolleyballLeague;
import com.sportsmanager.volleyball.VolleyballTeam;
import com.sportsmanager.football.FootballTeam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.util.List;

public class ChampionScreenController {
    @FXML private Label championName;
    @FXML private Label stats;

    private League league;
    private Team userTeam;

    public void initData(League league, Team userTeam) {
        this.league = league;
        this.userTeam = userTeam;

        league.generateStanding();

        Team champion = null;
        if (league instanceof com.sportsmanager.football.FootballLeague) {
            champion = ((com.sportsmanager.football.FootballLeague) league).getTeams().get(0);
        } else if (league instanceof com.sportsmanager.volleyball.VolleyballLeague) {
            champion = ((com.sportsmanager.volleyball.VolleyballLeague) league).getTeams().get(0);
        }

        if (champion != null) {
            championName.setText("CHAMPION: " + champion.getTeamName() + "!");

            if (champion instanceof FootballTeam) {
                FootballTeam ft = (FootballTeam) champion;
                stats.setText("Points: " + ft.getPoints() +
                        "\nWins: " + ft.getWins() +
                        "\nGoals: " + ft.getGoalsScored());
            } else if (champion instanceof VolleyballTeam) {
                VolleyballTeam vt = (VolleyballTeam) champion;
                stats.setText("Points: " + vt.getPoints() +
                        "\nSets Won: " + vt.getWinSet() +
                        "\nSets Lost: " + vt.getLoseSet());
            }
        }
    }

    @FXML
    private void handleNextSeason(ActionEvent event) throws Exception {
        league.setCurrentWeek(1);

        resetLeagueTeams();

        league.generateFullSeasonFixture();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainDashboard.fxml"));
        Parent root = loader.load();

        MainDashboardController controller = loader.getController();
        controller.initDate(this.league, this.userTeam);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    private void resetLeagueTeams() {
        List<? extends Team> allTeams;

        if (league instanceof FootballLeague) {
            allTeams = ((FootballLeague) league).getTeams();
        } else {
            allTeams = ((VolleyballLeague) league).getTeams();
        }

        for (Team t : allTeams) {
            t.setPoints(0);
            t.setWins(0);
            t.setLosses(0);
            t.setGamesPlayed(0);

            if (t instanceof FootballTeam) {
                FootballTeam ft = (FootballTeam) t;
                ft.setDraw(0);
                ft.setGoalsScored(0);
            } else if (t instanceof VolleyballTeam) {
                VolleyballTeam vt = (VolleyballTeam) t;
                vt.setWinSet(0);
                vt.setLoseSet(0);
            }
        }
    }
}