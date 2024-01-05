package pages.components;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static io.qameta.allure.Allure.attachment;

public class WebSteps {

    @Step("Открываем страницу {url}")
    public void openPage(String url) {
        open(url);
        attachment("Source", webdriver().driver().source());
        takeScreenshot();
    }


    @Step("Клик на кнопку категорий")
    public void clickOnCategoryButton() {
        $(".main-page__content").shouldBe(visible);
        $(".nav-element button").click();
    }

    @Step("Клик на кнопку категорий {category}")
    public void clickOnCategory(String category) {
        $$(".menu-burger__main-list-item--subcategory").findBy(text(category)).click();

    }

    @Step("Название категории {category} отображается в гриде товаров")
    public void categoryIsOnGoodsGrid(String category) {
        $(".catalog-title-wrap").shouldHave(text(category));

    }
    @Step("Url в браузере соответствует категории")
    public void urlMatchesCategory(String urlCategory) {
        webdriver().shouldHave(url(urlCategory));

    }

    @Attachment(value = "Screenshot", type = "image/png", fileExtension = "png")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
