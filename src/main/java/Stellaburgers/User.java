package Stellaburgers;

import com.github.javafaker.Faker;
import io.qameta.allure.Allure;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Locale;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String email;
    private String password;
    private String name;

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public static User getRandomUser(){
        Faker faker = new Faker(new Locale("en"));
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        String name = faker.name().firstName();
        return new User(email,password,name);
    }



}