package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

class CardDeliveryOrder {

    @BeforeEach
    void openUrl() {
        Configuration.holdBrowserOpen = true;
        open ("http://localhost:9999/");
    }

    public String generateDate (int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldCardDeliveryOrder() {
        String dateVisit = generateDate(7);
        $("[data-test-id=city] input").setValue("Томск");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(dateVisit);
        $("[data-test-id=name] input").setValue("Гончиков Болот");
        $("[data-test-id=phone] input").setValue("+79994996468");
        $("[class=\"checkbox__box\"]").click();
        $(withText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(appear, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + dateVisit), Duration.ofSeconds(15));

    }

}
