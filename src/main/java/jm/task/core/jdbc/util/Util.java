package jm.task.core.jdbc.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public static Connection getConnection() {
        String DB_URL;
        String DB_USER;
        String DB_PASSWORD;
        Properties properties = new Properties();
        Connection connection = null;

        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(fis);
            DB_URL = properties.getProperty("db_url");
            DB_USER = properties.getProperty("db_user");
            DB_PASSWORD = properties.getProperty("db_password");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.err.println("Не удалось соединиться с базой данных");
            e.printStackTrace();
        }

        return connection;
    }
}
