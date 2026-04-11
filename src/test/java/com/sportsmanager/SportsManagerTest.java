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
}