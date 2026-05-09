package com.sportsmanager;

import com.sportsmanager.football.FootballLeague;
import com.sportsmanager.football.FootballMatch;
import com.sportsmanager.football.FootballTeam;
import com.sportsmanager.framework.Player;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;

public class MatchSimulationController {
    @FXML private ComboBox<String> cmbStarter;
    @FXML private ComboBox<String> cmbSub;
    @FXML private Label lblTeams;
    @FXML private Label lblMatchDetails;
    @FXML private ComboBox<String> cmbTactics;
    @FXML private Button btnAction;

    private int subsLeft = 2;
    private FootballLeague league;
    private FootballTeam userTeam;
    private FootballMatch activeMatch;

    private int state = 0; // for first half and second half;

    public void initData(FootballLeague league, FootballTeam userTeam, FootballMatch match) {
        this.league = league;
        this.userTeam = userTeam;
        this.activeMatch = match;

        lblTeams.setText(match.getHomeTeam().getTeamName() + " vs " + match.getAwayTeam().getTeamName());
        lblMatchDetails.setText("Score: 0 - 0\nReady to begin the first half.");
        btnAction.setText("Play First Half");

        cmbTactics.getItems().addAll("Offense", "Defense", "Balanced");
        cmbTactics.setValue("Balanced");
        refreshSubstitutionBoxes();
    }

    private void refreshSubstitutionBoxes() {

        cmbStarter.getItems().clear();
        cmbSub.getItems().clear();

        for(int i = 0; i < 11; i++){

            Player p = userTeam.getPlayers().get(i);

            if(!p.isInjured()){
                cmbStarter.getItems().add(p.getName());
            }
        }

        for(int i = 11; i < userTeam.getPlayers().size(); i++){

            Player p = userTeam.getPlayers().get(i);

            if(!p.isInjured()){
                cmbSub.getItems().add(p.getName());
            }
        }
    }

    @FXML
    private void handleManualSubstitution() {
        if (subsLeft <= 0) {
            lblMatchDetails.setText("No substitutions remaining!");
            return;
        }

        String starterName = cmbStarter.getValue();
        String subName = cmbSub.getValue();

        if (starterName == null || subName == null) return;

        Player out = null;
        Player in = null;

        for (Player p : userTeam.getPlayers()) {
            if (p.getName().equals(starterName)) out = p;
            if (p.getName().equals(subName)) in = p;
        }

        if (out != null && in != null) {
            handleSubstitution(out, in);


            lblMatchDetails.setText(String.format("SUBSTITUTION MADE:\n%s OUT\n%s IN\nSubs Remaining: %d",
                    out.getName(), in.getName(), subsLeft));
            cmbStarter.setValue(null);
            cmbSub.setValue(null);

            refreshSubstitutionBoxes();
        }
    }
    @FXML
    private void HandleMatchProgress(ActionEvent event) throws IOException {
        if (state == 0){
            activeMatch.simulateFirstHalf();

            lblMatchDetails.setText("Half-Time Score: " + activeMatch.getHomeScore() + " - " + activeMatch.getAwayScore());
            cmbTactics.setVisible(true);
            btnAction.setText("Apply Tactic & Play 2nd Half");
            state = 1;
            cmbStarter.setVisible(true);
            cmbSub.setVisible(true);
        }else if(state == 1){
            String chosenTacticName = cmbTactics.getValue();
            int modifier = 0;
            if ("Offense".equals(chosenTacticName)) modifier = 5;
            else if ("Defense".equals(chosenTacticName)) modifier = -3;

            userTeam.setTactic(new Tactic(chosenTacticName, modifier));

            activeMatch.simulateSecondHalf();

            lblMatchDetails.setText("Final Score: " + activeMatch.getHomeScore() + " - " + activeMatch.getAwayScore());
            cmbTactics.setVisible(false);
            cmbStarter.setVisible(false);
            cmbSub.setVisible(false);
            btnAction.setText("Return to Dashboard");
            state = 2;
        } else if (state == 2) {
            league.simulateRestOfMatches(userTeam);
            league.advanceWeek();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainDashboard.fxml"));
            Parent root = loader.load();

            MainDashboardController controller = loader.getController();
            controller.initDate(league, userTeam);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        }
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
