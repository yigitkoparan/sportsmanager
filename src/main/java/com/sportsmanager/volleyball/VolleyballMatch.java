package com.sportsmanager.volleyball;
import com.sportsmanager.football.FootballTeam;
import com.sportsmanager.framework.Match;
import com.sportsmanager.framework.Tactic;
import com.sportsmanager.framework.Team;
import java.util.Random;
import java.util.ArrayList;

public class VolleyballMatch extends Match {
    VolleyballTeam homeTeam;
    VolleyballTeam awayTeam;
    int homeTeamSet;
    int awayTeamSet;
    private int scoreTarget;
    private int ovetTimeScoreTarget;
    private ArrayList<String> sets = new ArrayList<>();
    Tactic homeTactic = new Tactic(false, true, false);
    Tactic awayTactic = new Tactic(false, true, false);

    public VolleyballMatch(VolleyballTeam homeTeam, VolleyballTeam awayTeam) {
        super(0, 0);
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamSet = 0;
        this.awayTeamSet = 0;
    }

    public int getHomeModifier(Tactic homeTeam, Tactic awayTeam) {
        int modifier = 0;
        if (homeTeam.isOffensive() && awayTeam.isOffensive()) {
            modifier = 4;
        }
        if (homeTeam.isOffensive() && awayTeam.isBalanced()) {
            modifier = 2;
        }
        if (homeTeam.isOffensive() && awayTeam.isDefensive()) {
            modifier = 0;
        }
        if (homeTeam.isBalanced() && awayTeam.isDefensive()) {
            modifier = -2;
        }
        if (homeTeam.isBalanced() && awayTeam.isBalanced()) {
            modifier = 0;
        }
        if (homeTeam.isBalanced() && awayTeam.isOffensive()) {
            modifier = 2;
        }
        if (homeTeam.isDefensive() && awayTeam.isDefensive()) {
            modifier = -4;
        }
        if (homeTeam.isDefensive() && awayTeam.isBalanced()) {
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
    public void simulate() {
        double homeSkill = homeTeam.calculateTeamSkill();
        double awaySkill = awayTeam.calculateTeamSkill();
       if(homeTeamSet!=2 && awayTeamSet!=2){
        while(homeScore<scoreTarget && awayScore<scoreTarget){
            int homeBound = (int)homeSkill + getHomeModifier(homeTactic, awayTactic);
            int awayBound = (int)awaySkill + getAwayModifier(homeTactic, awayTactic);

            int  homeProbability = rand.nextInt(Math.max(1, homeBound)) / 2;
            int awayProbability = rand.nextInt(Math.max(1, awayBound)) / 2;
            if(homeProbability>awayProbability){
                homeScore++;
            }
            else if(awayProbability>homeProbability){
                awayScore++;
            }
            if(homeScore==24 && awayScore==24){
                while(Math.abs(homeScore-awayScore)<2 ){
                     homeBound = (int)homeSkill + getHomeModifier(homeTactic, awayTactic);
                     awayBound = (int)awaySkill + getAwayModifier(homeTactic, awayTactic);

                      homeProbability = rand.nextInt(Math.max(1, homeBound)) / 2;
                     awayProbability = rand.nextInt(Math.max(1, awayBound)) / 2;
                    if(homeProbability>awayProbability){
                        homeScore++;
                    }
                    else if(awayProbability>homeProbability){
                        awayScore++;
                    }

                }
            }

        }
        if(homeScore>awayScore){
            homeTeamSet++;
            homeTeam.setWinSet(homeTeam.getWinSet()+1);
            awayTeam.setLoseSet(awayTeam.getLoseSet()+1);

        }
        if(awayScore>homeScore){
            awayTeamSet++;
            awayTeam.setWinSet(awayTeam.getWinSet()+1);
            homeTeam.setLoseSet(homeTeam.getLoseSet()+1);
        }
           homeTeam.setPointScored(homeTeam.getPointScored()+homeScore);
           homeTeam.setOpponentPoint(homeTeam.getOpponentPoint()+awayScore);
           awayTeam.setPointScored(awayTeam.getPointScored()+awayScore);
           awayTeam.setOpponentPoint(awayTeam.getOpponentPoint()+homeScore);

       }
       if(homeTeamSet==3&&awayTeamSet==1){
           homeTeam.setPoints(homeTeam.getPoints()+3);
       }
        if(awayTeamSet==3&&homeTeamSet==1){
            awayTeam.setPoints(awayTeam.getPoints()+3);
        }
        if(homeTeamSet==3&&awayTeamSet==0){
            homeTeam.setPoints(homeTeam.getPoints()+3);
        }
        if(awayTeamSet==3&&homeTeamSet==0){
            awayTeam.setPoints(awayTeam.getPoints()+3);
        }




    }


}

