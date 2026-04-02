package com.sportsmanager.framework;

public abstract class Match {
    String homeTeam;
    String awayTeam;
    int homeScore;
    int awayScore;

        public Match(String homeTeam, String awayTeam){
            this.homeTeam = homeTeam;
            this.awayTeam = awayTeam;
        }

        public abstract void simulate();
}
