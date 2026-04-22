package com.sportsmanager;

import com.sportsmanager.football.FootballLeague;
import com.sportsmanager.football.FootballMatch;
import com.sportsmanager.football.FootballTeam;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;



public class MainApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));

        primaryStage.setTitle("Sports Manager - Milestone 3");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
}