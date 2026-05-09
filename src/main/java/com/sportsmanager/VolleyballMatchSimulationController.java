package com.sportsmanager;

import com.sportsmanager.framework.Player;
import com.sportsmanager.volleyball.VolleyballLeague;
import com.sportsmanager.volleyball.VolleyballMatch;
import com.sportsmanager.volleyball.VolleyballTeam;
import com.sportsmanager.framework.Tactic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;

public class VolleyballMatchSimulationController {
    @FXML private Label lblScore;
    @FXML private ListView<String> setList;
    @FXML private ComboBox<String> cmbTactics;
    @FXML private Button btnAction;
    @FXML private Label matchHeader;

    private int subsLeft = 2;
    private VolleyballLeague league;
    private VolleyballMatch match;
    private VolleyballTeam userTeam;


    public void initData(VolleyballLeague league, VolleyballTeam userTeam, VolleyballMatch match) {
        this.league = league;
        this.userTeam = userTeam;
        this.match = match;

        matchHeader.setText(match.getHomeTeam().getTeamName()+" vs "+match.getAwayTeam().getTeamName());
        cmbTactics.getItems().addAll("Balanced", "Offense", "Defense");
        cmbTactics.setValue("Balanced");


        lblScore.setText("0 - 0");
        btnAction.setText("Play First Set");
    }

    @FXML
    private void handleMatchProgress(ActionEvent event) throws IOException {
        if (match.isMatchFinished()) {
            returnToDashboard(event);
            return;
        }

        applyTactic();
        match.playNextSet();
        updateUI();

        if (match.isMatchFinished()) {
            btnAction.setText("Match Over - Return to Dashboard");
            cmbTactics.setVisible(false);
        } else {

            btnAction.setText("Play Next Set (" + (match.getSets().size() + 1) + ")");
        }
    }

    private void applyTactic() {
        String selected = cmbTactics.getValue();
        int mod = selected.equals("Offense") ? 5 : (selected.equals("Defense") ? -3 : 0);
        userTeam.setTactic(new Tactic(selected, mod));
    }

    private void updateUI() {
        setList.getItems().setAll(match.getSets());
        lblScore.setText(match.getHomeTeamSet() + " - " + match.getAwayTeamSet());
    }

    private void returnToDashboard(ActionEvent event) throws IOException {
        league.simulateRestOfMatches(userTeam);
        league.advanceWeek();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainDashboard.fxml"));
        Parent root = loader.load();

        MainDashboardController controller = loader.getController();
        controller.initDate(this.league, this.userTeam);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void handleSubstitution(Player out, Player in) {
        if (subsLeft > 0 && !in.isInjured()) {
            Collections.swap(userTeam.getPlayers(),
                    userTeam.getPlayers().indexOf(out),
                    userTeam.getPlayers().indexOf(in));
            subsLeft--;
            System.out.println("Substitution successful. Subs remaining: " + subsLeft);
        }
    }
}