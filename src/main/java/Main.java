import entity.Person;
import service.GameService;
import service.TimeManager;
import logger.GeneralLogger;
import controller.GameController; // GameController 임포트

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GameService gameService = new GameService();
        TimeManager timeManager = new TimeManager();
        GeneralLogger logger = new GeneralLogger();
        Scanner scanner = new Scanner(System.in);

        // 게임 참가자 생성 및 설정
        List<Person> participants = gameService.createGameParticipants("김현진"); // GameService의 createGameParticipants 사용

        // GameController 생성 및 게임 시작
        GameController gameController = new GameController(gameService, timeManager, logger, scanner);
        gameController.startGame(participants);

        scanner.close(); // 게임 종료 후 스캐너 닫기
    }
}
