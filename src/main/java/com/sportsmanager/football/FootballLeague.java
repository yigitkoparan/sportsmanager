package com.sportsmanager.football;
import com.sportsmanager.framework.League;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FootballLeague extends League {
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
        for(int i = 0; i < teamNumber; i++){
            teams.add(new FootballTeam(teamNames[i]));
            for(int j = 0; j < 11; j++){
                teams.get(i).addPlayer(players.get(i+j));
            }
        }
    }

    @Override
    public void generatePlayer() {
        for(int i = 0; i < teamNumber; i++){
            for(int j = 0; j < 11; j++){
                players.add(new FootballPlayer("player"+i+j, rand.nextInt(15)+20));
            }
        }
    }


    @Override
    public void generateStanding() {
        if(teams == null || teams.isEmpty()){
            return;
        }
        teams.sort((t1, t2) -> Integer.compare(t2.getPoints(), t1.getPoints()));
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

        for (int round = 0; round < totalRounds; round++) {
            List<FootballMatch> roundMatches = new ArrayList<>();

            for (int matchIdx = 0; matchIdx < matchesPerRound; matchIdx++) {
                FootballTeam home = rotatedTeams.get(matchIdx);
                FootballTeam away = rotatedTeams.get(numTeams - 1 - matchIdx);


                if (!home.getTeamName().equals("BYE") && !away.getTeamName().equals("BYE")) {

                    if (round % 2 == 1) {
                        roundMatches.add(new FootballMatch(home, away));
                    } else {
                        roundMatches.add(new FootballMatch(away, home));
                    }
                }
            }
            seasonSchedule.add(roundMatches);

            FootballTeam lastTeam = rotatedTeams.get(numTeams - 1);
            for (int i = numTeams - 1; i > 1; i--) {
                rotatedTeams.set(i, rotatedTeams.get(i - 1));
            }
            rotatedTeams.set(1, lastTeam);
        }
    }

    public void generateFixtureForWeek() {
        currentWeekMatches.clear();
        int scheduleIndex = currentWeek - 1;

        if (scheduleIndex >= 0 && scheduleIndex < seasonSchedule.size()) {
            currentWeekMatches.addAll(seasonSchedule.get(scheduleIndex));
        }
    }

    public FootballMatch getUserMatch(FootballTeam userTeam) {
        return currentWeekMatches.stream()
                .filter(m -> m.getHomeTeam().equals(userTeam) || m.getAwayTeam().equals(userTeam))
                .findFirst()
                .orElse(null);
    }

    public void simulateRestOfMatches(FootballTeam userTeam) {
        for (FootballMatch match : currentWeekMatches) {
            if (!match.getHomeTeam().equals(userTeam) && !match.getAwayTeam().equals(userTeam)) {
                match.simulateFirstHalf();
                match.simulateSecondHalf();
            }
        }
    }

    public void advanceWeek(){
        this.currentWeek++;
    }

}
