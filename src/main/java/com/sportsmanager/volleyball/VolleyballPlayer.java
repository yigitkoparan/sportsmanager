package com.sportsmanager.volleyball;
import com.sportsmanager.framework.Player;

import java.io.Serializable;

public class VolleyballPlayer extends Player implements Serializable {
    private static final long serialVersionUID = 1L;
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
