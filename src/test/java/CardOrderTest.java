import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardOrderTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String planningDate = generateDate(3);

    @Data
    @RequiredArgsConstructor
    public class CardDeliveryDate {
        private final String city;
        private final String name;
        private final String phone;
    }

    @Test
    void shouldTestCardOrderTask1() {
        val date = new CardDeliveryDate("Уфа", "Петров Петр", "+79000000000");
        //int i = 1;
        //while (i != 2) {
            open("http://localhost:9999/");
            SelenideElement form = $(By.className("form"));
            form.$("[data-test-id=city] input").setValue(date.city);
            form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
            form.$("[data-test-id=date] input").setValue(planningDate);
            form.$("[data-test-id=name] input").setValue(date.name);
            form.$("[data-test-id=phone] input").setValue(date.phone);
            form.$("[data-test-id=agreement]").click();
            form.$(By.className("button")).click();
            form.$(By.className("button")).click();
            //i++;
        //}
        $("[data-test-id=replan-notification]")
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"), Duration.ofSeconds(20));

    }

}