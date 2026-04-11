package com.sportsmanager.football;
import com.sportsmanager.framework.Match;
import com.sportsmanager.framework.Tactic;
import com.sportsmanager.framework.Team;
import java.util.Random;

public class FootballMatch extends Match{
    private final int gameDuration = 90;
    FootballTeam homeTeam;
    FootballTeam awayTeam;
    Tactic homeTactic= new Tactic(false,true,false);
    Tactic awayTactic=new Tactic(false,true,false);


    public FootballMatch(FootballTeam homeTeam, FootballTeam awayTeam){
        super(0,0);
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public int getHomeModifier(Tactic homeTeam, Tactic awayTeam){
        int modifier = 0;
        if(homeTeam.isOffensive() && awayTeam.isOffensive()){
            modifier = 4;
        }
        if(homeTeam.isOffensive() && awayTeam.isBalanced()){
            modifier = 2;
        }
        if (homeTeam.isOffensive() && awayTeam.isDefensive()) {
            modifier = 0;
        }
        if(homeTeam.isBalanced() && awayTeam.isDefensive()){
            modifier = -2;
        }
        if(homeTeam.isBalanced() && awayTeam.isBalanced()){
            modifier = 0;
        }
        if (homeTeam.isBalanced() && awayTeam.isOffensive()) {
            modifier = 2;
        }
        if(homeTeam.isDefensive() && awayTeam.isDefensive()){
            modifier = -4;
        }
        if(homeTeam.isDefensive() && awayTeam.isBalanced()){
            modifier = -2;
        }
        if (homeTeam.isDefensive() && awayTeam.isOffensive()) {
            modifier = 0;
        }



        return modifier;
    }

    public int getAwayModifier(Tactic homeTeam, Tactic awayTeam){
        int modifier = 0;
        if(awayTeam.isOffensive() && homeTeam.isOffensive()){
            modifier = 4;
        }if(awayTeam.isOffensive() && homeTeam.isBalanced()){
            modifier = 2;
        }if(awayTeam.isOffensive() && homeTeam.isDefensive()){
            modifier = 0;
        }if(awayTeam.isBalanced() && homeTeam.isOffensive()){
            modifier = 2;
        }if(awayTeam.isBalanced() && homeTeam.isBalanced()){
            modifier = 0;
        }if(awayTeam.isBalanced() && homeTeam.isDefensive()){
            modifier = -2;
        }if(awayTeam.isDefensive() && homeTeam.isOffensive()){
            modifier = 0;
        }if(awayTeam.isDefensive() && homeTeam.isBalanced()){
            modifier = -2;
        }if(awayTeam.isDefensive() && homeTeam.isDefensive()){
            modifier = -4;
        }

        return modifier;

    }

    Random rand = new Random();
    @Override
    public void simulate(){
        double homeSkill = homeTeam.calculateTeamSkill();
        double awaySkill = awayTeam.calculateTeamSkill();

        int homeBound = (int)homeSkill + getHomeModifier(homeTactic, awayTactic);
        int awayBound = (int)awaySkill + getAwayModifier(homeTactic, awayTactic);

        homeScore = rand.nextInt(Math.max(1, homeBound)) / 2;
        awayScore = rand.nextInt(Math.max(1, awayBound)) / 2;

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
