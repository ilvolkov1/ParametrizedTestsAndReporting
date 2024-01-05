package tests;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;


public class UnknownTest {

    @Test
    @Description("This test is intentionally designed to be unknown.")
    public void testUnknown() {
        // Some test logic without explicit assertions or checks
        performUnknownTestLogic();
    }

    private void performUnknownTestLogic() {
        open("https://github.com/");
        // Some interaction with the web page without any assertions
        // For example, clicking elements, navigating, etc.
    }

    private void open(String url) {
        Selenide.open(url);
    }
}
