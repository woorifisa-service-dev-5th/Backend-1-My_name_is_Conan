package service;

import java.util.Scanner;

public class InputThread extends Thread {
    private String userInput = null;

    public void run() {
        Scanner scanner = new Scanner(System.in);
        try {
            if (scanner.hasNextLine()) {
                userInput = scanner.nextLine();
            }
        } catch (Exception e) {
            // 입력 중단 또는 에러 처리
        }
        scanner.close();
    }

    public String getUserInput() {
        return userInput;
    }
}

