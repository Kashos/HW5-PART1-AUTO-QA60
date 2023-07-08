import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.Keys;
import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardOrderTest {

    @Test
    void shouldTestCardOrderTask1() {
        DataGenerator.Registration.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        int firstMitingDays = 3;
        String firstMitingDate = DataGenerator.generateDate(firstMitingDays);
        int secondMitingDays = 7;
        String secondMitingDate = DataGenerator.generateDate(secondMitingDays);
        open("http://localhost:9999/");
        $("[data-test-id=city] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstMitingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification]")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + firstMitingDate), Duration.ofSeconds(15));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(secondMitingDate);
        $(byText("Запланировать")).click();
        $("[data-test-id=replan-notification]")
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldBe(visible);
        $(byText("Перепланировать")).click();
        $("[data-test-id=success-notification]")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + secondMitingDate), Duration.ofSeconds(15));
    }

}