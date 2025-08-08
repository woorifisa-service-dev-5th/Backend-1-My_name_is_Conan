package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entity.Person;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class PersonParser {
    public List<Person> loadPersonnel() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("PersonInfo.json")) {
            if (in == null) {

                throw new IllegalStateException("PersonInfo.json not found");
            }
            Gson gson = new Gson();
            Type personListType = new TypeToken<List<Person>>(){}.getType();
            return gson.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), personListType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load PersonInfo.json", e);
        }
    }
}