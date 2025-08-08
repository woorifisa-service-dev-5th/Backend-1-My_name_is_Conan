package entity;

import java.util.List;
import java.util.ArrayList;

public class Game {
    private List<Person> participants;
    private int currentDay;
    private boolean isGameOver;
    private boolean mafiaCaught;

    public Game(List<Person> participants) {
        this.participants = new ArrayList<>(participants);
        this.currentDay = 0;
        this.isGameOver = false;
        this.mafiaCaught = false;
    }

    public List<Person> getParticipants() {
        return participants;
    }

    public void removeParticipant(Person person) {
        participants.remove(person);
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public void incrementDay() {
        this.currentDay++;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public boolean isMafiaCaught() {
        return mafiaCaught;
    }

    public void setMafiaCaught(boolean mafiaCaught) {
        this.mafiaCaught = mafiaCaught;
    }

    // 게임 종료 조건 확인 (임시 구현)
    public boolean checkGameOverCondition() {
        // 참가자가 2명 이하가 되면 게임 종료 (예시)
        if (participants.size() <= 2) {
            isGameOver = true;
            // 마피아가 잡혔는지 여부는 GameService에서 판단하도록 위임 가능
        }
        return isGameOver;
    }
}
