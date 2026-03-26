package ru.netology.test.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.test.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement loginField = $("[data-test-id='login'] input");
    private final SelenideElement passwordField = $("[data-test-id='password'] input");
    private final SelenideElement loginButton = $("[data-test-id='action-login']");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");
    private final SelenideElement errorText = $("[data-test-id='error-notification'] .notification__content");

    public LoginPage() {
        loginField.shouldBe(visible);
    }

    private void fillAndSubmit(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
    }

    public VerificationPage validLogin(DataHelper.AuthInfo authInfo) {
        fillAndSubmit(authInfo.getLogin(), authInfo.getPassword());
        return new VerificationPage();
    }

    public void invalidLogin(DataHelper.AuthInfo authInfo) {
        fillAndSubmit(authInfo.getLogin(), authInfo.getPassword());
    }

    public void shouldShowError(String expectedText) {
        errorNotification.shouldBe(visible);
        errorText.shouldHave(exactText(expectedText));
    }
}