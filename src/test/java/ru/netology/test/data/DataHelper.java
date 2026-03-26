package ru.netology.test.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static AuthInfo getValidAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getInvalidPasswordForValidUser() {
        return new AuthInfo("vasya", "wrongpass");
    }

    public static AuthInfo getInvalidLoginInfo() {
        return new AuthInfo("petya", "qwerty123");
    }

    public static VerificationCode getInvalidVerificationCode() {
        return new VerificationCode("000000");
    }
}