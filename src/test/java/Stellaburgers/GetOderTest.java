package Stellaburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class GetOderTest {
    public User user;
    public UserClient userClient;
    public OrderClient oderClient;
    public Order oder;
    public Ingredient allIngredient;
    public IngredientClient ingredientClient;
    private String accessToken;
    public List<String> ingredients;
    Response response;

    @Before
    public void setUp() {
        ingredientClient = new IngredientClient();
        allIngredient = ingredientClient.getIngredient();
        ingredients = new ArrayList<>();
        ingredients.add(allIngredient.data.get(0).get_id());
        ingredients.add(allIngredient.data.get(1).get_id());
        ingredients.add(allIngredient.data.get(2).get_id());

        user = User.getRandomUser();
        userClient = new UserClient();
        userClient.create(user);
        Response responseForToken = userClient.login(user);
        accessToken = responseForToken.then().extract().path("accessToken");
        oder = new Order(ingredients);
        oderClient = new OrderClient();
        oderClient.createOderWithAuthorization(accessToken, oder);
    }

    @After
    public void tearDown(){
        userClient.deleteUser();
    }

    @Test
    @DisplayName("Проверка получение заказа для неавторизированного пользователя")
    public void getOderForUserWithoutAuthorizationTest() {

        response = oderClient.getOderUserWithoutAuthorization();
        response.then().assertThat().body("success", equalTo(false)).and().statusCode(401);
    }

    @Test
    @DisplayName("Проверка получение заказа для авторизированного пользователя")
    public void getOderForUserWithAuthorizationTest() {
        response = oderClient.getOderUserWithAuthorization(accessToken);
        response.then().assertThat().body("success", equalTo(true)).and().statusCode(200);
    }
}