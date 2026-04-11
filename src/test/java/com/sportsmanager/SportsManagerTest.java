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
}