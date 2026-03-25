package ru.netology.test.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.visible;

public class VerificationPage {
    private final SelenideElement codeInput = $("[data-test-id='code'] input");
    private final SelenideElement verifyButton = $("[data-test-id='action-verify']");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public VerificationPage() {
        codeInput.shouldBe(visible);
    }

    public void validVerify(String code) {
        codeInput.setValue(code);
        verifyButton.click();
    }

    public void invalidVerify(String code) {
        codeInput.setValue(code);
        verifyButton.click();
    }

    public SelenideElement getErrorNotification() {
        return errorNotification;
    }
}