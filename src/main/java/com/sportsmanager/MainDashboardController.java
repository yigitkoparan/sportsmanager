package com.sportsmanager;

import com.sportsmanager.football.FootballLeague;
import com.sportsmanager.football.FootballMatch;
import com.sportsmanager.football.FootballPlayer;
import com.sportsmanager.football.FootballTeam;
import com.sportsmanager.framework.Player;
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

    @FXML private ListView<String> playerList;

    @FXML private TableView<FootballTeam> standings;
    @FXML private TableColumn<FootballTeam, String> teamNames;
    @FXML private TableColumn<FootballTeam, Integer> points;

    private FootballLeague league;
    private FootballTeam userTeam;

    public void initDate(FootballLeague league, FootballTeam userTeam){
        this.league = league;
        this.userTeam = userTeam;

        UserTeamName.setText(userTeam.getTeamName());
        LeagueName.setText(league.getLeagueName());

        playerList.getItems().clear();

        UserTeamName.setText(userTeam.getTeamName());
        for (Player p : userTeam.getPlayers()){
            playerList.getItems().add((p.getName() + " || Overall  =  " + p.getSkillLevel()));
        }

        teamNames.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTeamName())
        );
        points.setCellValueFactory(new PropertyValueFactory<>("points"));
        league.generateFixtureForWeek();
        FootballMatch humanMatch = league.getUserMatch(userTeam);

        league.generateStanding();
        standings.setItems(FXCollections.observableArrayList(league.getTeams()));

        points.setSortType(TableColumn.SortType.DESCENDING);
        standings.getSortOrder().add(points);
        standings.sort();
    }

    @FXML
    private void handleSimulateWeek(ActionEvent event) throws IOException {

        FootballMatch humanMatch = league.getUserMatch(userTeam);

        if (humanMatch != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MatchSimulation.fxml"));
            Parent root = loader.load();

            MatchSimulationController controller = loader.getController();
            controller.initData(this.league, this.userTeam, humanMatch);

            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChampionScreen.fxml"));
            Parent root = loader.load();

            ChampionScreenController controller = loader.getController();
            controller.initData(league,userTeam);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        }
    }

    @FXML
    private void handleSaveAndExit(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Your Football Game");

        fileChooser.setInitialFileName("my_football_career.dat");
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
}
