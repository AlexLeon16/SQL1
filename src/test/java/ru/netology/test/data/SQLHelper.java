package ru.netology.test.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private SQLHelper() {
    }

    private static final String URL = "jdbc:mysql://localhost:3306/app";
    private static final String USER = "app";
    private static final String PASSWORD = "pass";

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static String getVerificationCode(String login) {
        String getUserIdSql = "SELECT id FROM users WHERE login = ?";
        String getCodeSql = "SELECT code FROM auth_codes WHERE user_id = ? ORDER BY created DESC LIMIT 1";

        try (var conn = getConn()) {
            var runner = new QueryRunner();

            String userId = runner.query(conn, getUserIdSql, new ScalarHandler<>(), login);
            return runner.query(conn, getCodeSql, new ScalarHandler<>(), userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getUserStatus(String login) {
        String sql = "SELECT status FROM users WHERE login = ?";

        try (var conn = getConn()) {
            var runner = new QueryRunner();
            return runner.query(conn, sql, new ScalarHandler<>(), login);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void cleanAuthCodes() {
        String sql = "DELETE FROM auth_codes";

        try (var conn = getConn()) {
            var runner = new QueryRunner();
            runner.update(conn, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void cleanDatabase() {
        try (var conn = getConn()) {
            var runner = new QueryRunner();
            runner.update(conn, "DELETE FROM auth_codes");
            runner.update(conn, "DELETE FROM card_transactions");
            runner.update(conn, "DELETE FROM cards");
            runner.update(conn, "DELETE FROM users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}