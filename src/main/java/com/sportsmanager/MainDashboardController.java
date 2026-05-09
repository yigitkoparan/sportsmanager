package com.sportsmanager;

import com.sportsmanager.football.FootballLeague;
import com.sportsmanager.football.FootballMatch;
import com.sportsmanager.football.FootballPlayer;
import com.sportsmanager.football.FootballTeam;
import com.sportsmanager.framework.League;
import com.sportsmanager.framework.Match;
import com.sportsmanager.framework.Player;
import com.sportsmanager.framework.Team;
import com.sportsmanager.volleyball.VolleyballLeague;
import com.sportsmanager.volleyball.VolleyballMatch;
import com.sportsmanager.volleyball.VolleyballTeam;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MainDashboardController {
    @FXML private Label UserTeamName;
    @FXML private Label LeagueName;
    @FXML private Label lblTrainingStatus;

    @FXML private ListView<String> playerList;

    @FXML private TableView<Team> standings;
    @FXML private TableColumn<Team, String> teamNames;
    @FXML private TableColumn<Team, Integer> points;
    @FXML private TableColumn<Team,Integer> average;

    private League league;
    private Team userTeam;

    public void initDate(League league, Team userTeam){
        this.league = league;
        this.userTeam = userTeam;

        UserTeamName.setText(userTeam.getTeamName());
        LeagueName.setText(league.getLeagueName());

        teamNames.setText("Team");
        points.setText("Points");
        average.setText("Average");

        playerList.getItems().clear();

        int starterCount = (league instanceof FootballLeague) ? 11 : 6;

        for (int i = 0; i < userTeam.getPlayers().size(); i++) {
            Player p = userTeam.getPlayers().get(i);

            String role = (i < starterCount) ? "[STARTER]" : "[SUB]";

            String injuryStatus = "";
            if (p.isInjured()) {
                injuryStatus = " (INJURED - " + p.getInjuryDuration() + " matches left)";
            }

            String displayString = String.format("%s %s | Ovr: %d%s",
                    role,
                    p.getName(),
                    p.getSkillLevel(),
                    injuryStatus);

            playerList.getItems().add(displayString);
        }

        teamNames.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTeamName())
        );
        points.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPoints())
        );
        average.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getAverage())
        );

        if (league.isTrainingDone()) {
            lblTrainingStatus.setText("Status: Training Complete");
        }else {
            lblTrainingStatus.setText("Status: Training Available");
        }


        refreshTable();
    }

    public void refreshTable(){
        league.generateStanding();

        if(league instanceof FootballLeague){
            standings.setItems(FXCollections.observableArrayList(((FootballLeague)league).getTeams()));
        } else if (league instanceof VolleyballLeague) {
            standings.setItems(FXCollections.observableArrayList(((VolleyballLeague)league).getTeams()));
        }
    }

    @FXML
    private void handleSimulateWeek(ActionEvent event) throws IOException {
        league.generateFixtureForWeek();
        Match humanMatch = league.getUserMatch(userTeam);

        if (humanMatch != null) {
            String fxmlPath = (league instanceof FootballLeague) ? "/MatchSimulation.fxml" : "/VolleyballMatchSimulation.fxml";

            int starterCount = (league instanceof FootballLeague) ? 11 : 6;
            userTeam.handleAutoSubstitutions(starterCount);

            refreshTable();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();


            if (league instanceof FootballLeague) {
                MatchSimulationController controller = loader.getController();
                controller.initData((FootballLeague) league, (com.sportsmanager.football.FootballTeam) userTeam, (com.sportsmanager.football.FootballMatch) humanMatch);
            }else if (league instanceof VolleyballLeague) {
                VolleyballMatchSimulationController controller = loader.getController();
                controller.initData((VolleyballLeague) league, (VolleyballTeam) userTeam, (VolleyballMatch) humanMatch);
            }


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.show();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChampionScreen.fxml"));
            Parent root = loader.load();

            ChampionScreenController controller = loader.getController();
            controller.initData(this.league, this.userTeam);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.show();
        }
    }

    @FXML
    private void handleSaveAndExit(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Your Football Game");

        fileChooser.setInitialFileName("my_manager_career.dat");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Save Files", "*.dat"));

        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(this.league);
                oos.writeObject(this.userTeam);

                System.out.println("Saved to: " + file.getAbsolutePath());
                javafx.application.Platform.exit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleTraining(ActionEvent event) {
        if (league.isTrainingDone()) {
            System.out.println("Training already completed for this week!");
            return;
        }

        userTeam.trainPlayers();

        league.setTrainingDone(true);

        initDate(league, userTeam);

        System.out.println("Training complete! Players improved.");
    }
}
