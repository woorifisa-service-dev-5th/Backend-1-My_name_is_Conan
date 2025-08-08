package service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import entity.Person;

public class PersonParser {
	public List<Person> loadFromResources() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("PersonInfo.json")) {
            if (in == null) throw new IllegalStateException("PersonInfo.json not found in resources");

            // Gson 인스턴스
            Gson gson = new Gson();

            // List<Person> 타입 지정
            Type personListType = new TypeToken<List<Person>>(){}.getType();

            // JSON → List<Person> 변환
            return gson.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), personListType);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load PersonInfo.json", e);
        }
    }

	 
}
