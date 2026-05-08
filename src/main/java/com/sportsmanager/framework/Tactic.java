package com.sportsmanager.framework;

import java.io.Serializable;

public class Tactic implements Serializable {
    private static final long serialVersionUID = 1L;
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
