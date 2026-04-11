package com.sportsmanager.football;
import com.sportsmanager.framework.Player;
import com.sportsmanager.framework.Team;

public class FootballTeam extends Team {
     int numberOfPlayer;
     int draw;
     int points;
     int goalsScored;

    public int getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }


    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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
}

