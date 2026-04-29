package com.sportsmanager.volleyball;

import com.sportsmanager.framework.Player;
import com.sportsmanager.framework.Team;

public class VolleyballTeam extends Team {
    int numberOfPlayer;
    int points;
    int pointScored;
    int opponentPoint;
    int winSet;
    int loseSet;

    public int getOpponentPoint() {
        return opponentPoint;
    }

    public void setOpponentPoint(int opponentPoint) {
        this.opponentPoint = opponentPoint;
    }


    public int getLoseSet() {
        return loseSet;
    }

    public void setLoseSet(int loseSet) {
        this.loseSet = loseSet;
    }


    public int getWinSet() {
        return winSet;
    }

    public void setWinSet(int winSet) {
        this.winSet = winSet;
    }


    public int getPointScored() {
        return pointScored;
    }

    public void setPointScored(int pointScored) {
        this.pointScored = pointScored;
    }


    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }



    public VolleyballTeam(String teamName){
        super(teamName,0);
        this.numberOfPlayer=6;
        this.points=0;
        this.loseSet=0;
        this.pointScored=0;
        this.opponentPoint=0;
        this.winSet=0;
    }

    @Override
    public double calculateTeamSkill() {
        double total = 0;
        int counter = 0;
        for (Player p : players) {
            total+=p.getSkillLevel();
            counter++;
        }
        return total/(double)counter;

    }

}
