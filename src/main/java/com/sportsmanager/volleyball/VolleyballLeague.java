package com.sportsmanager.volleyball;

import com.sportsmanager.framework.League;
import com.sportsmanager.framework.Match;
import com.sportsmanager.framework.Player;
import com.sportsmanager.framework.Team;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VolleyballLeague extends League implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int teamNumber;
    private List<VolleyballTeam> teams;
    private List<VolleyballPlayer> players;


    private List<List<VolleyballMatch>> seasonSchedule;
    private transient List<VolleyballMatch> currentWeekMatches; // transient because we regenerate it from schedule

    private String[] teamNames = {
            "VakıfBank", "Eczacıbaşı", "Fenerbahçe Opet", "THY", "Kuzeyboru",
            "Galatasaray Daikin", "Nilüfer Bld.", "Muratpaşa Bld.", "Sarıyer Bld.",
            "Çukurova Bld.", "Beşiktaş Ayos", "Aydın BB", "PTT", "Karayolları",
            "Zeren Spor", "Aras Kargo", "Bahçelievler Bld.", "Sigorta Shop"
    };

    public VolleyballLeague(String leagueName, int startWeek) {
        super(leagueName, startWeek);
        this.teamNumber = 18;
        this.teams = new ArrayList<>();
        this.players = new ArrayList<>();
        this.seasonSchedule = new ArrayList<>();
        this.currentWeekMatches = new ArrayList<>();
    }



    @Override
    public void generatePlayer() {
        Random rand = new Random();
        for (int i = 0; i < teamNumber; i++) {
            for (int j = 0; j < 12; j++) {
                players.add(new VolleyballPlayer("Player_V" + i + j, rand.nextInt(15) + 20));
            }
        }
    }

    @Override
    public void generateTeam() {
        for (int i = 0; i < teamNumber; i++) {
            VolleyballTeam team = new VolleyballTeam(teamNames[i]);
            for (int j = 0; j < 12; j++) {
                team.addPlayer(players.get(i * 12 + j));
            }
            teams.add(team);
        }
    }

    @Override
    public void generateStanding() {
        if (teams == null || teams.isEmpty()) return;

        teams.sort((t1, t2) -> {
            if (t1.getPoints() != t2.getPoints()) {
                return Integer.compare(t2.getPoints(), t1.getPoints());
            }

            return Integer.compare(t2.getAverage(), t1.getAverage());
        });
    }



    public void generateFullSeasonFixture() {
        seasonSchedule.clear();

        List<VolleyballTeam> rotatedTeams = new ArrayList<>(teams);

        if (rotatedTeams.size() % 2 != 0) {
            rotatedTeams.add(new VolleyballTeam("BYE"));
        }

        int numTeams = rotatedTeams.size();
        int totalRounds = numTeams - 1;
        int matchesPerRound = numTeams / 2;

        List<List<VolleyballMatch>> firstHalf = new ArrayList<>();


        for (int round = 0; round < totalRounds; round++) {

            List<VolleyballMatch> roundMatches = new ArrayList<>();

            for (int matchIdx = 0; matchIdx < matchesPerRound; matchIdx++) {

                VolleyballTeam home = rotatedTeams.get(matchIdx);
                VolleyballTeam away = rotatedTeams.get(numTeams - 1 - matchIdx);

                if (!home.getTeamName().equals("BYE")
                        && !away.getTeamName().equals("BYE")) {

                    if (round % 2 == 1) {
                        roundMatches.add(new VolleyballMatch(home, away));
                    } else {
                        roundMatches.add(new VolleyballMatch(away, home));
                    }
                }
            }

            firstHalf.add(roundMatches);

            VolleyballTeam lastTeam = rotatedTeams.get(numTeams - 1);

            for (int i = numTeams - 1; i > 1; i--) {
                rotatedTeams.set(i, rotatedTeams.get(i - 1));
            }

            rotatedTeams.set(1, lastTeam);
        }


        seasonSchedule.addAll(firstHalf);


        for (List<VolleyballMatch> round : firstHalf) {

            List<VolleyballMatch> reverseRound = new ArrayList<>();

            for (VolleyballMatch match : round) {

                reverseRound.add(new VolleyballMatch(match.getAwayTeam(), match.getHomeTeam()));
            }

            seasonSchedule.add(reverseRound);
        }
    }

    public void generateFixtureForWeek() {
        if (currentWeekMatches == null) currentWeekMatches = new ArrayList<>();
        currentWeekMatches.clear();
        int idx = currentWeek - 1;
        if (idx >= 0 && idx < seasonSchedule.size()) {
            currentWeekMatches.addAll(seasonSchedule.get(idx));
        }
    }



    public Match getUserMatch(Team userTeam) {
        return currentWeekMatches.stream()
                .filter(m -> m.getHomeTeam().equals(userTeam) || m.getAwayTeam().equals(userTeam))
                .findFirst()
                .orElse(null);
    }

    public void simulateRestOfMatches(Team userTeam) {
        if (currentWeekMatches == null) return;

        for (VolleyballMatch m : currentWeekMatches) {
            if (!m.getHomeTeam().equals(userTeam) && !m.getAwayTeam().equals(userTeam)) {

                while (!m.isMatchFinished()) {
                    m.playNextSet();
                }
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
            t.setSubsRemaining(2);
        }
    }


    public List<VolleyballTeam> getTeams() { return teams; }
}
