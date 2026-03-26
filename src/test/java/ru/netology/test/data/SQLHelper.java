package ru.netology.test.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {

    private static final String URL = System.getProperty("db.url", "jdbc:mysql://localhost:3306/app");
    private static final String USER = System.getProperty("db.user", "app");
    private static final String PASSWORD = System.getProperty("db.password", "pass");

    private SQLHelper() {
    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static String getVerificationCode(String login) {
        var codeSql = """
                SELECT ac.code
                FROM auth_codes ac
                JOIN users u ON ac.user_id = u.id
                WHERE u.login = ?
                ORDER BY ac.created DESC
                LIMIT 1
                """;

        var runner = new QueryRunner();
        try (var conn = getConn()) {
            return runner.query(conn, codeSql, new ScalarHandler<>(), login).toString();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getUserStatus(String login) {
        var statusSql = "SELECT status FROM users WHERE login = ?";

        var runner = new QueryRunner();
        try (var conn = getConn()) {
            return runner.query(conn, statusSql, new ScalarHandler<>(), login).toString();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void cleanDatabase() {
        var runner = new QueryRunner();

        try (var conn = getConn()) {
            runner.update(conn, "DELETE FROM card_transactions");
            runner.update(conn, "DELETE FROM auth_codes");
            runner.update(conn, "DELETE FROM cards");
            runner.update(conn, "DELETE FROM users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}