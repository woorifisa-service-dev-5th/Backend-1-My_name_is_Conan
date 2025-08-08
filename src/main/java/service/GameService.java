package service;

import entity.Person;
import entity.Game;
import types.PersonType;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GameService {

    private Game currentGame; // 현재 게임 상태를 관리할 Game 객체

    private final PersonParser personParser = new PersonParser();
    private final Random random = new Random();

    // game 참가자 생성
    public List<Person> createGameParticipants(String playerName) {

        List<Person> allPersonnel = personParser.loadPersonnel();

        List<Person> participants = allPersonnel.stream()
                .filter(p -> !p.getName().equalsIgnoreCase(playerName))
                .collect(Collectors.toList());


        int criminalIndex = random.nextInt(participants.size());
        participants.get(criminalIndex).setPersonType(PersonType.Criminal);
        participants.get(criminalIndex).randomizeAttributes();

        for (int i = 0; i < participants.size(); i++) {
            if (i != criminalIndex) {
                participants.get(i).setPersonType(PersonType.Commoner);
                participants.get(i).randomizeAttributes();
            }
        }
        return participants;
    }

    // 게임 초기화 및 참가자 설정
    public void initializeGame(List<Person> participants) {
        this.currentGame = new Game(participants);
    }

    // 랜덤으로 사망자 선택
    public Person selectRandomVictim() {
        List<Person> livingParticipants = currentGame.getParticipants().stream()
                .filter(person -> !person.isDead()) // isDead 상태를 활용 (Person 클래스에 추가 필요)
                .collect(Collectors.toList());

        if (livingParticipants.isEmpty()) {
            return null; // 살아있는 참가자가 없으면 null 반환
        }

        Random random = new Random();
        int randomIndex = random.nextInt(livingParticipants.size());
        Person victim = livingParticipants.get(randomIndex);
        victim.setDead(true); // 사망 처리 (Person 클래스에 setDead 메소드 추가 필요)
        return victim;
    }

    // 다잉 메시지 생성 (임시 구현)
    public String generateDyingMessage(Person victim) {
        // 사망자의 특성 등을 활용하여 다잉 메시지 생성 로직 추가
        return "법인의 머리색은 빨강색"; // 예시 다잉 메시지
    }

    // 참가자 제거
    public void removeParticipant(Person person) {
        currentGame.removeParticipant(person);
    }

    // 게임 종료 조건 확인
    public boolean isGameEnd() {
        return currentGame.checkGameOverCondition(); // Game 객체에 위임
    }

    // 마피아가 잡혔는지 확인 (임시 구현)
    public boolean isMafiaCaught() {
        // 마피아가 모두 제거되었는지 등의 조건 확인 로직 추가
        long mafiaCount = currentGame.getParticipants().stream()
                .filter(person -> person.getPersonType() == PersonType.Criminal && !person.isDead())
                .count();
        return mafiaCount == 0;
    }

    // 이름으로 참가자 찾기
    public Person findParticipantByName(String name) {
        for (Person person : currentGame.getParticipants()) {
            if (person.getName().equalsIgnoreCase(name)) {
                return person;
            }
        }
        return null;
    }

    // 참가자 조사 (특성 공개 등)
    public void investigatePerson(Person person) {
        System.out.println(person.getName() + "을(를) 조사합니다.");
        System.out.println("성별: " + person.getSex());
        System.out.println("머리 색깔: " + person.getHair());
        // 추가적인 특성 정보 출력
    }

    // 참가자 지목 및 마피아 여부 확인
    public boolean accusePerson(Person person) {
        System.out.println(person.getName() + "을(를) 지목합니다.");
        if (person.getPersonType() == PersonType.Criminal) {
            currentGame.setMafiaCaught(true);
            return true;
        } else {
            return false;
        }
    }

    // 현재 게임 상태 반환
    public Game getCurrentGame() {
        return currentGame;
    }
}
