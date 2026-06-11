package tests;

import api.CreateUser;
import api.CreateUserSteps;
import tests.helper.RandomDataUser;
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

@Feature("Регистрация пользователя")
public class MainLoginPageTest extends BaseTest {
    private MainPage mainPage;
    private LoginPage loginPage;
    private CreateUserSteps userSteps;
    private String lastRegisteredEmail;
    private String lastRegisteredPassword;

    @Before
    public void initPages() {
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        userSteps = new CreateUserSteps();
    }

    @After
    public void cleanUpRegisteredUser() {
        if (lastRegisteredEmail != null) {
            CreateUser creds = new CreateUser(lastRegisteredEmail, lastRegisteredPassword, null);
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

    @Test
    @DisplayName("Успешная регистрация")
    @Description("Проверка успешной регистрации пользователя с валидными данными и последующим очищением бд")
    public void successRegistrationTest() {
        openRegistrationForm();

        CreateUser user = RandomDataUser.generate();
        lastRegisteredEmail = user.getEmail();
        lastRegisteredPassword = user.getPassword();

        loginPage.registerUser(user.getName(), lastRegisteredEmail, lastRegisteredPassword);
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

        CreateUser user = RandomDataUser.generate();
        loginPage.registerUser(user.getName(), user.getEmail(), "12345");

        String errorText = loginPage.getPasswordErrorText();
        Assertions.assertThat(errorText)
                .as("Должна отображаться ошибка некорректного пароля")
                .isEqualTo("Некорректный пароль");
    }
}