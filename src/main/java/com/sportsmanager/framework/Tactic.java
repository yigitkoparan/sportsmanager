package com.sportsmanager.framework;

public class Tactic {
   private String tacticName;
   private int modifier;

   public Tactic(String tacticName, int modifier){
       this.tacticName = tacticName;
       this.modifier = modifier;
   }

   public String getTacticName(){
       return tacticName;
   }

   public int getModifier(){
       return modifier;
   }

}
