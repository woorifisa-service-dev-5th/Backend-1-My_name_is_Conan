package entity;

import types.Color;
import types.HairType;
import types.PersonType;
import types.Sex;

import java.util.Random;

public class Person {
    private String name;
    private HairType hair;
    private Color outer;
    private Color inner;
    private Color pants;
    private Color socks;
    private Sex sex;
    private Color shoes;
    private PersonType personType;
    private boolean isDead;

    private static final Random RANDOM = new Random();

    public Person(String name, Sex sex, PersonType personType) {
        this.name = name;
        this.personType = personType;
        this.sex = sex;
        this.isDead = false;

        // Randomly assign values for other attributes
        this.hair = HairType.values()[RANDOM.nextInt(HairType.values().length)];
        this.outer = Color.values()[RANDOM.nextInt(Color.values().length)];
        this.inner = Color.values()[RANDOM.nextInt(Color.values().length)];
        this.pants = Color.values()[RANDOM.nextInt(Color.values().length)];
        this.socks = Color.values()[RANDOM.nextInt(Color.values().length)];
        this.shoes = Color.values()[RANDOM.nextInt(Color.values().length)];
    }

  //getter 만들어줘요~
    
	public String getName() {
		return name;
	}

	public HairType getHair() {
		return hair;
	}

	public Color getOuter() {
		return outer;
	}

	public Color getInner() {
		return inner;
	}

	public Color getPants() {
		return pants;
	}

	public Color getSocks() {
		return socks;
	}

	public Sex getSex() {
		return sex;
	}

	public Color getShoes() {
		return shoes;
	}

	public PersonType getPersonType() {
		return personType;
	}

	public boolean isDead() {
		return isDead;
	}

}
