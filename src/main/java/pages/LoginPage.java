package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By loginHeading = By.xpath(".//h2[text()='Вход']");
    private final By registerLink = By.xpath(".//a[text()='Зарегистрироваться']");
    private final By loginEmailInput = By.xpath(".//label[text()='Email']/following-sibling::input");
    private final By loginPasswordInput = By.xpath(".//label[text()='Пароль']/following-sibling::input");
    private final By loginButton = By.xpath(".//button[text()='Войти']");
    private final By nameInput = By.xpath(".//label[text()='Имя']/following-sibling::input");
    private final By emailInput = By.xpath(".//label[text()='Email']/following-sibling::input");
    private final By passwordInput = By.xpath(".//label[text()='Пароль']/following-sibling::input");
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By passwordError = By.xpath(".//p[text()='Некорректный пароль']");
    private final By loginLinkFromRegisterForm = By.xpath(".//p[contains(text(),'Уже зарегистрированы')]/a[text()='Войти']");
    private final By forgotPasswordLink = By.xpath(".//a[text()='Восстановить пароль']");
    private final By loginLinkFromForgotPasswordForm = By.xpath(".//p[contains(text(),'Вспомнили пароль')]/a[text()='Войти']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private void safeClick(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(locator));
        }
    }

    @Step("Кликнуть по ссылке 'Зарегистрироваться'")
    public void clickRegisterLink() {
        safeClick(registerLink);
    }

    @Step("Кликнуть по ссылке 'Восстановить пароль'")
    public void clickForgotPasswordLink() {
        safeClick(forgotPasswordLink);
    }

    @Step("Выполнить вход в аккаунт: Email={email}")
    public void login(String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginEmailInput)).sendKeys(email);
        driver.findElement(loginPasswordInput).sendKeys(password);
        safeClick(loginButton);
    }

    @Step("Проверить, отображается ли заголовок 'Вход'")
    public boolean isLoginHeadingDisplayed() {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(loginHeading)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Заполнить форму регистрации: Имя={name}, Email={email}")
    public void registerUser(String name, String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput)).sendKeys(name);
        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(passwordInput).sendKeys(password);
        safeClick(registerButton);
    }

    @Step("Получить текст ошибки некорректного пароля")
    public String getPasswordErrorText() {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(passwordError)).getText();
        } catch (Exception e) {
            return "";
        }
    }

    @Step("Кликнуть по кнопке 'Войти' на форме регистрации")
    public void clickLoginLinkFromRegisterForm() {
        safeClick(loginLinkFromRegisterForm);
    }

    @Step("Кликнуть по кнопке 'Войти' на форме восстановления пароля")
    public void clickLoginLinkFromForgotPasswordForm() {
        safeClick(loginLinkFromForgotPasswordForm);
    }
}
