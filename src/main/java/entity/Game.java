package entity;

import types.Time;

public class Game {
    private static Time time;
    private static int days;
    private static Person Criminal;
    private static boolean isFinished;

    public Game(Person criminal){
        this.time = Time.Morning;
        this.days = 0;
        this.Criminal = criminal;
        this.isFinished = false;
    }
}
