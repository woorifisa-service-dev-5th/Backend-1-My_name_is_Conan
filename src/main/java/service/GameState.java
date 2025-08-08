package service;

import types.Time;

public class GameState {
	public volatile Time currentTime = Time.Morning;

    public Time getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Time time) {
        this.currentTime = time;
    }


}
