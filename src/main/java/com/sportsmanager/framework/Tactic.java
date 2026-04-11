package com.sportsmanager.framework;

public class Tactic {
    protected boolean defensive;
    protected boolean offensive;
    protected boolean balanced;

    public boolean isOffensive() {
        return offensive;
    }

    public void setOffensive(boolean offensive) {
        this.offensive = offensive;
    }


    public boolean isBalanced() {
        return balanced;
    }

    public void setBalanced(boolean balanced) {
        this.balanced = balanced;
    }


    public boolean isDefensive() {
        return defensive;
    }

    public void setDefensive(boolean defensive) {
        this.defensive = defensive;
    }


    public Tactic(boolean offensive, boolean balanced, boolean defensive){
        this.offensive = offensive;
        this.balanced = balanced;
        this.defensive = defensive;
    }
    public void Offensive(){
        balanced=false;
        defensive=false;
        offensive=true;
    }
    public void Defensive(){
        balanced=false;
        defensive=true;
        offensive=false;
    }
    public void Balance(){
        balanced=true;
        defensive=false;
        offensive=false;
    }

}
