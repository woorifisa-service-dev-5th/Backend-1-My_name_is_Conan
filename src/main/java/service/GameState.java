package service;

import types.Time;

public class GameState {
    private Time currentTime;
    private int currentDay; // New field for the day count

    public GameState() {
        this.currentTime = Time.Morning;
        this.currentDay = 1; // Initialize the game to Day 1
    }

    public Time getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Time newTime) {
        this.currentTime = newTime;
    }

    // Getter for the day count
    public int getCurrentDay() {
        return currentDay;
    }

    // Method to increment the day
    public void incrementCurrentDay() {
        this.currentDay++;
    }
}