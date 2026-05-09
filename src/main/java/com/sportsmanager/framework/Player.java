package com.sportsmanager.framework;
import java.io.Serializable;
import java.util.Random;


    public abstract class Player implements Serializable {
        private static final long serialVersionUID = 1L;
        protected String name;
        protected int age;
        protected boolean isInjured;
        protected int injuryDuration = 0;
        protected int skillLevel;
        Random r =new Random();

        public void healOneMatch() {
            if (injuryDuration > 0) {
                injuryDuration--;
                if (injuryDuration == 0) this.isInjured = false;
            }
        }

        public int getInjuryDuration() { return injuryDuration; }

        public void setInjuryDuration(int duration) {
            this.injuryDuration = duration;
            this.isInjured = (duration > 0);
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }


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
