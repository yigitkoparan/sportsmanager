package com.sportsmanager.framework;

import java.io.Serializable;

public abstract class Match implements Serializable {
    private static final long serialVersionUID = 1L;
    protected int awayScore;
    protected int homeScore;
    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }



        public Match(int homeScore, int awayScore){
            this.homeScore = 0;
            this.awayScore = 0;
        }

}
