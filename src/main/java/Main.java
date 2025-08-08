import entity.Person;
import service.GameService;
import types.PersonType;
import types.Sex;

public class Main {
	public static void main(String[] args) {
        GameService gameService = new GameService();
        System.out.println(gameService.createGameParticipants("김현진"));
	}
}
