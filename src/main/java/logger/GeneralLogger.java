package logger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 게임 전체 로깅 기능을 제공하는 GeneralLogger 클래스 (파사드 패턴 적용)
 * - ConsoleLogger, FileLogger를 내부적으로 감싸 복잡성을 숨김
 * - 클라이언트는 단순한 API로 로그 기록 가능
 * 
 * INFO : 게임시작/종료
 * WARN : 플레이어 죽음
 * ERROR : 시스템 오류
 * DEBUG : 상태변화추적
 * 
 * 
 */
public class GeneralLogger {
    // 내부 로깅 시스템들
    private final ConsoleLogger consoleLogger; // 콘솔 출력 담당
    private final FileLogger fileLogger;       // 파일 저장 담당
    private final List<String> logBuffer;      // 메모리 내 로그 버퍼 (세션 로그 저장용)
    private final DateTimeFormatter timeFormatter; // 로그 출력용 시간 포맷터

    private String currentLogLevel = "INFO";         // 현재 로그 레벨 설정
    private String logFilePath = "logs/game_session.log"; // 기본 로그 파일 경로
    private boolean fileLoggingEnabled = true;       // 파일 로깅 활성화 여부

    /**
     * 기본 생성자 - 내부 로거 및 설정 초기화
     */
    public GeneralLogger() {
        this.consoleLogger = new ConsoleLogger();
        this.fileLogger = new FileLogger();
        this.logBuffer = new ArrayList<>();
        this.timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        createLogDirectoryIfNotExists();
    }

    /**
     * 설정 지정 생성자
     * @param enableFileLogging 파일 로깅 사용 여부
     * @param logLevel 초기 로그 레벨
     */
    public GeneralLogger(boolean enableFileLogging, String logLevel) {
        this();
        this.fileLoggingEnabled = enableFileLogging;
        this.currentLogLevel = logLevel;
    }

    /**
     * 게임 시작 로그
     */
    public void logGameStart(int playerCount, String gameMode) {
        String message = String.format("[게임 시작] 플레이어 수: %d명, 모드: %s", playerCount, gameMode);
        
        // 라운드 구분선을 위한 추가 로그
        writeLog("INFO", "GAME_START", message);
    }

    /**
     * 게임 종료 로그
     */
    public void logGameEnd(boolean isSuccess, int totalRounds, long elapsedTime) {
        String result = isSuccess ? "성공" : "실패";
        String message = String.format("[게임 종료] 결과: %s, 총 라운드: %d, 경과시간: %d초", result, totalRounds, elapsedTime / 1000);
        
        writeLog("INFO", "GAME_END", message);
    }

    /**
     * 밤이 지나간 로그
     */
    public void logNightPassed(int currentNight) {
        String message = String.format("[시간 경과] %d번째 밤이 지났습니다.", currentNight);
        writeLog("INFO", "NIGHT_CYCLE", message);
    }

    /**
     * 라운드 시작 로그
     */
    public void logNewRound(int roundNumber) {
    	String message = String.format("[아침] === %d일차 아침이 되었습니다 ===", roundNumber);
        writeLog("INFO", "MORNING", message);
 
        writeLog("DEBUG", "SEPARATOR", "=".repeat(50));
    }

    /**
     * 피해자 발견 로그
     */
    public void logVictimFound(String victimName, String dyingMessage) {
        String message = String.format("[사건 발생] 피해자: %s 발견, 다잉메시지: '%s'", victimName, dyingMessage);
        writeLog("WARN", "VICTIM_FOUND", message);
        writeLog("WARN", "DYING_MESSAGE", String.format("\uD83D\uDD0D 단서: %s", dyingMessage));
    }

    /**
     * 아무 일 없는 밤 로그
     */
    public void logPeacefulNight(int nightNumber) {
        String message = String.format("[평화로운 밤] %d번째 밤, 아무 일도 일어나지 않음", nightNumber);
        writeLog("INFO", "PEACEFUL_NIGHT", message);
    }

    /**
     * 조사 로그
     */
    public void logInvestigation(String detectiveName, String targetName, String discoveredTrait) {
        String message = String.format("[조사 활동] %s 탐정이 %s을(를) 조사 → 발견된 특성: %s", detectiveName, targetName, discoveredTrait);
        writeLog("INFO", "INVESTIGATION", message);
    }

    /**
     * 용의자 지목 로그
     */
    public void logSuspectAccusation(String detectiveName, String suspectName, String reason) {
        String message = String.format("[범인 지목] %s 탐정이 %s을(를) 지목, 근거: %s", detectiveName, suspectName, reason);
        writeLog("WARN", "ACCUSATION", message);
    }

    /**
     * 용의자 연행 로그
     */
    public void logSuspectArrested(String suspectName, String arrestTime) {
        String message = String.format("[용의자 연행] %s 연행됨 (시각: %s)", suspectName, arrestTime);
        writeLog("INFO", "ARREST", message);
    }

    /**
     * 범인 검거 성공 로그
     */
    public void logCulpritCaught(String culpritName, int solvedInRounds) {
        String message = String.format("[수사 성공] \uD83C\uDF89 진범 %s 검거! %d라운드만에 해결", culpritName, solvedInRounds);
        writeLog("INFO", "SUCCESS", message);
        writeLog("INFO", "CELEBRATION", "축하합니다! 훌륭한 추리력을 보여주셨습니다!");
    }

