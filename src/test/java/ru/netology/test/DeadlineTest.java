package ru.netology.test;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.test.data.DataHelper;
import ru.netology.test.data.SQLHelper;
import ru.netology.test.page.DashboardPage;
import ru.netology.test.page.LoginPage;
import ru.netology.test.page.VerificationPage;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class DeadlineTest {

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
        Configuration.baseUrl = "http://localhost:9999";
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.headless = true;
    }

    @AfterAll
    static void cleanDatabase() {
        SQLHelper.cleanDatabase();
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
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
    void shouldShowErrorIfInvalidPassword() {
        open("/");

        var loginPage = new LoginPage();
        var invalidAuthInfo = DataHelper.getInvalidPasswordForValidUser();

        loginPage.invalidLogin(invalidAuthInfo);
        loginPage.shouldShowError("Ошибка! Неверно указан логин или пароль");
    }

    @Test
    void shouldShowErrorIfInvalidLogin() {
        open("/");

        var loginPage = new LoginPage();
        var invalidAuthInfo = DataHelper.getInvalidLoginInfo();

        loginPage.invalidLogin(invalidAuthInfo);
        loginPage.shouldShowError("Ошибка! Неверно указан логин или пароль");
    }
}