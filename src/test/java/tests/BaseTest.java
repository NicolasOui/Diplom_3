package tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseTest {
    protected WebDriver driver;
    protected final String baseUrl = "https://stellarburgers.education-services.ru";
    private final String browser = System.getProperty("browser", "chrome");

    @Before
    public void startBrowser() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        if ("chrome".equalsIgnoreCase(browser)) {
            System.clearProperty("webdriver.chrome.driver");
            driver = new ChromeDriver(options);

        } else if ("yandex".equalsIgnoreCase(browser)) {
            System.setProperty("webdriver.chrome.driver", "drivers/yandexdriver.exe");
            String localAppData = System.getenv("LOCALAPPDATA");
            if (localAppData != null) {
                options.setBinary(localAppData + "\\Yandex\\YandexBrowser\\Application\\browser.exe");
            } else {
                options.setBinary("C:\\Users\\VAR\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");
            }

            driver = new ChromeDriver(options);
        }

        driver.get(baseUrl);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
