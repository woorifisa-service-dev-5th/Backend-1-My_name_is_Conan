package controller;

import service.GameService;
import service.TimeManager;
import logger.GeneralLogger;
import entity.Person;
import types.Time;

import java.util.List;
import java.util.Scanner;

public class GameController {
    private GameService gameService;
    private TimeManager timeManager;
    private GeneralLogger logger;
    private Scanner scanner;

    public GameController(GameService gameService, TimeManager timeManager, GeneralLogger logger, Scanner scanner) {
        this.gameService = gameService;
        this.timeManager = timeManager;
        this.logger = logger;
        this.scanner = scanner;
    }

    public void startGame(List<Person> participants) {
        gameService.initializeGame(participants);
        logger.gameStart(participants.size());
        // logger.log("INFO", "게임 시작 - 참가자: " + participants.size() + "명"); // logger 사용
        System.out.println("게임 시작 - 참가자: " + participants.size() + "명"); // 사용자에게 보여주는 메시지는 sout 유지

        runGameLoop();
    }

    private void runGameLoop() {
        while (!gameService.isGameEnd()) {
            passNightPhase();
            if (gameService.isGameEnd()) break; // 밤 이후 게임 종료 확인
            runDayPhase();
        }

        endGame();
    }

    private void passNightPhase() {
        timeManager.passNight();
        timeManager.printTimeMessage(Time.Morning);
        logger.newDay(timeManager.getCurrentDay());
        // logger.log("INFO", "[아침입니다. " + timeManager.getCurrentDay() + "일차]"); // logger 사용
        System.out.println("\n[아침입니다. " + timeManager.getCurrentDay() + "일차]"); // 사용자에게 보여주는 메시지는 sout 유지


        Person victim = gameService.selectRandomVictim();
        if (victim != null) {
            String dyingMessage = gameService.generateDyingMessage(victim);
            logger.victimFound(victim.getName(), dyingMessage);
            // logger.log("WARN", "💀 사망자 발견: " + victim.getName()); // logger 사용
            // logger.log("WARN", "다잉 메시지: '" + dyingMessage + "'"); // logger 사용
            System.out.println("💀 사망자 발견: " + victim.getName()); // 사용자에게 보여주는 메시지는 sout 유지
            System.out.println("다잉 메시지: '" + dyingMessage + "'"); // 사용자에게 보여주는 메시지는 sout 유지

        } else {
            // logger.log("INFO", "밤 사이에 아무 일도 일어나지 않았습니다."); // logger 사용
             System.out.println("밤 사이에 아무 일도 일어나지 않았습니다."); // 사용자에게 보여주는 메시지는 sout 유지
        }
    }

    private void runDayPhase() {
        // logger.log("INFO", "\n행동을 선택하세요: 1. 조사, 2. 지목"); // logger 사용
        System.out.println("\n행동을 선택하세요: 1. 조사, 2. 지목"); // 사용자에게 보여주는 메시지는 sout 유지

        int choice = getUserChoice();

        if (choice == 1) {
            handleInvestigation();
        } else if (choice == 2) {
            handleAccusation();
        } else {
            // logger.log("WARN", "잘못된 선택입니다."); // logger 사용
            System.out.println("잘못된 선택입니다."); // 사용자에게 보여주는 메시지는 sout 유지
        }
    }

    private int getUserChoice() {
        // logger.log("INFO", "숫자를 입력해주세요."); // logger 사용
         while (!scanner.hasNextInt()) {
            System.out.println("숫자를 입력해주세요."); // 사용자에게 보여주는 메시지는 sout 유지
            scanner.next(); // 잘못된 입력 consume
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return choice;
    }

    private void handleInvestigation() {
        // logger.log("INFO", "조사할 사람의 이름을 입력하세요:"); // logger 사용
        System.out.println("조사할 사람의 이름을 입력하세요:"); // 사용자에게 보여주는 메시지는 sout 유지
        String targetName = scanner.nextLine();
        Person target = gameService.findParticipantByName(targetName);

        if (target != null) {
            gameService.investigatePerson(target);
        } else {
            // logger.log("WARN", "해당 이름의 참가자가 없습니다."); // logger 사용
            System.out.println("해당 이름의 참가자가 없습니다."); // 사용자에게 보여주는 메시지는 sout 유지
        }
    }

    private void handleAccusation() {
        // logger.log("INFO", "지목할 사람의 이름을 입력하세요:"); // logger 사용
        System.out.println("지목할 사람의 이름을 입력하세요:"); // 사용자에게 보여주는 메시지는 sout 유지
        String targetName = scanner.nextLine();
        Person target = gameService.findParticipantByName(targetName);

        if (target != null) {
            boolean success = gameService.accusePerson(target);
            if (success) {
                // logger.log("INFO", "🎉 " + target.getName() + "은(는) 마피아였습니다! 시민팀 승리!"); // logger 사용
                 System.out.println("🎉 " + target.getName() + "은(는) 마피아였습니다! 시민팀 승리!"); // 사용자에게 보여주는 메시지는 sout 유지
            } else {
                // logger.log("INFO", "🤔 " + target.getName() + "은(는) 마피아가 아니었습니다."); // logger 사용
                 System.out.println("🤔 " + target.getName() + "은(는) 마피아가 아니었습니다."); // 사용자에게 보여주는 메시지는 sout 유지
                timeManager.passNight(); // 지목 실패 시 밤이 지나도록 처리
                timeManager.printTimeMessage(Time.Evening);
            }
             gameService.removeParticipant(target); // 지목된 사람 제거
        } else {
            // logger.log("WARN", "해당 이름의 참가자가 없습니다."); // logger 사용
            System.out.println("해당 이름의 참가자가 없습니다."); // 사용자에게 보여주는 메시지는 sout 유지
        }
    }

    private void endGame() {
        boolean success = gameService.isMafiaCaught();
        logger.gameEnd(success, timeManager.getCurrentDay());
        // logger.log("INFO", "\n=== 게임 종료 ==="); // logger 사용
        System.out.println("\n=== 게임 종료 ==="); // 사용자에게 보여주는 메시지는 sout 유지

        if (success) {
            // logger.log("INFO", "🥳 마피아를 잡았습니다! 게임 승리!"); // logger 사용
             System.out.println("🥳 마피아를 잡았습니다! 게임 승리!"); // 사용자에게 보여주는 메시지는 sout 유지
        } else {
            // logger.log("INFO", "😔 마피아를 찾지 못했습니다. 게임 패배..."); // logger 사용
            System.out.println("😔 마피아를 찾지 못했습니다. 게임 패배..."); // 사용자에게 보여주는 메시지는 sout 유지
        }
    }
}
