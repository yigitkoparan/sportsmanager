package com.sportsmanager;

import com.sportsmanager.football.FootballLeague;
import com.sportsmanager.football.FootballMatch;
import com.sportsmanager.football.FootballTeam;

public class App {
    public static void main(String[] args) {
        System.out.println("--- Sports Manager: Football Simulation ---");


        FootballLeague league = new FootballLeague("Süper Lig", 1);

        league.generatePlayer();
        league.generateTeam();
        System.out.println("League '" + league.getLeagueName() + "' created with " + league.getTeams().size() + " teams.");


        FootballTeam home = league.getTeams().get(0);
        FootballTeam away = league.getTeams().get(1);

        System.out.println("\nUpcoming Match: " + home.getTeamName() + " vs " + away.getTeamName());
        System.out.println("Home Team Skill: " + String.format("%.2f", home.calculateTeamSkill()));
        System.out.println("Away Team Skill: " + String.format("%.2f", away.calculateTeamSkill()));


        FootballMatch match = new FootballMatch(home, away);
        match.simulate();


        System.out.println("\n--- Match Result ---");
        System.out.println(home.getTeamName() + " " + match.getHomeScore() + " - " + match.getAwayScore() + " " + away.getTeamName());


        league.generateStanding();
        System.out.println("\n--- Updated Standings ---");
        System.out.println("1. " + league.getTeams().get(0).getTeamName() + " | Points: " + league.getTeams().get(0).getPoints() + " | Goals Scored : "
        + league.getTeams().get(0).getGoalsScored());
        System.out.println("2. " + league.getTeams().get(1).getTeamName() + " | Points: " + league.getTeams().get(1).getPoints() + " | Goals Scored : "
        + league.getTeams().get(1).getGoalsScored());
    }
}