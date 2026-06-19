package com.sportsmanager.football;
import com.sportsmanager.framework.Player;

import java.io.Serializable;

public class FootballPlayer extends Player implements Serializable {
    private static final long serialVersionUID = 1L;

        private int goals;
        private int assists;

        public FootballPlayer(String name, int age,String position) {
            super(name, age);
            this.position = position;
            this.goals = 0;
            this.assists = 0;
        }

        @Override
        public int calculatePerformance() {

            return this.skillLevel + (goals * 2);
        }
    }

