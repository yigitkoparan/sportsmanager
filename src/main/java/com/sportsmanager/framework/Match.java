package com.sportsmanager.framework;

public abstract class Match {
    protected int homeScore;
    protected int awayScore;


        public Match(int homeScore, int awayScore){
            this.homeScore = 0;
            this.awayScore = 0;
        }

        public abstract void simulate();
}
