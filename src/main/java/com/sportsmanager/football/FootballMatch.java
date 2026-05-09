package com.sportsmanager.football;
import com.sportsmanager.framework.Match;
import com.sportsmanager.framework.Player;
import com.sportsmanager.framework.Tactic;
import com.sportsmanager.framework.Team;

import java.io.Serializable;
import java.util.Random;

public class FootballMatch extends Match implements Serializable {
    private static final long serialVersionUID = 1L;
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
        double homeAttack = homeTeam.calculateTeamSkill() + homeTeam.getTactic().getModifier() - awayTeam.getTactic().getModifier();
        double awayAttack = awayTeam.calculateTeamSkill() + awayTeam.getTactic().getModifier() - homeTeam.getTactic().getModifier();

        this.homeScore += (int) (Math.random() * (homeAttack / 20.0 + 1) * 2);
        this.awayScore += (int) (Math.random() * (awayAttack / 20.0 + 1) * 2);
    }

    public void simulateSecondHalf() {
        double homeAttack = homeTeam.calculateTeamSkill() + homeTeam.getTactic().getModifier() - awayTeam.getTactic().getModifier();
        double awayAttack = awayTeam.calculateTeamSkill() + awayTeam.getTactic().getModifier() - homeTeam.getTactic().getModifier();

        this.homeScore += (int) (Math.random() * (homeAttack / 20.0 + 1) * 2);
        this.awayScore += (int) (Math.random() * (awayAttack / 20.0 + 1) * 2);

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

        checkForInjuries(homeTeam);
        checkForInjuries(awayTeam);
    }

    private void checkForInjuries(Team team) {
        Random rand = new Random();
        for (Player p : team.getPlayers()) {

            if (rand.nextDouble() < 0.05) {
                p.setInjuryDuration(rand.nextInt(3) + 1);
            }
        }
    }
}
