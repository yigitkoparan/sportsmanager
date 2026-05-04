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
    private ArrayList<String> sets = new ArrayList<>();


    public VolleyballMatch(VolleyballTeam homeTeam, VolleyballTeam awayTeam) {
        super(0, 0);
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamSet = 0;
        this.awayTeamSet = 0;
    }

    private int getTacticModifier(String tactic) {
        if (tactic == null) return 0;
        switch (tactic) {
            case "Offense":
                return 5;
            case "Defense":
                return -3;
            default:
                return 0;
        }
    }

    Random rand = new Random();

    public void simulate() {
        double homeSkill = homeTeam.calculateTeamSkill();
        double awaySkill = awayTeam.calculateTeamSkill();
        while (homeTeamSet < 3 && awayTeamSet < 3) {
            if (homeTeamSet == 2 && awayTeamSet == 2) {
                while (homeScore < 15 && awayScore < 15) {
                    int homeBound = (int) homeSkill + homeTeam.getTactic().getModifier();
                    int awayBound = (int) awaySkill + awayTeam.getTactic().getModifier();

                    int homeProbability = rand.nextInt(Math.max(1, homeBound)) ;
                    int awayProbability = rand.nextInt(Math.max(1, awayBound)) ;
                    if (homeProbability > awayProbability) {
                        homeScore++;
                    } else if (awayProbability > homeProbability) {
                        awayScore++;
                    }
                    if (homeScore == 14 && awayScore == 14) {
                        while (Math.abs(homeScore - awayScore) < 2) {

                            homeProbability = rand.nextInt(Math.max(1, homeBound)) ;
                            awayProbability = rand.nextInt(Math.max(1, awayBound)) ;
                            if (homeProbability > awayProbability) {
                                homeScore++;
                            } else if (awayProbability > homeProbability) {
                                awayScore++;
                            }

                        }
                    }

                }
                if (homeScore > awayScore) {
                    homeTeamSet++;
                    homeTeam.setWinSet(homeTeam.getWinSet() + 1);
                    awayTeam.setLoseSet(awayTeam.getLoseSet() + 1);

                }
                if (awayScore > homeScore) {
                    awayTeamSet++;
                    awayTeam.setWinSet(awayTeam.getWinSet() + 1);
                    homeTeam.setLoseSet(homeTeam.getLoseSet() + 1);
                }
                homeTeam.setPointScored(homeTeam.getPointScored() + homeScore);
                homeTeam.setOpponentPoint(homeTeam.getOpponentPoint() + awayScore);
                awayTeam.setPointScored(awayTeam.getPointScored() + awayScore);
                awayTeam.setOpponentPoint(awayTeam.getOpponentPoint() + homeScore);

                int setNumber = homeTeamSet + awayTeamSet;
                sets.add(setNumber + ". set " + homeScore + "-" + awayScore);

                homeScore = 0;
                awayScore = 0;
            } else {
                while (homeScore < 25 && awayScore < 25) {

                    int homeBound = (int) homeSkill + homeTeam.getTactic().getModifier();
                    int awayBound = (int) awaySkill + awayTeam.getTactic().getModifier();
                    int homeProbability = rand.nextInt(Math.max(1, homeBound)) ;
                    int awayProbability = rand.nextInt(Math.max(1, awayBound)) ;
                    if (homeProbability > awayProbability) {
                        homeScore++;
                    } else if (awayProbability > homeProbability) {
                        awayScore++;
                    }
                    if (homeScore == 24 && awayScore == 24) {
                        while (Math.abs(homeScore - awayScore) < 2) {

                            homeProbability = rand.nextInt(Math.max(1, homeBound)) ;
                            awayProbability = rand.nextInt(Math.max(1, awayBound)) ;
                            if (homeProbability > awayProbability) {
                                homeScore++;
                            } else if (awayProbability > homeProbability) {
                                awayScore++;
                            }

                        }
                    }

                }
                if (homeScore > awayScore) {
                    homeTeamSet++;
                    homeTeam.setWinSet(homeTeam.getWinSet() + 1);
                    awayTeam.setLoseSet(awayTeam.getLoseSet() + 1);

                }
                if (awayScore > homeScore) {
                    awayTeamSet++;
                    awayTeam.setWinSet(awayTeam.getWinSet() + 1);
                    homeTeam.setLoseSet(homeTeam.getLoseSet() + 1);
                }
                homeTeam.setPointScored(homeTeam.getPointScored() + homeScore);
                homeTeam.setOpponentPoint(homeTeam.getOpponentPoint() + awayScore);
                awayTeam.setPointScored(awayTeam.getPointScored() + awayScore);
                awayTeam.setOpponentPoint(awayTeam.getOpponentPoint() + homeScore);

                int setNumber = homeTeamSet + awayTeamSet + 1;
                sets.add(setNumber + ". set " + homeScore + "-" + awayScore);
                homeScore = 0;
                awayScore = 0;

            }
            if (homeTeamSet == 3 && (awayTeamSet == 0 || awayTeamSet == 1)) {
                homeTeam.setPoints(homeTeam.getPoints() + 3);
            } else if (awayTeamSet == 3 && (homeTeamSet == 0 || homeTeamSet == 1)) {
                awayTeam.setPoints(awayTeam.getPoints() + 3);
            } else if (homeTeamSet == 3 && awayTeamSet == 2) {
                homeTeam.setPoints(homeTeam.getPoints() + 2);
                awayTeam.setPoints(awayTeam.getPoints() + 1);
            } else if (awayTeamSet == 3 && homeTeamSet == 2) {
                awayTeam.setPoints(awayTeam.getPoints() + 2);
                homeTeam.setPoints(homeTeam.getPoints() + 1);


            }
        }


    }
}

