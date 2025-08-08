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
    private Sex sex; // Gson will use a setter to fill this
    private Color shoes;
    private PersonType personType;
    private boolean isDead;

    private static final Random RANDOM = new Random();

    // Gson will use this default constructor.
    public Person() {}

    // You can also use this constructor for manual creation.
    public Person(String name, Sex sex) {
        this.name = name;
        this.sex = sex;
    }

    public Person(String name, Sex sex, PersonType personType) {
        this.name = name;
        this.sex = sex;
        this.personType = personType;
    }

    // This method is used by GameService to assign random attributes
    public void randomizeAttributes() {
        this.hair = HairType.values()[RANDOM.nextInt(HairType.values().length)];
        this.outer = Color.values()[RANDOM.nextInt(Color.values().length)];
        this.inner = Color.values()[RANDOM.nextInt(Color.values().length)];
        this.pants = Color.values()[RANDOM.nextInt(Color.values().length)];
        this.socks = Color.values()[RANDOM.nextInt(Color.values().length)];
        this.shoes = Color.values()[RANDOM.nextInt(Color.values().length)];
    }

    // Getters and setters for all attributes are required for Gson and GameService
    public String getName() { return name; }
    public Sex getSex() { return sex; }
    public PersonType getPersonType() { return personType; }

    public void setName(String name) { this.name = name; }
    public void setSex(Sex sex) { this.sex = sex; } // crucial for Gson
    public void setPersonType(PersonType personType) { this.personType = personType; }
    public void setDead(boolean dead) { isDead = dead; }
    // ... add setters for other attributes if needed

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", hair=" + hair +
                ", outer=" + outer +
                ", inner=" + inner +
                ", pants=" + pants +
                ", socks=" + socks +
                ", shoes=" + shoes +
                ", personType=" + personType +
                ", isDead=" + isDead +
                '}';
    }
}