package com.sportsmanager.football;
import com.sportsmanager.framework.League;
import com.sportsmanager.framework.Match;
import com.sportsmanager.framework.Player;
import com.sportsmanager.framework.Team;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FootballLeague extends League implements Serializable {
    private static final long serialVersionUID = 1L;
    protected int teamNumber;
    private List<FootballTeam> teams;
    private List<FootballPlayer> players;

    private List<List<FootballMatch>> seasonSchedule;
    private List<FootballMatch> currentWeekMatches;

    private String[] teamNames = {"Beşiktaş","Galatasaray","Fenerbahçe","Trabzonspor","Başakşehir","Göztepe","Samsunspor","Konyaspor","Rizespor","Gaziantep FK","Kocaelispor","Alanyaspor","Kasımpaşa","Gençlerbirliği","Eyüpspor","Antalyaspor","Kayserispor","Karagümrük"};

    public List<FootballTeam> getTeams() {
        return teams;
    }
    public void setTeams(List<FootballTeam> teams) {
        this.teams = teams;
    }
    public List<FootballPlayer> getPlayers() {
        return players;
    }
    public void setPlayers(List<FootballPlayer> players) {
        this.players = players;
    }


    Random rand = new Random();

    public FootballLeague(String leagueName,int startWeek){
        super(leagueName,startWeek);
        this.teamNumber=18;
        this.teams = new java.util.ArrayList<>();
        this.players = new java.util.ArrayList<>();
        this.seasonSchedule = new ArrayList<>();
        this.currentWeekMatches = new ArrayList<>();
    }

    @Override
    public void generateTeam() {
        int playerCounter = 0;
        for(int i = 0; i < teamNumber; i++){
            FootballTeam currentTeam = new FootballTeam(teamNames[i]);

            for(int j = 0; j < 18; j++){
                // Add the next unique player and increment the counter
                currentTeam.addPlayer(players.get(playerCounter));
                playerCounter++;
            }
            teams.add(currentTeam);
        }
    }

    @Override
    public void generatePlayer() {
        players.clear();
        for(int i = 0; i < teamNumber * 18; i++){
            players.add(new FootballPlayer("Player " + i, rand.nextInt(15) + 20));
        }
    }


    @Override
    public void generateStanding() {
        if(teams == null || teams.isEmpty()){
            return;
        }
        teams.sort((t1, t2) -> {
            if (t1.getPoints() != t2.getPoints()) {
                return Integer.compare(t2.getPoints(), t1.getPoints());
            }

            return Integer.compare(t2.getAverage(), t1.getAverage());
        });
    }

    public void generateFullSeasonFixture() {
        seasonSchedule.clear();

        List<FootballTeam> rotatedTeams = new ArrayList<>(teams);

        if (rotatedTeams.size() % 2 != 0) {
            rotatedTeams.add(new FootballTeam("BYE"));
        }

        int numTeams = rotatedTeams.size();
        int totalRounds = numTeams - 1;
        int matchesPerRound = numTeams / 2;

        List<List<FootballMatch>> firstHalf = new ArrayList<>();


        for (int round = 0; round < totalRounds; round++) {

            List<FootballMatch> roundMatches = new ArrayList<>();

            for (int matchIdx = 0; matchIdx < matchesPerRound; matchIdx++) {

                FootballTeam home = rotatedTeams.get(matchIdx);
                FootballTeam away = rotatedTeams.get(numTeams - 1 - matchIdx);

                if (!home.getTeamName().equals("BYE")
                        && !away.getTeamName().equals("BYE")) {

                    if (round % 2 == 1) {
                        roundMatches.add(new FootballMatch(home, away));
                    } else {
                        roundMatches.add(new FootballMatch(away, home));
                    }
                }
            }

            firstHalf.add(roundMatches);

            FootballTeam lastTeam = rotatedTeams.get(numTeams - 1);

            for (int i = numTeams - 1; i > 1; i--) {
                rotatedTeams.set(i, rotatedTeams.get(i - 1));
            }

            rotatedTeams.set(1, lastTeam);
        }


        seasonSchedule.addAll(firstHalf);


        for (List<FootballMatch> round : firstHalf) {

            List<FootballMatch> reverseRound = new ArrayList<>();

            for (FootballMatch match : round) {

                reverseRound.add(new FootballMatch(match.getAwayTeam(), match.getHomeTeam()));
            }

            seasonSchedule.add(reverseRound);
        }
    }

    public void generateFixtureForWeek() {
        currentWeekMatches.clear();
        int scheduleIndex = currentWeek - 1;

        if (scheduleIndex >= 0 && scheduleIndex < seasonSchedule.size()) {
            currentWeekMatches.addAll(seasonSchedule.get(scheduleIndex));
        }
    }

    public Match getUserMatch(Team userTeam) {
        return currentWeekMatches.stream()
                .filter(m -> m.getHomeTeam().equals(userTeam) || m.getAwayTeam().equals(userTeam))
                .findFirst()
                .orElse(null);
    }

    public void simulateRestOfMatches(Team userTeam) {
        for (FootballMatch match : currentWeekMatches) {
            if (!match.getHomeTeam().equals(userTeam) && !match.getAwayTeam().equals(userTeam)) {
                match.simulateFirstHalf();
                match.simulateSecondHalf();
            }
        }
    }

    @Override
    public void advanceWeek() {
        super.advanceWeek();
        for (Team t : teams) {
            for (Player p : t.getPlayers()) {
                p.healOneMatch();
            }
            t.setSubsRemaining(2); // Reset subs for the next match
        }
    }

}
