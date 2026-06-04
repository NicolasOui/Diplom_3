package tests;

import api.CreateUser;
import api.CreateUserSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.MainPage;
import pages.LoginPage;
import java.util.Random;

@Feature("Регистрация пользователя")
public class MainLoginPageTest extends BaseTest {
    private MainPage mainPage;
    private LoginPage loginPage;
    private CreateUserSteps userSteps;
    private String lastRegisteredEmail;
    private final String testPassword = "password123";

    @Before
    public void initPages() {
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        userSteps = new CreateUserSteps(); // Инициализируем API-шаги
    }

    @After
    public void cleanUpRegisteredUser() {
        if (lastRegisteredEmail != null) {
            CreateUser creds = new CreateUser(lastRegisteredEmail, testPassword, null);
            Response response = userSteps.login(creds);
            String token = response.path("accessToken");

            if (token != null) {
                userSteps.delete(token);
            }
        }
    }

    private void openRegistrationForm() {
        mainPage.open(baseUrl);
        mainPage.clickCenterLoginButton();
        loginPage.clickRegisterLink();
    }

    private String generateRandomEmail() {
        return "burger_user_" + new Random().nextInt(100000) + "@yandex.ru";
    }

    @Test
    @DisplayName("Успешная регистрация")
    @Description("Проверка успешной регистрации пользователя с валидными данными и последующим очищением бд")
    public void successRegistrationTest() {
        openRegistrationForm();
        lastRegisteredEmail = generateRandomEmail();
        loginPage.registerUser("Владимир", lastRegisteredEmail, testPassword);
        boolean isLoginVisible = loginPage.isLoginHeadingDisplayed();
        Assertions.assertThat(isLoginVisible)
                .as("После успешной регистрации должен отображаться экран Входа")
                .isTrue();
    }

    @Test
    @DisplayName("Ошибка для некорректного пароля")
    @Description("Проверка появления ошибки 'Некорректный пароль' при вводе пароля менее 6 символов")
    public void registrationWithShortPasswordShowsErrorTest() {
        openRegistrationForm();
        loginPage.registerUser("Владимир", generateRandomEmail(), "12345");
        String errorText = loginPage.getPasswordErrorText();
        Assertions.assertThat(errorText)
                .as("Должна отображаться ошибка некорректного пароля")
                .isEqualTo("Некорректный пароль");
    }
}