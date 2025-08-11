package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import entity.Character;

public class DetectiveGame {
    private List<Character> characters;
    private Character murderer;
    private Character victim;
    private Character suspect;
    private String dyingMessage;
    private String detectiveName;
    private int lives;
    private Scanner scanner;

    public DetectiveGame() {
        // 게임 초기 세팅
        this.characters = new ArrayList<>(Arrays.asList(
                new Character("김현진", "장발이야", "운동복을 입었어", "나이키를 신었어"),
                new Character("사혜빈", "파란 모자를 썼어", "양복을 입었어", "구두를 신었어"),
                new Character("김민영", "파마를 했어", "무스탕을 입었어", "아무것도 안 신고 있었어"),
                new Character("박소희", "스님 머리야", "셔츠를 입었어", "슬리퍼 신었어"),
                new Character("김종민", "단발머리야", "치마를 입었어", "부츠를 신었어"),
                new Character("이종혁", "투블럭을 했어", "반팔티를 입었어", "크록스를 신었어"),
                new Character("김영재", "허리까지 머리카락이 있어", "원피스를 입었어", "힐을 신었어")
        ));
        this.lives = 2;
        this.scanner = new Scanner(System.in);
        resetGame();
    }

    public void resetGame() {
        // 게임 재시작 시 초기화
        this.lives = 2;
        this.murderer = null;
        this.victim = null;
        this.suspect = null;
        this.dyingMessage = null;
        this.detectiveName = null;

        // 캐릭터 리스트 재구성 (새 게임을 위해 원래대로 복원)
        this.characters = new ArrayList<>(Arrays.asList(
                new Character("김현진", "장발이야", "운동복을 입었어", "나이키를 신었어"),
                new Character("사혜빈", "파란 모자를 썼어", "양복을 입었어", "구두를 신었어"),
                new Character("김민영", "파마를 했어", "무스탕을 입었어", "아무것도 안 신고 있었어"),
                new Character("박소희", "스님 머리야", "셔츠를 입었어", "슬리퍼 신었어"),
                new Character("김종민", "단발머리야", "치마를 입었어", "부츠를 신었어"),
                new Character("이종혁", "투블럭을 했어", "반팔티를 입었어", "크록스를 신었어"),
                new Character("김영재", "허리까지 머리카락이 있어", "원피스를 입었어", "힐을 신었어")
        ));
    }

    public void showIntro() throws InterruptedException {
        System.out.println("탐정 게임에 오신 것을 환영합니다.");
        Thread.sleep(1000);

        System.out.println("탐정님의 이름을 알려주세요!");
        this.detectiveName = scanner.nextLine().trim();

        // 랜덤으로 희생자 지정 및 목록에서 제거
        Random random = new Random();
        this.victim = characters.remove(random.nextInt(characters.size()));

        // 용의자 목록에서 범인 지정
        this.murderer = characters.get(random.nextInt(characters.size()));

        // 다잉 메시지 생성
        List<String> dyingMessageTypes = Arrays.asList("hair", "clothes", "shoes");
        String dyingMessageType = dyingMessageTypes.get(random.nextInt(dyingMessageTypes.size()));
        if (dyingMessageType.equals("hair")) {
            this.dyingMessage = String.format("머리스타일은 %s 윽..☠️", murderer.getHair());
        } else if (dyingMessageType.equals("clothes")) {
            this.dyingMessage = String.format("옷은 %s 윽..☠️", murderer.getClothes());
        } else {
            this.dyingMessage = String.format("신발은 %s 윽..☠️", murderer.getShoes());
        }

        // 게임 스토리 출력 (파이썬 코드와 동일)
        System.out.println("########################################");
        System.out.println("#######        평화로운 해커톤             ");
        System.out.println("########################################");
        Thread.sleep(1500);

        System.out.printf("\n코딩에 몰두하던 %s, 눈이 피로해지기 시작한다. 해커톤의 열기가 고조될수록, 정신은 점점 흐릿해진다.\n", detectiveName);
        Thread.sleep(1000);
        System.out.printf("%s: 이 해커톤, 너무 평화롭기만 하군... 뭔가 재밌는 사건이라도 터져야 할 텐데. 뭐, 코드가 몽땅 날아가는 일이라든지.\n", detectiveName);
        Thread.sleep(1000);
        System.out.printf("%s: 하하, 탐정님! 그런 무서운 말씀은 제발 그만하세요. 상상만 해도 아찔하네요... 그런 일은 절대로 일어나지 않을 거예요.\n", victim.getName());
        Thread.sleep(1000);
        System.out.printf("%s: 뭐, 그렇긴 하겠지. 아, 참고로 나는 명탐정 %s! 사건이 터지면 언제든 나를 찾아.\n", detectiveName, detectiveName);
        Thread.sleep(1000);
        System.out.printf("%s: 하하, 명탐정님! 알겠습니다. 그런데 요즘 제 노트북을 자꾸 누가 훔쳐보는 것 같아서 신경 쓰이긴 해요. 집 앞 카페에서 코딩하다 보면 말이죠...\n", victim.getName());
        Thread.sleep(1000);
        System.out.println("그렇게 둘은 헤어졌고, 그로부터 10분 후 갑자기 정전이 일어나게 되는데..\n");
        Thread.sleep(1000);

        System.out.println("########################################");
        System.out.println("#######        사건 발생             ");
        System.out.println("########################################");
        Thread.sleep(1000);

        System.out.printf("갑자기 날카로운 비명 소리가 울려 퍼졌다. %s씨의 노트북 화면이 순식간에 블루스크린으로 바뀌었다.\n", victim.getName());
        Thread.sleep(1000);
        System.out.printf("그 충격에 %s씨는 정신을 잃고 그대로 쓰러졌다...\n", victim.getName());
        Thread.sleep(1000);

        System.out.printf("\n현장은 순식간에 혼란에 빠졌고, 바닥에는 %s씨의 메시지가 남겨져 있었다.\n", victim.getName());
        Thread.sleep(1000);

        System.out.println("\n================사망하지는 않았지만 이게 다잉메시지?!================");
        System.out.printf("                   \"%s\"\n", dyingMessage);
        System.out.println("=================================================================");
        Thread.sleep(1000);

        System.out.printf("용의자는 총 %d명입니다.\n", characters.size());
        Thread.sleep(1000);

        System.out.println("그중, 범인은 바로 이 자리에 있을 것입니다...");
        Thread.sleep(1000);
        System.out.printf("%s님의 임무는 범인을 찾아내는 것입니다. 진실을 밝혀내세요. 기회는 2번입니다.\n", detectiveName);
        Thread.sleep(1000);

        System.out.println("########################################");
        System.out.println("#######        추리 시작             ");
        System.out.println("########################################");
        Thread.sleep(1000);
    }

