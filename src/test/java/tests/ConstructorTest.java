package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import pages.MainPage;

@Feature("Раздел Конструктор")
public class ConstructorTest extends BaseTest {
    private MainPage mainPage;

    @Before
    public void initPages() {
        mainPage = new MainPage(driver);
    }

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    @Description("Проверка, что при клике на вкладку 'Соусы' она становится активной")
    public void testSwitchToSaucesTab() {
        mainPage.clickSaucesTab();
        Assertions.assertThat(mainPage.isSaucesTabActive())
                .as("Вкладка 'Соусы' должна стать активной")
                .isTrue();
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    @Description("Проверка, что при клике на вкладку 'Начинки' она становится активной")
    public void testSwitchToIngredientsTab() {
        mainPage.clickIngredientsTab();
        Assertions.assertThat(mainPage.isIngredientsTabActive())
                .as("Вкладка 'Начинки' должна стать active")
                .isTrue();
    }

    @Test
    @DisplayName("Переход к разделу 'Булки'")
    @Description("Проверка, что при переключении с соусов обратно на 'Булки' вкладка становится активной")
    public void testSwitchToBunsTab() {
        mainPage.clickSaucesTab();
        mainPage.clickBunsTab();
        Assertions.assertThat(mainPage.isBunsTabActive())
                .as("Вкладка 'Булки' должна снова стать активной")
                .isTrue();
    }
}
