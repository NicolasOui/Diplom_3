package api;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CreateUserSteps {
    private final Gson gson = new Gson();

    @Step("Отправка POST-запроса на регистрацию пользователя")
    public Response register(CreateUser user) {
        String jsonBody = gson.toJson(user);
        return given()
                .filter(new AllureRestAssured()) // Подключаем логи в Allure
                .header("Content-Type", "application/json")
                .baseUri(Endpoints.BASE_URL)
                .body(jsonBody)
                .when()
                .post(Endpoints.USER_REGISTER);
    }

    @Step("Удаление пользователя по токену")
    public Response delete(String token) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .baseUri(Endpoints.BASE_URL)
                .when()
                .delete(Endpoints.DELETE_USER);
    }

    @Step("Авторизация пользователя (получение токена)")
    public Response login(CreateUser user) {
        String jsonBody = gson.toJson(user);
        return given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "application/json")
                .baseUri(Endpoints.BASE_URL)
                .body(jsonBody)
                .when()
                .post(Endpoints.USER_LOGIN);
    }
}
