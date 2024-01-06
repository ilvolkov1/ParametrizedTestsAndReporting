package tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import pages.components.WebSteps;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static io.qameta.allure.Allure.step;

public class ParametrizedTests extends TestBase {

    // Метод передачи данных. Его имя должно совпадать с именем аннотации ниже
    static Stream<Arguments> methodSourceExampleTest() {
        return Stream.of(
                // с первым запуском тест получит в виде аргументов строки и список
                Arguments.of("first string", List.of(42, 13)),
                // со вторым запуском уже другую строку и список
                Arguments.of("second string", List.of(1, 2)));
    }

    // Аннотация поставщика данных, помним про имя
    @MethodSource("methodSourceExampleTest")
    @ParameterizedTest
    // Метод теста. В этом случае просто выводит в консоль аргументы
    void methodSourceExampleTest(String firstArg, List<Integer> secondArg) {
        System.out.println(firstArg + " and list: " + secondArg);
    }


    @CsvSource(value = {
//            "alex, 30",
//            "brian, 35",
            "charles, 40"})
    @ParameterizedTest
    void TestWithCsvSource(String name, int age) {
        open("https://github.com/");
        System.out.println(name + "   " + age);
    }

    @Disabled
    @ParameterizedTest
    @EnumSource(Direction.class)
    void testWithEnumSource(Direction d) {
        System.out.println(d);
    }

    @ParameterizedTest
    @EnumSource(Browser.class)
    void testWithEnumSourceBrowser(Browser browser) {
        Configuration.browser = browser.toString();
        open("https://github.com/");
        System.out.println(browser);
     //   assert (2 > 3);
    }

    @Test
    public void testUnknown() {
        open("https://github.com/");
        // Assume some test logic that doesn't explicitly indicate pass/fail
        // For example, interacting with the web page without assertions
    }

    @Test
    public void testBroken() {
        open("https://google.com/");
        // Simulate a broken test by throwing an exception
        throw new RuntimeException("Simulated broken test");
    }


    @Tag("Smoke")
    @Feature("Категории товаров")
    @Story("Открытие категории товаров ведёт на страницу этой категории")
    @Owner("ilvolkov")
    @Severity(SeverityLevel.CRITICAL)
    @Link(value = "Testing", url = "https://www.wildberries.ru/")
    @DisplayName("Открытие категории товаров ведёт на страницу этой категории")
    @CsvSource(value = {
            "Женщинам, https://www.wildberries.ru/catalog/zhenshchinam",
            "Мужчинам, https://www.wildberries.ru/catalog/muzhchinam",})
    @ParameterizedTest
    void testGoodsCategoriesWithLambdaSteps(String category, String urlCategory) {

        step("Открываем главную страницу", () -> {
            open("https://www.wildberries.ru/");
        });
        step("Кликаем на кнопку Категорий", () -> {
            $(".main-page__content").shouldBe(visible);
            $(".nav-element button").click();
        });
        step("В списке категорий кликаем на категорию " + category, () -> {
            $$(".menu-burger__main-list-item--subcategory").findBy(text(category)).click();
        });
        step("Название категории " + category + " отображается в гриде товаров", () -> {
            $(".catalog-title-wrap").shouldHave(text(category));
        });
        step("Url в браузере соответствует категории " + urlCategory, () -> {
            webdriver().shouldHave(url(urlCategory));
        });

    }

    @Tag("Smoke")
    @Feature("Категории товаров")
    @Story("Открытие категории товаров ведёт на страницу этой категории(WebSteps)")
    @Owner("ilvolkov")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "Testing", url = "https://www.wildberries.ru/")
    @DisplayName("Открытие категории товаров ведёт на страницу этой категории")
    @CsvSource(value = {
            "Дом, https://www.wildberries.ru/catalog/dom-i-dacha",
            "Детям, https://www.wildberries.ru/catalog/detyam",
    })
    @ParameterizedTest
    void testGoodsCategoriesWithSteps(String category, String urlCategory) {
        WebSteps steps = new WebSteps();
        steps.openPage("https://www.wildberries.ru/");
        steps.clickOnCategoryButton();
        steps.takeScreenshot();
        steps.clickOnCategory(category);
        steps.takeScreenshot();
        steps.categoryIsOnGoodsGrid(category);
        steps.takeScreenshot();
        steps.urlMatchesCategory(urlCategory);

    }

    enum Direction {
        EAST, WEST, NORTH, SOUTH
    }


    enum Browser {
        edge, chrome
    }


}
