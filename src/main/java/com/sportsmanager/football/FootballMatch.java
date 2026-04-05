package com.sportsmanager.football;
import com.sportsmanager.framework.Match;
import com.sportsmanager.framework.Team;
import java.util.Random;

public class FootballMatch extends Match{
    private final int gameDuration = 90;
    FootballTeam homeTeam;
    FootballTeam awayTeam;

    public FootballMatch(FootballTeam homeTeam, FootballTeam awayTeam){
        super(0,0);
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    Random rand = new Random();
    @Override
    public void simulate(){
        double homeSkill = homeTeam.calculateTeamSkill();
        double awaySkill = awayTeam.calculateTeamSkill();

        homeScore = rand.nextInt((int)homeSkill) / 2;
        awayScore = rand.nextInt((int)awaySkill) / 2;

        homeTeam.setGamesPlayed(homeTeam.getGamesPlayed()+1);
        awayTeam.setGamesPlayed(awayTeam.getGamesPlayed()+1);

        homeTeam.setGoalsScored(homeTeam.getGoalsScored()+homeScore);
        awayTeam.setGoalsScored(awayTeam.getGoalsScored()+awayScore);

        if(homeScore > awayScore){
            homeTeam.setWins(homeTeam.getWins()+1);
            awayTeam.setLosses(awayTeam.getLosses()+1);
            homeTeam.setPoints(homeTeam.getPoints()+3);
        }else if(homeScore == awayScore){
            homeTeam.setDraw(homeTeam.getDraw()+1);
            awayTeam.setDraw(awayTeam.getDraw()+1);
            homeTeam.setPoints(homeTeam.getPoints()+1);
            awayTeam.setPoints(awayTeam.getPoints()+1);
        }else{
            awayTeam.setWins(awayTeam.getWins()+1);
            homeTeam.setLosses(homeTeam.getLosses()+1);
            awayTeam.setPoints(awayTeam.getPoints()+3);
        }
    }
}
