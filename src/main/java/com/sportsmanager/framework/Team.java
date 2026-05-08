package com.sportsmanager.framework;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public abstract class Team implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String teamName;
    protected int gamesPlayed;
    protected int wins;
    protected int losses;
    protected List<Player> players;
    protected double teamSkill;
    private Tactic currentTactic;

    public Tactic getTactic(){
        return currentTactic;
    }

    public void setTactic(Tactic tactic){
        this.currentTactic = tactic;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public Team(String teamName,int gamesPlayed) {
        this.teamName = teamName;
        this.gamesPlayed = gamesPlayed;
        this.wins = 0;
        this.losses = 0;
        this.players = new ArrayList<>();
        this.teamSkill = calculateTeamSkill();
        this.currentTactic = new Tactic("Balanced",0);
    }

    public abstract double calculateTeamSkill() ;


}

