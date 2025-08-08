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

    // ===== Getters =====
    public String getName() { return name; }
    public HairType getHair() { return hair; }
    public Color getOuter() { return outer; }
    public Color getInner() { return inner; }
    public Color getPants() { return pants; }
    public Color getSocks() { return socks; }
    public Sex getSex() { return sex; }
    public Color getShoes() { return shoes; }
    public PersonType getPersonType() { return personType; }
    public boolean isDead() { return isDead; }

    // ===== Setters =====
    public void setName(String name) { this.name = name; }
    public void setHair(HairType hair) { this.hair = hair; }
    public void setOuter(Color outer) { this.outer = outer; }
    public void setInner(Color inner) { this.inner = inner; }
    public void setPants(Color pants) { this.pants = pants; }
    public void setSocks(Color socks) { this.socks = socks; }
    public void setSex(Sex sex) { this.sex = sex; } // crucial for Gson
    public void setShoes(Color shoes) { this.shoes = shoes; }
    public void setPersonType(PersonType personType) { this.personType = personType; }
    public void setDead(boolean dead) { isDead = dead; }

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
