package com.sportsmanager.framework;
import java.util.Random;


    public abstract class Player {
        protected String name;
         protected int age;
         protected boolean isInjured;
        protected int skillLevel;
        Random r =new Random();

        public int getSkillLevel() {
            return skillLevel;
        }

        public void setSkillLevel(int skillLevel) {
            this.skillLevel = skillLevel;
        }




        public Player(String name, int age) {
            this.name = name;
            this.age = age;
            this.skillLevel =r.nextInt(20)+1 ;
            this.isInjured = false;
        }


        public abstract int calculatePerformance();


        public String getName() {
            return name;
        }

        public boolean isInjured() {
            return isInjured;
        }

        public void setInjured(boolean injured) {
            this.isInjured = injured;
        }
    }
