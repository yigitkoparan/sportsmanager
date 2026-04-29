package com.sportsmanager.volleyball;
import com.sportsmanager.framework.Player;

public class VolleyballPlayer extends Player{
    private int point;
    public VolleyballPlayer(String name, int age) {
        super(name, age);
        this.point = 0;

    }
    @Override
    public int calculatePerformance() {

        return this.skillLevel + (point * 2);
    }
}
