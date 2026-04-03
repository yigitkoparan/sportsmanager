package com.sportsmanager.football;
import com.sportsmanager.framework.Player;

public class FootballPlayer extends Player{


        private int goals;
        private int assists;

        public FootballPlayer(String name, int age, int skillLevel) {
            super(name, age, skillLevel);
            this.goals = 0;
            this.assists = 0;
        }

        @Override
        public int calculatePerformance() {

            return this.skillLevel + (goals * 2);
        }
    }