    /**
     * 범인 검거 실패 로그 (실제 범인을 드러내지 않고 새로운 피해자만 공개)
     * @param wrongSuspect 잘못 지목된 용의자
     * @param additionalVictim 추가 피해자 (실패 후 발생한)
     */
    public void logInvestigationFailed(String wrongSuspect, String additionalVictim) {
        String message = String.format("[수사 실패] ❌ 잘못된 지목: %s, 새로운 피해자: %s 발견", 
                                     wrongSuspect, additionalVictim);
        writeLog("ERROR", "FAILURE", message);
        
        // 실패 원인 분석 로그 (범인 정체는 숨김)
        String analysis = String.format("[사건 계속] 범인은 여전히 활동 중... %s가(이) 추가로 희생되었습니다.", additionalVictim);
        writeLog("ERROR", "FAILURE_ANALYSIS", analysis);
    }


    /**
     * 시스템 오류 로그
     */
    public void logError(String errorType, String errorMessage, String context) {
        String message = String.format("[시스템 오류] 타입: %s, 메시지: %s, 컨텍스트: %s", errorType, errorMessage, context);
        writeLog("ERROR", "SYSTEM_ERROR", message);
    }

    /**
     * 게임 통계 출력 로그
     */
    public void logGameStatistics(int totalPlayers, int alivePlayers, int deadPlayers, int arrestedPlayers) {
        String message = String.format("[게임 통계] 전체: %d명, 생존: %d명, 사망: %d명, 연행: %d명", totalPlayers, alivePlayers, deadPlayers, arrestedPlayers);
        writeLog("INFO", "STATISTICS", message);
    }

    /**
     * 로그 레벨 설정
     */
    public void setLogLevel(String logLevel) {
        this.currentLogLevel = logLevel.toUpperCase();
        writeLog("INFO", "CONFIG", "로그 레벨이 " + logLevel + "로 변경되었습니다.");
    }

    /**
     * 로그 파일 경로 변경
     */
    public void setLogFilePath(String filePath) {
        this.logFilePath = filePath;
        writeLog("INFO", "CONFIG", "로그 파일 경로가 " + filePath + "로 변경되었습니다.");
    }

    /**
     * 현재 로그 버퍼를 파일로 저장
     */
    public boolean saveLogsToFile(String fileName) {
        try {
            fileLogger.saveToFile(fileName, logBuffer);
            writeLog("INFO", "FILE_SAVE", "로그가 " + fileName + "에 저장되었습니다.");
            return true;
        } catch (Exception e) {
            writeLog("ERROR", "FILE_SAVE_ERROR", "파일 저장 실패: " + e.getMessage());
            return false;
        }
    }

    /**
     * 로그 버퍼 초기화
     */
    public void clearLogs() {
        logBuffer.clear();
        writeLog("INFO", "LOG_CLEAR", "로그 버퍼가 초기화되었습니다.");
    }

    /**
     * 로그 디렉토리 없으면 생성
     */
    private void createLogDirectoryIfNotExists() {
        try {
            java.nio.file.Path logDir = java.nio.file.Paths.get("logs");
            if (!java.nio.file.Files.exists(logDir)) {
                java.nio.file.Files.createDirectories(logDir);
            }
        } catch (Exception e) {
            System.err.println("로그 디렉토리 생성 실패: " + e.getMessage());
        }
    }

    /**
     * 실제 로그 기록 (콘솔/파일)
     */
    private void writeLog(String level, String category, String message) {
        if (!shouldLog(level)) return;

        String timestamp = LocalDateTime.now().format(timeFormatter);
        String formattedLog = String.format("[%s] [%s] [%s] %s", timestamp, level, category, message);

        logBuffer.add(formattedLog);
        consoleLogger.log(level, formattedLog);

        if (fileLoggingEnabled) {
            try {
                fileLogger.appendToFile(logFilePath, formattedLog);
            } catch (IOException e) {
                consoleLogger.log("ERROR", "파일 쓰기 실패: " + e.getMessage());
            }
        }
    }

    /**
     * 현재 로그 레벨에 따라 출력 여부 결정
     */
    private boolean shouldLog(String level) {
        switch (currentLogLevel) {
            case "ERROR": return level.equals("ERROR");
            case "WARN": return level.equals("ERROR") || level.equals("WARN");
            case "INFO": return !level.equals("DEBUG");
            case "DEBUG": return true;
            default: return true;
        }
    }
}

/**
 * 콘솔 출력 전담 로거 클래스
 * - 로그 레벨에 따른 색상 처리 포함
 */
class ConsoleLogger {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";

    public void log(String level, String message) {
        System.out.println(addColor(level, message));
    }

    private String addColor(String level, String message) {
        switch (level) {
            case "ERROR": return RED + message + RESET;
            case "WARN": return YELLOW + message + RESET;
            case "INFO": return GREEN + message + RESET;
            case "DEBUG": return CYAN + message + RESET;
            default: return message;
        }
    }
}

/**
 * 파일 출력 전담 로거 클래스
 * - 로그 파일 쓰기 및 롤링 처리
 */
class FileLogger {
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB

    public void appendToFile(String filePath, String message) throws IOException {
        checkAndRollFile(filePath);
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(message + System.lineSeparator());
        }
    }

    public void saveToFile(String fileName, List<String> logs) throws IOException {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (String log : logs) {
                writer.write(log + System.lineSeparator());
            }
        }
    }

    private void checkAndRollFile(String filePath) throws IOException {
        java.io.File file = new java.io.File(filePath);
        if (file.exists() && file.length() > MAX_FILE_SIZE) {
            String backupPath = filePath + ".bak";
            java.nio.file.Files.move(
                java.nio.file.Paths.get(filePath),
                java.nio.file.Paths.get(backupPath),
                java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );
        }
    }
}
