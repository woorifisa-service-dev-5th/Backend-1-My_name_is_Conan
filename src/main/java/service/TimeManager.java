package service;

import types.Time;

public class TimeManager {
    private GameState gameState;

    public TimeManager(GameState gameState) {
        this.gameState = gameState;
    }

    public void advanceTime() {
        Time current = gameState.getCurrentTime();
        Time next;

        switch (current) {
            case Morning:
                next = Time.Afternoon;
                break;
            case Afternoon:
                next = Time.Evening;
                break;
            default:
                next = Time.Morning;
                // Increment day count when a new day begins
                gameState.incrementCurrentDay();
                break;
        }

        gameState.setCurrentTime(next);
        printTimeMessage(next);
    }

    // Add the missing passNight() method
    public void passNight() {
        // This method can simply advance the time, which will handle the day change.
        System.out.println("\n[밤이 지났습니다.]");
        advanceTime();
    }

    // Add the missing getCurrentDay() method
    public int getCurrentDay() {
        return gameState.getCurrentDay();
    }

    public void printTimeMessage(Time time) {
        switch (time) {
            case Morning:
                System.out.println("\n[아침이 되었습니다.]");
                System.out.println("밤 사이에 한 명이 죽었습니다...");
                break;
            case Afternoon:
                System.out.println("\n[오후입니다.]");
                System.out.println("용의자를 지목하여 죽이거나 피의자의 정보를 알 수 있습니다.");
                break;
            case Evening:
                System.out.println("\n[밤입니다.]");
                System.out.println("지금은 아무 행동도 할 수 없습니다. 조용히 밤을 보내세요...");
                break;
        }
    }
}