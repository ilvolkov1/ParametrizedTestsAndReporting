package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;


public class TestBase {

    // Настройка Selenide и WebDriver перед запуском тестов
    @BeforeAll
    static void beforeAll() {
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
        Configuration.browser = "chrome";
        Configuration.timeout = 7000;
        SelenideLogger.addListener("allure", new AllureSelenide());

    }

}
