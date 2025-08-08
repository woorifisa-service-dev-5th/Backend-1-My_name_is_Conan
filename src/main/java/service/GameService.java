package service;

import entity.Person;
import types.PersonType;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
public class GameService {
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
}