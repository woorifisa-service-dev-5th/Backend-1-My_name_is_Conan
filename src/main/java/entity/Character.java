package entity;

public class Character {
    private String name;
    private String hair;
    private String clothes;
    private String shoes;

    public Character(String name, String hair, String clothes, String shoes) {
        this.name = name;
        this.hair = hair;
        this.clothes = clothes;
        this.shoes = shoes;
    }

    // Getter 메서드
    public String getName() {
        return name;
    }

    public String getHair() {
        return hair;
    }

    public String getClothes() {
        return clothes;
    }

    public String getShoes() {
        return shoes;
    }
}
