package com.sportsmanager.framework;


    public abstract class Player {
        protected String name;
         protected int age;
         protected int skillLevel;
         protected boolean isInjured;

        public Player(String name, int age, int skillLevel) {
            this.name = name;
            this.age = age;
            this.skillLevel = skillLevel;
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
