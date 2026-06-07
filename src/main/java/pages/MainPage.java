package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By centerLoginButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By headerProfileButton = By.xpath(".//p[text()='Личный Кабинет']/parent::a");
    private final By createOrderButton = By.xpath(".//button[text()='Оформить заказ']");
    private final By bunsTab = By.xpath(".//span[text()='Булки']/parent::div");
    private final By saucesTab = By.xpath(".//span[text()='Соусы']/parent::div");
    private final By ingredientsTab = By.xpath(".//span[text()='Начинки']/parent::div");
    private final By activeBunsTab = By.xpath(".//span[text()='Булки']/parent::div[contains(@class, 'tab_tab_type_current')]");
    private final By activeSaucesTab = By.xpath(".//span[text()='Соусы']/parent::div[contains(@class, 'tab_tab_type_current')]");
    private final By activeIngredientsTab = By.xpath(".//span[text()='Начинки']/parent::div[contains(@class, 'tab_tab_type_current')]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Открыть главную страницу Stellar Burgers")
    public void open(String url) {
        driver.get(url);
    }

    private void safeClick(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(locator));
        }
    }

    @Step("Кликнуть по центральной кнопке 'Войти в аккаунт'")
    public void clickCenterLoginButton() {
        safeClick(centerLoginButton);
    }

    @Step("Кликнуть по кнопке 'Личный кабинет' в шапке")
    public void clickHeaderProfileButton() {
        safeClick(headerProfileButton);
    }

    @Step("Проверить, отображается ли кнопка 'Оформить заказ'")
    public boolean isCreateOrderButtonDisplayed() {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(createOrderButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Кликнуть на вкладку 'Булки'")
    public void clickBunsTab() {
        safeClick(bunsTab);
    }

    @Step("Кликнуть на вкладку 'Соусы'")
    public void clickSaucesTab() {
        safeClick(saucesTab);
    }

    @Step("Кликнуть на вкладку 'Начинки'")
    public void clickIngredientsTab() {
        safeClick(ingredientsTab);
    }

    @Step("Проверить, активна ли вкладка 'Булки'")
    public boolean isBunsTabActive() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(activeBunsTab));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверить, активна ли вкладка 'Соусы'")
    public boolean isSaucesTabActive() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(activeSaucesTab));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверить, активна ли вкладка 'Начинки'")
    public boolean isIngredientsTabActive() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(activeIngredientsTab));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
