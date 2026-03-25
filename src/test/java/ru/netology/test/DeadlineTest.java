package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.test.data.DataHelper;
import ru.netology.test.data.SQLHelper;
import ru.netology.test.page.LoginPage;
import ru.netology.test.page.VerificationPage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.visible;

public class DeadlineTest {

    @BeforeAll
    static void setupAll() {
        Configuration.baseUrl = "http://localhost:9999";
        Configuration.browserSize = "1920x1080";
    }

    @Test
    void shouldLoginWithValidUser() {
        open("/");

        var authInfo = DataHelper.getValidAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo.getLogin(), authInfo.getPassword());

        String code = SQLHelper.getVerificationCode(authInfo.getLogin());
        verificationPage.validVerify(code);

        $("[data-test-id='dashboard']").shouldBe(visible);
    }

    @Test
    void shouldBlockUserAfterThreeInvalidAttempts() {
        open("/");

        var loginPage = new LoginPage();

        loginPage.invalidLogin("vasya", "wrongpass");
        loginPage.invalidLogin("vasya", "wrongpass");
        loginPage.invalidLogin("vasya", "wrongpass");

        String status = SQLHelper.getUserStatus("vasya");
        assertEquals("blocked", status);
    }
}