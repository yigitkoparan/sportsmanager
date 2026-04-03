package com.sportsmanager.football;
import com.sportsmanager.framework.Player;
import com.sportsmanager.framework.Team;
 class FootballTeam extends Team {
    int numberOfPlayer;
    public FootballTeam(String teamName){
        super(teamName);
        this.numberOfPlayer=11;

    }


     @Override
     public double calculateTeamSkill() {
         double total = 0;
         for (Player p : players) {
             total+=p.getSkillLevel();
         }
         return total/(double)numberOfPlayer;

     }
     }

