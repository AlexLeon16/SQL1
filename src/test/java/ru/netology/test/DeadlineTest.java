package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.test.data.DataHelper;
import ru.netology.test.data.SQLHelper;
import ru.netology.test.page.DashboardPage;
import ru.netology.test.page.LoginPage;
import ru.netology.test.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {

    @BeforeAll
    static void setupAll() {
        Configuration.baseUrl = "http://localhost:9999";
        Configuration.browserSize = "1920x1080";
    }

    @AfterAll
    static void cleanDatabase() {
        SQLHelper.cleanDatabase();
    }

    @Test
    void shouldLoginWithValidUser() {
        open("/");

        var authInfo = DataHelper.getValidAuthInfo();
        var loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(authInfo);

        var verificationCode = SQLHelper.getVerificationCode(authInfo.getLogin());
        DashboardPage dashboardPage = verificationPage.validVerify(verificationCode);

        dashboardPage.shouldBeVisible();
    }

    @Test
    void shouldBlockUserAfterThreeInvalidAttempts() {
        open("/");

        var loginPage = new LoginPage();
        var invalidAuthInfo = DataHelper.getInvalidPasswordForValidUser();

        loginPage.invalidLogin(invalidAuthInfo);
        loginPage.shouldShowError("Ошибка! Неверно указан логин или пароль");

        loginPage.invalidLogin(invalidAuthInfo);
        loginPage.shouldShowError("Ошибка! Неверно указан логин или пароль");

        loginPage.invalidLogin(invalidAuthInfo);
        loginPage.shouldShowError("Ошибка! Неверно указан логин или пароль");

        String actualStatus = SQLHelper.getUserStatus(invalidAuthInfo.getLogin());
        assertEquals("blocked", actualStatus);
    }
}