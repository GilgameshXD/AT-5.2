package Test;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;


import static Data.Data.Registration.getRegisteredUser;
import static Data.Data.Registration.getUser;
import static Data.Data.getRandomLogin;
import static Data.Data.getRandomPassword;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class AuthTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }
    @Test
    void HappyPath() {
        var activeUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(activeUser.getLogin());
        $("[data-test-id='password'] input").setValue(activeUser.getPassword());
        $("button.button").click();
        $("h2").shouldHave(Condition.text("Личный кабинет"))
                .shouldBe(Condition.visible);
    }
    @Test
    void SadPathBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"))
                .shouldBe(Condition.visible);
    }
    @Test
    void SadPathInvalidUser() {
        var invalidUser = getUser("active");
        $("[data-test-id='login'] input").setValue(invalidUser.getLogin());
        $("[data-test-id='password'] input").setValue(invalidUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }
    @Test
    void SadPathInvalidLogin() {
        var invalidUser = getRegisteredUser("active");
        var randomLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(randomLogin);
        $("[data-test-id='password'] input").setValue(invalidUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }
    @Test
    void SadPathInvalidPassword() {
        var invalidUser = getRegisteredUser("active");
        var randomPassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue(invalidUser.getLogin());
        $("[data-test-id='password'] input").setValue(randomPassword);
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }

}