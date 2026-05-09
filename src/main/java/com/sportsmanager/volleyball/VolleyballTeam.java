package com.sportsmanager.volleyball;
import com.sportsmanager.framework.Tactic;
import com.sportsmanager.framework.Player;
import com.sportsmanager.framework.Team;

import java.io.Serializable;

public class VolleyballTeam extends Team implements Serializable {
    private static final long serialVersionUID = 1L;
    int numberOfPlayer;
    int pointScored;
    int opponentPoint;
    int winSet;
    int loseSet;
    private Tactic currentTactic;


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


    public VolleyballTeam(String teamName){
        super(teamName,0);
        this.numberOfPlayer=6;
        this.points=0;
        this.loseSet=0;
        this.pointScored=0;
        this.opponentPoint=0;
        this.winSet=0;
        this.currentTactic = new Tactic("Balanced",0);
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

    @Override
    public int getAverage() {
        return this.winSet-this.loseSet;
    }
}
