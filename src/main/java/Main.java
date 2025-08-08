import entity.Person;
import types.PersonType;
import types.Sex;

public class Main {
	public static void main(String[] args) {
		Person p1 = new Person("김민영", Sex.Female, PersonType.Commoner);
		Person p2 = new Person("홍길동", Sex.Male, PersonType.Criminal);
		
		System.out.println(p1);
		System.out.println(p2);
	}
}
