package com.sportsmanager.volleyball;

import com.sportsmanager.framework.Match;
import com.sportsmanager.framework.Tactic;

import java.io.Serializable;
import java.util.Random;
import java.util.ArrayList;

public class VolleyballMatch extends Match implements Serializable {
    private static final long serialVersionUID = 1L;

    private VolleyballTeam homeTeam;
    private VolleyballTeam awayTeam;

    private int homeTeamSet;
    private int awayTeamSet;


    private ArrayList<String> sets = new ArrayList<>();

    private transient Random rand = new Random();

    public VolleyballMatch(VolleyballTeam homeTeam, VolleyballTeam awayTeam) {
        super(0, 0);
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamSet = 0;
        this.awayTeamSet = 0;
    }


    public boolean playNextSet() {
        if (isMatchFinished()) return false;
        if (rand == null) rand = new Random();
        int targetScore = (homeTeamSet == 2 && awayTeamSet == 2) ? 15 : 25;

        simulateSetPoints(targetScore);
        finalizeSetResult();

        if (isMatchFinished()) {
            distributeLeaguePoints();
            return false;
        }
        return true;
    }


    private void simulateSetPoints(int targetScore) {
        double homeSkill = homeTeam.calculateTeamSkill();
        double awaySkill = awayTeam.calculateTeamSkill();

        while (homeScore < targetScore && awayScore < targetScore) {

            int homeBound = (int) homeSkill + homeTeam.getTactic().getModifier();
            int awayBound = (int) awaySkill + awayTeam.getTactic().getModifier();

            int homeProb = rand.nextInt(Math.max(1, homeBound));
            int awayProb = rand.nextInt(Math.max(1, awayBound));

            if (homeProb > awayProb) homeScore++;
            else if (awayProb > homeProb) awayScore++;


            if (homeScore == targetScore - 1 && awayScore == targetScore - 1) {
                while (Math.abs(homeScore - awayScore) < 2) {
                    homeProb = rand.nextInt(Math.max(1, homeBound));
                    awayProb = rand.nextInt(Math.max(1, awayBound));
                    if (homeProb > awayProb) homeScore++;
                    else if (awayProb > homeProb) awayScore++;
                }
            }
        }
    }


    private void finalizeSetResult() {
        if (homeScore > awayScore) {
            homeTeamSet++;
            homeTeam.setWinSet(homeTeam.getWinSet() + 1);
            awayTeam.setLoseSet(awayTeam.getLoseSet() + 1);
        } else {
            awayTeamSet++;
            awayTeam.setWinSet(awayTeam.getWinSet() + 1);
            homeTeam.setLoseSet(homeTeam.getLoseSet() + 1);
        }


        sets.add("Set " + (sets.size() + 1) + ": " + homeScore + " - " + awayScore);


        homeScore = 0;
        awayScore = 0;
    }


    private void distributeLeaguePoints() {
        if (homeTeamSet == 3 && awayTeamSet < 2) {
            homeTeam.setPoints(homeTeam.getPoints() + 3);
        } else if (awayTeamSet == 3 && homeTeamSet < 2) {
            awayTeam.setPoints(awayTeam.getPoints() + 3);
        } else if (homeTeamSet == 3 && awayTeamSet == 2) {
            homeTeam.setPoints(homeTeam.getPoints() + 2);
            awayTeam.setPoints(awayTeam.getPoints() + 1);
        } else if (awayTeamSet == 3 && homeTeamSet == 2) {
            awayTeam.setPoints(awayTeam.getPoints() + 2);
            homeTeam.setPoints(homeTeam.getPoints() + 1);
        }
    }

    public boolean isMatchFinished() {
        return homeTeamSet == 3 || awayTeamSet == 3;
    }


    public VolleyballTeam getHomeTeam() { return homeTeam; }
    public VolleyballTeam getAwayTeam() { return awayTeam; }
    public int getHomeTeamSet() { return homeTeamSet; }
    public int getAwayTeamSet() { return awayTeamSet; }
    public ArrayList<String> getSets() { return sets; }
}