package com.sportsmanager.framework;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public abstract class League implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String leagueName;
    protected int currentWeek;
    protected boolean trainingDone = false;


    public boolean isTrainingDone() { return trainingDone; }

    public void setTrainingDone(boolean trainingDone) { this.trainingDone = trainingDone; }

    public int getCurrentWeek() {
        return currentWeek;
    }

    public void setCurrentWeek(int currentWeek) {
        this.currentWeek = currentWeek;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public League(String leagueName,int currentWeek){
        this.leagueName=leagueName;
        this.currentWeek=currentWeek;
    }
    public void advanceWeek(){
        this.currentWeek++;
        trainingDone = false;
    }

    public abstract void generatePlayer();
    public abstract void generateStanding();
    public abstract void generateTeam();
    public abstract void generateFixtureForWeek();
    public abstract Match getUserMatch(Team userTeam);
    public abstract void generateFullSeasonFixture();
    public abstract void simulateRestOfMatches(Team userTeam);

}
