package com.sportsmanager.framework;

import java.util.List;
import java.util.ArrayList;

public abstract class Team {
    protected String teamName;
    protected int wins;
    protected int losses;
    protected List<Player> players;
    protected double teamSkill;

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }

    public Team(String teamName) {
        this.teamName = teamName;
        this.wins = 0;
        this.losses = 0;
        this.players = new ArrayList<>();

    }

    public abstract double calculateTeamSkill() ;


}

