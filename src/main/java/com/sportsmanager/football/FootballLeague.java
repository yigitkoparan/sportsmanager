package com.sportsmanager.football;
import com.sportsmanager.framework.League;

import java.util.List;
import java.util.Random;

public class FootballLeague extends League {
    protected int teamNumber;
    private List<FootballTeam> teams;
    private List<FootballPlayer> players;

    public List<FootballTeam> getTeams() {
        return teams;
    }

    public void setTeams(List<FootballTeam> teams) {
        this.teams = teams;
    }

    public List<FootballPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<FootballPlayer> players) {
        this.players = players;
    }


    Random rand = new Random();

    public  FootballLeague(String leagueName,int currentWeek){
        super(leagueName,currentWeek);
        this.teamNumber=20;

        this.teams = new java.util.ArrayList<>();
        this.players = new java.util.ArrayList<>();
    }

    @Override
    public void generateTeam() {
        for(int i = 0; i < teamNumber; i++){
            teams.add(new FootballTeam("team"+i));
            for(int j = 0; j < 11; j++){
                teams.get(i).addPlayer(players.get(i+j));
            }
        }
    }

    @Override
    public void generatePlayer() {
        for(int i = 0; i < teamNumber; i++){
            for(int j = 0; j < 11; j++){
                players.add(new FootballPlayer("player"+i+j, rand.nextInt(15)+20));
            }
        }
    }

    @Override
    public void generateFixtures() {
       generateTeam();

    }

    @Override
    public void generateStanding() {
        if(teams == null || teams.isEmpty()){
            return;
        }

        teams.sort((t1, t2) -> Integer.compare(t2.getPoints(), t1.getPoints()));
    }

}
