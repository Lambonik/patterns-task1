package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        //Configuration.holdBrowserOpen=true;
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
            $("[data-test-id='city'] input").setValue(DataGenerator.generateCity());
            $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.LEFT_SHIFT, Keys.HOME), Keys.BACK_SPACE);
            $("[data-test-id='date'] input").setValue(firstMeetingDate);
            $("[data-test-id='name'] input").setValue(DataGenerator.generateName());
            $("[data-test-id='phone'] input").setValue(DataGenerator.generatePhone());
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            $(".notification__content")
                    .shouldHave(Condition.text("Встреча успешно забронирована на " + firstMeetingDate), Duration.ofSeconds(15))
                    .shouldBe(Condition.visible);
            $(".notification .icon-button").click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.LEFT_SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(secondMeetingDate);
        $$("button").find(exactText("Забронировать")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + secondMeetingDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}
