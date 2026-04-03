package com.sportsmanager.football;
import com.sportsmanager.framework.League;

import java.util.List;

public class FootballLeague extends League {
    protected int teamNumber;
    protected List<FootballTeam> teams;

    public  FootballLeague(String leagueName,int currentWeek){
        super(leagueName,currentWeek);
        this.teamNumber=20;

    }

    @Override
    public void generateTeam() {
        for(int i = 0; i < teamNumber; i++){
            teams.add(new FootballTeam("team"+i));
        }
    }

    @Override
    public void generateFixtures() {
       generateTeam();

    }

    @Override
    public void generateStanding() {
        if(teams == null || teams.isEmpty()){
            return;
        }

        teams.sort((t1, t2) -> Integer.compare(t2.getPoints(), t1.getPoints()));
    }

}
