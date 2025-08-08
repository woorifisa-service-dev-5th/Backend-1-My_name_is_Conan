package entity;

import types.Time;

public class Game {
    private Time time;
    int days;
    Person Criminal;
    boolean isFinished;

    public Game(Person criminal){
        this.time = Time.Morning;
        this.days = 0;
        this.Criminal = criminal;
        this.isFinished = false;
    }
}
