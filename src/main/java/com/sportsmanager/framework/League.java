package com.sportsmanager.framework;

import java.util.List;
import java.util.ArrayList;

public abstract class League {
    protected String leagueName;
    protected int currentWeek;





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
    public abstract void generateFixtures();
    public abstract void generateStanding();
    public abstract void generateTeam();



}
