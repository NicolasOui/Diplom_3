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
import pages.LoginPage;
import pages.MainPage;

@Feature("Авторизация пользователя")
public class LoginTest extends BaseTest {
    private MainPage mainPage;
    private LoginPage loginPage;
    private CreateUserSteps userSteps;
    private String userEmail;
    private String userPassword;
    private String accessToken;

    @Before
    public void initPagesAndRegisterUserViaApi() {
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        userSteps = new CreateUserSteps();

        CreateUser user = RandomDataUser.generate();
        userEmail = user.getEmail();
        userPassword = user.getPassword();

        Response response = userSteps.register(user);
        accessToken = response.path("accessToken");
        mainPage.open(baseUrl);
    }

    @After
    public void deleteTestUserViaApi() {
        if (accessToken != null) {
            userSteps.delete(accessToken);
        }
    }

    @Test
    @DisplayName("Вход по кнопке 'Войти в аккаунт' на главной")
    @Description("Проверка авторизации при переходе через центральную кнопку на главной странице")
    public void loginViaCenterButtonOnMainPageTest() {
        mainPage.clickCenterLoginButton();
        loginPage.login(userEmail, userPassword);
        Assertions.assertThat(mainPage.isCreateOrderButtonDisplayed())
                .as("После входа на главной странице должна появиться кнопка оформления заказа")
                .isTrue();
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    @Description("Проверка авторизации при переходе через кнопку 'Личный кабинет' в шапке")
    public void loginViaHeaderProfileButtonTest() {
        mainPage.clickHeaderProfileButton();
        loginPage.login(userEmail, userPassword);
        Assertions.assertThat(mainPage.isCreateOrderButtonDisplayed())
                .as("После входа через Личный кабинет должна появиться кнопка оформления заказа")
                .isTrue();
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    @Description("Проверка перехода к авторизации по ссылке 'Войти' со страницы регистрации")
    public void loginViaRegisterFormLinkTest() {
        mainPage.clickCenterLoginButton();
        loginPage.clickRegisterLink();
        loginPage.clickLoginLinkFromRegisterForm();
        loginPage.login(userEmail, userPassword);
        Assertions.assertThat(mainPage.isCreateOrderButtonDisplayed())
                .as("После входа с формы регистрации должна появиться кнопка оформления заказа")
                .isTrue();
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    @Description("Проверка перехода к авторизации по ссылке 'Войти' со страницы восстановления пароля")
    public void loginViaForgotPasswordFormLinkTest() {
        mainPage.clickCenterLoginButton();
        loginPage.clickForgotPasswordLink();
        loginPage.clickLoginLinkFromForgotPasswordForm();
        loginPage.login(userEmail, userPassword);
        Assertions.assertThat(mainPage.isCreateOrderButtonDisplayed())
                .as("После входа с формы восстановления пароля должна появиться кнопка оформления заказа")
                .isTrue();
    }
}
