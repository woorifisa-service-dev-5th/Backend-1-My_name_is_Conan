package service;

import types.Time;

public class TimeThread extends Thread {
	private GameState gameState = new GameState();
    private volatile boolean running = true;

    public TimeThread(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void run() {
        Time[] timeCycle = Time.values(); //time enum 배열 구조로 바꿈
        int index = 0;

        while (running) {
            Time current = timeCycle[index];
            gameState.setCurrentTime(current);

            switch (current) {
                case Morning:
                    System.out.println("\n[아침이 되었습니다.]");
                    System.out.println("밤 사이에 한 명이 죽었습니다...");
                    break;

                case Afternoon:
                    System.out.println("\n[오후입니다.]");
                    System.out.println("용의자를 지목하여 죽일 수 있습니다.");
                    break;

                case Evening:
                    System.out.println("\n[밤입니다.]");
                    System.out.println("지금은 아무 행동도 할 수 없습니다. 조용히 밤을 보내세요...");
                    break;
            }

            try {
                Thread.sleep(30000); // 30초마다 시간 변경
            } catch (InterruptedException e) {
                running = false;
            }

         // MORNING, AFTERNOON, EVENING 반복
            index = (index + 1) % timeCycle.length;  
        }
    }

    public void stopTime() {
        running = false;
        this.interrupt();
    }
}