    public void investigate() throws InterruptedException {
        System.out.println("용의자와 대화를 나누고 인상착의를 수집하세요...\n");
        for (int i = 0; i < characters.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, characters.get(i).getName());
        }
        Thread.sleep(1000);

        System.out.print("\n누구를 조사하시겠습니까? 이름을 입력하세요: ");
        String choiceName = scanner.nextLine().trim();
        Thread.sleep(1000);

        boolean found = false;
        for (Character character : characters) {
            if (character.getName().equals(choiceName)) {
                System.out.printf("%s에 대한 조사를 시작할게요.\n 이 사람은 %s. 그리고 %s. 또한, %s\n",
                        character.getName(), character.getHair(), character.getClothes(), character.getShoes());
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("잘못된 입력입니다! 시간이 얼마 남지 않았습니다, 다시 시도해주세요!");
            System.out.println("범인은 아직도 우리 곁에 있어요. 서둘러 진실을 밝혀내야 합니다!");
            System.out.printf("%s: 좋아, 이번엔 잘 선택해보자.\n", detectiveName);
            investigate();
        }
    }

    public boolean matchDyingMessage(Character character) {
        String hairMsg = String.format("머리스타일은 %s 윽..☠️", character.getHair());
        String clothesMsg = String.format("옷은 %s 윽..☠️", character.getClothes());
        String shoesMsg = String.format("신발은 %s 윽..☠️", character.getShoes());

        return dyingMessage.equals(hairMsg) || dyingMessage.equals(clothesMsg) || dyingMessage.equals(shoesMsg);
    }

    public String promptChoice(String prompt) {
        while (true) {
            System.out.print(prompt);
            String choice = scanner.nextLine().trim();
            if (choice.equals("네") || choice.equals("아니오")) {
                return choice;
            }
            System.out.println("잘못된 입력입니다. 네 또는 아니오만 입력해 주세요.");
        }
    }

    public void accuse() throws InterruptedException {
        System.out.println("\n범인을 지목할 시간입니다.");
        for (int i = 0; i < characters.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, characters.get(i).getName());
        }

        System.out.print("\n누구를 범인으로 지목하시겠습니까? 이름을 입력하세요: ");
        String choiceName = scanner.nextLine().trim();
        System.out.printf("%s: 범인은 바로 %s씨야\n", detectiveName, choiceName);

        boolean found = false;
        for (Character character : characters) {
            if (character.getName().equals(choiceName)) {
                suspect = character;
                checkOutcome();
                found = true;
                return;
            }
        }

