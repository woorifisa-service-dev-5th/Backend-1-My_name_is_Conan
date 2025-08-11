package entity;

import types.PersonType;
import types.Gender;
import java.util.ArrayList;
import java.util.List;

public class Player extends Person {
    private List<String> record;

    public Player(String name,Gender gender) {
        super(name, gender, PersonType.Detective); // Calls the constructor of the parent class (Person)
        this.record = new ArrayList<>(); // Initializes the record list
    }

    public List<String> getRecord() {
        return record;
    }

    public void addRecord(String newRecord) {
        this.record.add(newRecord);
    }
}