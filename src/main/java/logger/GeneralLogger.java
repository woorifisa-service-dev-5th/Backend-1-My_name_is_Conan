package logger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 간소화된 게임 로거 클래스
 * - 콘솔 출력과 파일 저장을 하나의 클래스로 통합
 * - 핵심 로깅 기능만 유지
 */
public class GeneralLogger {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final String logFile = "logs/game.log";
    
    /**
     * 기본 로그 메서드 - 콘솔과 파일에 동시 기록
     */
    private void log(String level, String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = String.format("[%s] [%s] %s", timestamp, level, message);
        
        // 콘솔 출력
        System.out.println(logEntry);
        
        // 파일 저장
        saveToFile(logEntry);
    }
    
    // === 게임 이벤트 로깅 메서드들 ===
    
    public void gameStart(int players, String mode) {
        log("INFO", String.format("🎮 게임 시작 - 플레이어: %d명, 모드: %s", players, mode));
    }
    
    public void gameEnd(boolean success, int rounds) {
        String result = success ? "성공 🎉" : "실패 ❌";
        log("INFO", String.format("게임 종료 - 결과: %s, 라운드: %d", result, rounds));
    }
    
    public void newDay(int day) {
        log("INFO", String.format("=== %d일차 아침 ===", day));
    }
    
    public void victimFound(String victim, String dyingMessage) {
        log("WARN", String.format("💀 피해자 발견: %s, 다잉메시지: '%s'", victim, dyingMessage));
    }
    
    public void investigation(String detective, String target, String trait) {
        log("INFO", String.format("🔍 %s가 %s 조사 → %s", detective, target, trait));
    }
    
    public void accusation(String detective, String suspect) {
        log("WARN", String.format("👮 %s가 %s를 범인으로 지목!", detective, suspect));
    }
    
    public void success(String culprit, int rounds) {
        log("INFO", String.format("🎯 범인 검거 성공! %s를 %d라운드만에 해결!", culprit, rounds));
    }
    
    public void failure(String wrongSuspect, String newVictim) {
        log("ERROR", String.format("❌ 잘못된 지목: %s, 새로운 피해자: %s", wrongSuspect, newVictim));
    }
    
    public void error(String message) {
        log("ERROR", "⚠️ 오류: " + message);
    }
    
    // === 유틸리티 메서드들 ===
    
    /**
     * 파일에 로그 저장
     */
    private void saveToFile(String message) {
        try {
            // logs 디렉토리 생성
            java.nio.file.Files.createDirectories(java.nio.file.Paths.get("logs"));
            
            // 파일에 추가
            try (FileWriter writer = new FileWriter(logFile, true)) {
                writer.write(message + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("파일 저장 실패: " + e.getMessage());
        }
    }
}