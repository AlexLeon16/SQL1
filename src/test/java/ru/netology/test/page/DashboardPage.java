package ru.netology.test.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    private final SelenideElement dashboard = $("[data-test-id='dashboard']");

    public DashboardPage() {
        dashboard.shouldBe(visible);
    }

    public void shouldBeVisible() {
        dashboard.shouldBe(visible);
    }
}