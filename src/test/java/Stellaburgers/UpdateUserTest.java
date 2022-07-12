package Stellaburgers;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UpdateUserTest {
    Faker faker = new Faker();
    public UserClient userClient;
    public User user;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getRandomUser();
        Response response = userClient.create(user);
        accessToken = response.then().extract().path("accessToken");
    }

    @After
    public void tearDown(){
        userClient.deleteUser();
    }

    @Test
    @DisplayName("Проверка изменения данных логина для авторизированного пользователя")
    public void UserAuthorizationUpdateEmailTest() {
        userClient.getDataUser(accessToken);
        user.setEmail(faker.internet().emailAddress());
        Response response = userClient.setDataUserWithToken(accessToken, new User(user.getEmail(), user.getName()));
        response.then().assertThat().body("success", equalTo(true)).and().statusCode(200);
    }

    @Test
    @DisplayName("Проверка изменения данных имени для авторизированного пользователя")
    public void UserAuthorizationUpdateNameTest() {
        userClient.getDataUser(accessToken);
        user.setName(faker.name().firstName());
        Response response = userClient.setDataUserWithToken(accessToken, new User(user.getEmail(), user.getName()));
        response.then().assertThat().body("success", equalTo(true)).and().statusCode(200);
    }

    @Test
    @DisplayName("Проверка изменения данных логина для неавторизированного пользователя")
    public void UserNoAuthorizationUpdateEmailTest() {
        userClient.getDataUser(accessToken);
        user.setEmail(faker.internet().emailAddress());
        Response response = userClient.setDataUserWithoutToken(new User(user.getEmail(), user.getName()));
        response.then().assertThat().body("success", equalTo(false)).and().statusCode(401);
    }

    @Test
    @DisplayName("Проверка изменения данных имени для неавторизированного пользователя")
    public void UserNoAuthorizationUpdateNameTest() {
        userClient.getDataUser(accessToken);
        user.setName(faker.name().firstName());
        Response response = userClient.setDataUserWithoutToken(new User(user.getEmail(), user.getName()));
        response.then().assertThat().body("success", equalTo(false)).and().statusCode(401);
    }
}