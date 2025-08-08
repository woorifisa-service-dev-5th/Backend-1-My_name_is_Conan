package logger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 🎮 간단한 게임용 로거 클래스
 * - 콘솔 출력과 파일 저장 기능을 통합 제공
 * - 로그 레벨별 로깅 지원 (INFO, WARN, ERROR)
 * - logs/game.log에 로그 저장
 */
public class GeneralLogger {
    // 로그 출력에 사용할 시간 포맷 (시:분:초)
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    // 로그 파일 경로 (기본 logs/game.log)
    private final String logFile = "logs/game.log";

    /**
     * 🔧 공통 로그 출력 함수
     * - 콘솔 출력 + 파일 저장을 함께 처리
     * 
     * @param level 로그 수준 (INFO, WARN, ERROR 등)
     * @param message 출력할 메시지 (이모지/정보 포함)
     */
    private void log(String level, String message) {
        // 현재 시간 형식화
        String timestamp = LocalDateTime.now().format(formatter);

        // 로그 문자열 생성: [12:30:00] [INFO] 메시지
        String logEntry = String.format("[%s] [%s] %s", timestamp, level, message);

        // 콘솔에 로그 출력
        System.out.println(logEntry);

        // 로그 파일에 저장
        saveToFile(logEntry);
    }


    /**
     * 게임 시작 로그
     * @param players 참여자 수
     */
    public void gameStart(int players) {
        log("INFO", String.format("🎮 게임 시작 - 플레이어: %d명", players));
    }

    /**
     * 게임 종료 로그
     * @param success 성공 여부
     * @param rounds 진행된 라운드 수
     */
    public void gameEnd(boolean success, int rounds) {
        String result = success ? "성공 🎉" : "실패 ❌";
        log("INFO", String.format("게임 종료 - 결과: %s", result, rounds));
    }

    /**
     * 새로운 날 시작 로그
     * @param day 몇 일차인지
     */
    public void newDay(int day) {
        log("INFO", String.format("=== %d일차 아침 ===", day));
    }

    /**
     * 피해자 발견 로그
     * @param victim 피해자 이름
     * @param dyingMessage 피해자의 유언
     */
    public void victimFound(String victim, String dyingMessage) {
        log("WARN", String.format("💀 피해자 발견: %s, 다잉메시지: '%s'", victim, dyingMessage));
    }

    /**
     * 조사 결과 로그
     * @param detective 조사자
     * @param target 조사 대상
     * @param trait 밝혀진 특징
     */
    public void investigation(String detective, String target, String trait) {
        log("INFO", String.format("🔍 %s가 %s 조사 → %s", detective, target, trait));
    }

    /**
     * 지목 로그
     * @param detective 지목한 탐정
     * @param suspect 지목된 용의자
     */
    public void accusation(String detective, String suspect) {
        log("WARN", String.format("👮 %s가 범인이다!", detective, suspect));
    }

    /**
     * 범인 검거 성공 로그
     * @param culprit 검거된 범인
     * @param rounds 걸린 라운드 수
     */
    public void success(String culprit, int rounds) {
        log("INFO", String.format("🎯 범인 검거 성공! %s이 살인자였습니다.", culprit, rounds));
    }

    /**
     * 범인 지목 실패 로그
     * @param wrongSuspect 잘못 지목한 인물
     * @param newVictim 새로운 피해자
     */
    public void failure(String wrongSuspect, String newVictim) {
        log("ERROR", String.format("❌ %s은 범인이 아니였습니다, %s이 살해당했습니다.", wrongSuspect, newVictim));
    }

    /**
     * 일반 오류 로그
     * @param message 오류 메시지
     */
    public void error(String message) {
        log("ERROR", "⚠️ 오류: " + message);
    }


    /**
     * 로그 파일에 한 줄 저장
     * - logs 디렉토리가 없으면 생성
     * - game.log 파일에 로그 한 줄씩 추가
     * 
     * @param message 저장할 로그 한 줄
     */
    private void saveToFile(String message) {
        try {
            // logs 디렉토리 생성 (없을 경우)
            java.nio.file.Files.createDirectories(java.nio.file.Paths.get("logs"));

            // 로그 파일에 한 줄 추가 쓰기
            try (FileWriter writer = new FileWriter(logFile, true)) {
                writer.write(message + System.lineSeparator());
            }
        } catch (IOException e) {
            // 파일 저장 실패 시 에러 메시지 출력
            System.err.println("파일 저장 실패: " + e.getMessage());
        }
    }
}
