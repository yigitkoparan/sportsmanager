package com.sportsmanager.football;
import com.sportsmanager.framework.Player;
import com.sportsmanager.framework.Tactic;
import com.sportsmanager.framework.Team;

import java.io.Serializable;

public class FootballTeam extends Team implements Serializable {
     private static final long serialVersionUID = 1L;
     int numberOfPlayer;
     int draw;
     int goalsScored;
     int goalsConceded;

     public int getGoalsConceded() {
        return goalsConceded;
    }

     public void setGoalsConceded(int goalsConceded) {
        this.goalsConceded = goalsConceded;
    }

     public int getGoalsScored() {
        return goalsScored;
    }

     public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

     public int getDraw() {
         return draw;
     }

     public void setDraw(int draw) {
         this.draw = draw;
     }


    public FootballTeam(String teamName){
        super(teamName,0);
        this.numberOfPlayer=11;
        this.points=0;
        this.draw=0;
        this.goalsScored=0;
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
        return this.goalsScored-this.goalsConceded;
    }
}