        if (!found) {
            System.out.println("탐정님, 그건 잘못된 선택입니다! 시간이 얼마 남지 않았습니다, 다시 시도해주세요!");
            System.out.println("범인은 아직도 우리 곁에 있어요. 서둘러 진실을 밝혀내야 합니다!");
            System.out.printf("%s: 좋아, 이번엔 잘 선택해보자.\n\n", detectiveName);
            Thread.sleep(1000);
            accuse();
        }
    }

    public void checkOutcome() throws InterruptedException {
        if (matchDyingMessage(suspect)) {
            // 승리 로직 (파이썬 코드와 동일한 ASCII 아트 사용)
            System.out.println("""
----------------------------------------------------------------------------------
... (WIN ASCII ART) ...
----------------------------------------------------------------------------------""");
            Thread.sleep(1000);
            System.out.println("###############################################");
            System.out.println("#######        당신은 역시 명탐정!!          ");
            System.out.println("###############################################");
            Thread.sleep(1000);
            System.out.printf("\n정답입니다! 범인은 바로 %s씨였습니다!\n", suspect.getName());
            Thread.sleep(1000);
            System.out.printf("당신의 추리는 완벽했습니다, 역시 명탐정 %s 답군요.\n", detectiveName);
            Thread.sleep(1000);
            System.out.printf("\n%s: 왜 노트북을 망가뜨렸습니까?? %s씨의 노트북을 왜 그렇게 했죠?\n", detectiveName, victim.getName());
            Thread.sleep(1000);
            System.out.printf("%s: 그게 사실... %s씨의 팀이 해커톤 우승을 못하게 하려고... 그래서 홧김에.. 죄송합니다🥹\n", suspect.getName(), victim.getName());
            Thread.sleep(1000);
            System.out.printf("\n%s씨는 끝내 자신의 범행을 인정했고, 우리 FISA 해커톤에서 퇴출당했습니다.\n", suspect.getName());
            Thread.sleep(1000);
            System.out.println("사건은 해결되었고, 모든 사람들이 안도의 한숨을 내쉬었습니다. 당신의 활약 덕분입니다.");
            Thread.sleep(1000);
            askRestart();
        } else {
            lives--;
            if (lives > 0) {
                System.out.printf("\n%s: 무슨 소리야? 내 인상착의를 봐... 당신 명탐정 맞아? 💢💢💢\n", suspect.getName());
                System.out.printf("\n틀렸습니다... %s씨는 범인이 아닙니다. 남은 기회는 %d번입니다.\n", suspect.getName(), lives);
                System.out.println("시간이 얼마 남지 않았어요. 신중하게 선택해주세요.");
                Thread.sleep(1000);
                String choice = promptChoice("용의자들의 인상착의를 다시 보겠습니까? (네/아니오): ");
                if (choice.equals("네")) {
                    System.out.printf("%s: 좋았어... 다시 차근차근 보자\n\n", detectiveName);
                    Thread.sleep(1000);
                    mainFlow();
                } else {
                    accuse();
                }
            } else {
                // 패배 로직 (파이썬 코드와 동일한 ASCII 아트 사용)
                System.out.println("""
----------------------------------------------------------------------------------
... (LOSE ASCII ART) ...
----------------------------------------------------------------------------------""");
                Thread.sleep(1000);
                System.out.println("########################################");
                System.out.println("#######         추리 실패......            ");
                System.out.println("########################################");
                Thread.sleep(1000);
                System.out.printf("\n%s: 명탐정 별거 없네~~~ 해커톤 우승은 내꺼다!!!\n", murderer.getName());
                Thread.sleep(1000);
                System.out.printf("\n안타깝습니다... 범인은 %s씨였습니다. 모든 기회를 다 써버렸습니다.\n", murderer.getName());
                Thread.sleep(1000);
                System.out.printf("추리는 실패했습니다. %s 탐정님, 이번 사건은 당신에게 큰 상처로 남게 될 것입니다.\n", detectiveName);
                Thread.sleep(1000);
                System.out.println("당신은 이 미스터리를 풀 기회를 잃었습니다...");
                Thread.sleep(1000);
                System.out.println("하지만, 진정한 탐정은 실패에서도 배웁니다. 다음엔 꼭 진실을 밝혀내세요.");
                Thread.sleep(1000);
                askRestart();
            }
        }
    }

    public void askRestart() {
        String choice = promptChoice("게임을 다시 시작하시겠습니까? (네/아니오): ");
        if (choice.equals("네")) {
            resetGame();
            play();
        } else {
            System.out.println("게임을 종료합니다. 감사합니다!");
            scanner.close();
        }
    }

    public void mainFlow() throws InterruptedException {
        while (true) {
            investigate();
            if (promptChoice("\n계속 조사하시겠습니까? (네/아니오): ").equals("아니오")) {
                break;
            }
        }
        accuse();
    }

    public void play() {
        try {
            showIntro();
            mainFlow();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        DetectiveGame game = new DetectiveGame();
        game.play();
    }
}
