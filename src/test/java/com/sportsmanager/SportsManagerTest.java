package com.sportsmanager;

import com.sportsmanager.football.*;
import com.sportsmanager.framework.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SportsManagerTest {

    @Test
    public void testFootballPointSystem() {
        FootballTeam team = new FootballTeam("Test FC");
        team.setWins(2);  // 6 points
        team.setDraw(1);  // 1 point
        team.setPoints(team.getWins() * 3 + team.getDraw());

        assertEquals(7, team.getPoints(), "2 wins and 1 draw should be 7 points");
    }

    @Test
    public void testTacticSwitching() {
        Tactic tactic = new Tactic(false, true, false);
        tactic.Offensive();

        assertTrue(tactic.isOffensive());
        assertFalse(tactic.isBalanced());
    }

    @Test
    public void testMatchSimulationIncrementsGames() {
        FootballTeam home = new FootballTeam("Home");
        FootballTeam away = new FootballTeam("Away");


        FootballPlayer p1 = new FootballPlayer("Ronaldo", 25);
        p1.setSkillLevel(15);
        home.addPlayer(p1);

        FootballPlayer p2 = new FootballPlayer("Messi", 28);
        p2.setSkillLevel(18);
        away.addPlayer(p2);

        FootballMatch match = new FootballMatch(home, away);


        match.simulate();

        assertEquals(1, home.getGamesPlayed());
        assertEquals(1, away.getGamesPlayed());
    }

    @Test
    public void testFootballPlayerPerformance() {
        FootballPlayer p = new FootballPlayer("Semih Kılıçsoy", 19);
        p.setSkillLevel(15);

        assertEquals(15, p.calculatePerformance());
    }
    @Test
    public void testTeamSkillCalculation() {
        FootballTeam team = new FootballTeam("Besiktas");
        FootballPlayer p1 = new FootballPlayer("P1", 20);
        p1.setSkillLevel(10);
        FootballPlayer p2 = new FootballPlayer("P2", 21);
        p2.setSkillLevel(20);

        team.addPlayer(p1);
        team.addPlayer(p2);


        assertEquals(30.0/2.0, team.calculateTeamSkill(), 0.01);
    }

    @Test
    public void testPlayerInjuryStatus() {
        FootballPlayer player = new FootballPlayer("Arda Guler", 21);

        assertFalse(player.isInjured(), "Player should not be injured by default");

        player.setInjured(true);

        assertTrue(player.isInjured(), "Player status should be updated to injured");
    }

    @Test
    public void testPlayerAge(){
        FootballPlayer player = new FootballPlayer("Arda Guler", 21);

        assertEquals(21,player.getAge());
    }

    @Test
    public void testTeamNameRetrieval() {
        FootballTeam team = new FootballTeam("Göztepe SK");

        assertEquals("Göztepe SK", team.getTeamName(), "The team name should match the constructor input");

        team.setTeamName("İzmir Spor");
        assertEquals("İzmir Spor", team.getTeamName(), "The team name should be updatable via setter");
    }

    @Test
    public void testGoalAccumulationAfterMatch() {
        FootballTeam home = new FootballTeam("BJK");
        FootballTeam away = new FootballTeam("fb");

        home.addPlayer(new FootballPlayer("Sergen Attı Adele Attı", 25));
        away.addPlayer(new FootballPlayer("Tümer Metin", 25));

        FootballMatch match = new FootballMatch(home, away);

        int initialHomeGoals = home.getGoalsScored();

        match.simulate();


        int expectedTotal = initialHomeGoals + match.getHomeScore();

        assertEquals(expectedTotal, home.getGoalsScored(),"Team's total goals should increase by the match score amount");
    }

    @Test
    public void calculateTeamSkill(){
        FootballPlayer p1 = new FootballPlayer("p1",21);
        FootballPlayer p2 = new FootballPlayer("p2",21);
        FootballTeam t1 = new FootballTeam("Beşiktaş");

        p1.setSkillLevel(12);
        p2.setSkillLevel(11);

        t1.addPlayer(p1);
        t1.addPlayer(p2);

        assertEquals(11.5,t1.calculateTeamSkill(),0.001);
    }
    @Test
    public void testTacticOffensiveSetsCorrectly() {
        Tactic t = new Tactic(false, true, false);
        t.Offensive();
        assertTrue(t.isOffensive(), "Offensive should be true");
        assertFalse(t.isBalanced(), "Balanced should be false");
        assertFalse(t.isDefensive(), "Defensive should be false");
    }

    @Test
    public void testTacticDefensiveSetsCorrectly() {
        Tactic t = new Tactic(false, true, false);
        t.Defensive();
        assertTrue(t.isDefensive());
        assertFalse(t.isOffensive());
    }

    @Test
    public void testTacticBalancedSetsCorrectly() {
        Tactic t = new Tactic(true, false, false);
        t.Balance();
        assertTrue(t.isBalanced());
        assertFalse(t.isOffensive());
    }

    @Test
    public void testTacticConstructor() {
        Tactic t = new Tactic(true, false, false);
        assertTrue(t.isOffensive());
    }
    @Test
    public void testOffensiveTacticModifier() {
        FootballTeam home = new FootballTeam("H");
        FootballTeam away = new FootballTeam("A");
        FootballMatch match = new FootballMatch(home, away);

        Tactic offensive = new Tactic(true, false, false);

        
        int modifier = match.getHomeModifier(offensive, offensive);

        assertEquals(4, modifier, "Double offensive tactics should result in a +4 modifier");
    }

    @Test
    public void testPlayerSkillRange() {
        FootballPlayer p = new FootballPlayer("Orkun Kökçü", 20);

        assertTrue(p.getSkillLevel() >= 1 && p.getSkillLevel() <= 20);
    }

    @Test
    public void testMatchDrawPoints() {
        FootballTeam home = new FootballTeam("Beşiktaş");
        FootballTeam away = new FootballTeam("Galatasaray");

        FootballPlayer p = new FootballPlayer("Sergen Yalçın", 25);
        p.setSkillLevel(20);
        home.addPlayer(p);
        away.addPlayer(p);

        FootballMatch match = new FootballMatch(home, away);

        match.simulate();

        if (match.getHomeScore() == match.getAwayScore()) {

            assertEquals(1, home.getPoints(), "Home team should get 1 point for a draw");
            assertEquals(1, away.getPoints(), "Away team should get 1 point for a draw");

            assertEquals(1, home.getDraw(), "Home team draw count should increase");
            assertEquals(1, away.getDraw(), "Away team draw count should increase");
        }
    }

    @Test
    public void testStandingSorting() {
        FootballLeague league = new FootballLeague("Süper Lig", 1);
        FootballTeam topTeam = new FootballTeam("Şampiyon Beşiktaş");
        FootballTeam bottomTeam = new FootballTeam("GS Kümeye");

        topTeam.setPoints(50);
        bottomTeam.setPoints(10);

        league.getTeams().add(bottomTeam);
        league.getTeams().add(topTeam);

        league.generateStanding(); //

        assertEquals("Şampiyon Beşiktaş", league.getTeams().get(0).getTeamName());
    }
    @Test
    public void testWinPointsLogic() {
        FootballTeam home = new FootballTeam("Home");
        FootballTeam away = new FootballTeam("Away");

        home.addPlayer(new FootballPlayer("P1", 20));
        away.addPlayer(new FootballPlayer("P2", 20));

        FootballMatch match = new FootballMatch(home, away);
        match.simulate();

        if (match.getHomeScore() > match.getAwayScore()) {
            assertEquals(3, home.getPoints(), "Winner should get 3 points");
            assertEquals(1, home.getWins(), "Win count should increase by 1");
        }
    }
}