package com.sportsmanager.football;
import com.sportsmanager.framework.Match;
import com.sportsmanager.framework.Tactic;
import com.sportsmanager.framework.Team;
import java.util.Random;

public class FootballMatch extends Match{
    private final int gameDuration = 90;
    FootballTeam homeTeam;
    FootballTeam awayTeam;
    private boolean isFinished;

    public FootballTeam getHomeTeam() { return homeTeam; }
    public FootballTeam getAwayTeam() { return awayTeam; }
    public boolean isFinished() { return isFinished; }

    public FootballMatch(FootballTeam homeTeam, FootballTeam awayTeam){
        super(0,0);
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.isFinished = false;
    }

    public void simulateFirstHalf(){
        double homeAttack = homeTeam.calculateTeamSkill() + homeTeam.getTactic().getModifier();
        double awayAttack = awayTeam.calculateTeamSkill() + awayTeam.getTactic().getModifier();

        this.homeScore += (int) (Math.random() * (homeAttack / 20.0 + 1));
        this.awayScore += (int) (Math.random() * (awayAttack / 20.0 + 1));
    }

    public void simulateSecondHalf() {
        double homeAttack = homeTeam.calculateTeamSkill() + homeTeam.getTactic().getModifier();
        double awayAttack = awayTeam.calculateTeamSkill() + awayTeam.getTactic().getModifier();

        this.homeScore += (int) (Math.random() * (homeAttack / 20.0 + 1));
        this.awayScore += (int) (Math.random() * (awayAttack / 20.0 + 1));

        this.isFinished = true;
        resolveMatchResults();
    }

    private void resolveMatchResults() {
        if (homeScore > awayScore) {
            homeTeam.setWins(homeTeam.getWins() + 1);
            homeTeam.setPoints(homeTeam.getPoints() + 3);
            awayTeam.setLosses(awayTeam.getLosses()+1);
        } else if (awayScore > homeScore) {
            awayTeam.setWins(awayTeam.getWins() + 1);
            awayTeam.setPoints(awayTeam.getPoints() + 3);
            homeTeam.setLosses(homeTeam.getLosses()+1);
        } else {
            homeTeam.setDraw(homeTeam.getDraw() + 1);
            awayTeam.setDraw(awayTeam.getDraw() + 1);
            homeTeam.setPoints(homeTeam.getPoints() + 1);
            awayTeam.setPoints(awayTeam.getPoints() + 1);
        }

        homeTeam.setGoalsScored(homeTeam.getGoalsScored() + homeScore);
        awayTeam.setGoalsScored(awayTeam.getGoalsScored() + awayScore);

    }

    private int getTacticModifier(String tactic){
        if (tactic == null) return 0;
        switch(tactic){
            case "Offense": return 5;
            case "Defense": return -3;
            default: return 0;
        }
    }
}
