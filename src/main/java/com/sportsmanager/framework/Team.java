package com.sportsmanager.framework;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import static java.util.Collections.swap;

public abstract class Team implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String teamName;
    protected int gamesPlayed;
    protected int wins;
    protected int losses;
    protected int points;
    protected List<Player> players;
    protected double teamSkill;
    private Tactic currentTactic;
    protected int subsRemaining = 2;

    public Tactic getTactic(){
        return currentTactic;
    }

    public void setTactic(Tactic tactic){
        this.currentTactic = tactic;
    }

    public int getSubsRemaining() { return subsRemaining; }

    public void setSubsRemaining(int subs) { this.subsRemaining = subs;}

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

    public int getPoints() { return points; }

    public void setPoints(int points) { this.points = points; }

    public Team(String teamName,int gamesPlayed) {
        this.teamName = teamName;
        this.gamesPlayed = gamesPlayed;
        this.wins = 0;
        this.losses = 0;
        this.points = 0;
        this.teamSkill = 0;
        this.players = new ArrayList<>();
        this.teamSkill = calculateTeamSkill();
        this.currentTactic = new Tactic("Balanced",0);
    }

    public abstract double calculateTeamSkill() ;

    public void handleAutoSubstitutions(int starterCount) {
        for (int i = 0; i < starterCount; i++) {
            Player starter = players.get(i);

            if (starter.isInjured()) {
                for (int j = starterCount; j < players.size(); j++) {
                    Player sub = players.get(j);

                    if (!sub.isInjured()) {
                        swap(players, i, j);
                        System.out.println("Auto-Sub: " + sub.getName() + " replaced injured " + starter.getName());
                        break;
                    }
                }
            }
        }
    }

    public void trainPlayers() {
        for (Player p : players) {
            if (p.getSkillLevel() < 12) {
                p.setSkillLevel(p.getSkillLevel() + 1);
            }
        }
    }

    public abstract int getAverage();

}

