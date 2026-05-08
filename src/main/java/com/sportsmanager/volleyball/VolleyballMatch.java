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
    private Random rand = new Random();

    public VolleyballMatch(VolleyballTeam homeTeam, VolleyballTeam awayTeam) {
        super(0, 0);
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamSet = 0;
        this.awayTeamSet = 0;
    }

    private void playSet(int targetScore, double homeSkill, double awaySkill) {

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

    private void finishSet() {

        if (homeScore > awayScore) {
            homeTeamSet++;
            homeTeam.setWinSet(homeTeam.getWinSet() + 1);
            awayTeam.setLoseSet(awayTeam.getLoseSet() + 1);
        } else {
            awayTeamSet++;
            awayTeam.setWinSet(awayTeam.getWinSet() + 1);
            homeTeam.setLoseSet(homeTeam.getLoseSet() + 1);
        }

        homeTeam.setPointScored(homeTeam.getPointScored() + homeScore);
        homeTeam.setOpponentPoint(homeTeam.getOpponentPoint() + awayScore);

        awayTeam.setPointScored(awayTeam.getPointScored() + awayScore);
        awayTeam.setOpponentPoint(awayTeam.getOpponentPoint() + homeScore);

        int setNumber = sets.size() + 1;
        sets.add(setNumber + ". set " + homeScore + "-" + awayScore);

        homeScore = 0;
        awayScore = 0;
    }

    private void adjustTactics() {

        if (homeTeamSet < awayTeamSet) {
            homeTeam.setTactic(new Tactic("Offense", 5));
        } else {
            homeTeam.setTactic(new Tactic("Balanced", 0));
        }

        if (awayTeamSet < homeTeamSet) {
            awayTeam.setTactic(new Tactic("Offense", 5));
        } else {
            awayTeam.setTactic(new Tactic("Balanced", 0));
        }
    }

    
    public void simulate() {

        double homeSkill = homeTeam.calculateTeamSkill();
        double awaySkill = awayTeam.calculateTeamSkill();

        while (homeTeamSet < 3 && awayTeamSet < 3) {

            boolean isFinalSet = (homeTeamSet == 2 && awayTeamSet == 2);

            if (isFinalSet) {
                playSet(15, homeSkill, awaySkill);
            } else {
                playSet(25, homeSkill, awaySkill);
            }

            finishSet();
            adjustTactics();
        }

        if (homeTeamSet == 3 && (awayTeamSet == 0 || awayTeamSet == 1)) {
            homeTeam.setPoints(homeTeam.getPoints() + 3);
        }
        else if (awayTeamSet == 3 && (homeTeamSet == 0 || homeTeamSet == 1)) {
            awayTeam.setPoints(awayTeam.getPoints() + 3);
        }
        else if (homeTeamSet == 3 && awayTeamSet == 2) {
            homeTeam.setPoints(homeTeam.getPoints() + 2);
            awayTeam.setPoints(awayTeam.getPoints() + 1);
        }
        else if (awayTeamSet == 3 && homeTeamSet == 2) {
            awayTeam.setPoints(awayTeam.getPoints() + 2);
            homeTeam.setPoints(homeTeam.getPoints() + 1);
        }
    }

    public void printMatch() {
        for (String s : sets) {
            System.out.println(s);
        }
        System.out.println("Final: " + homeTeamSet + " - " + awayTeamSet);
    }
}