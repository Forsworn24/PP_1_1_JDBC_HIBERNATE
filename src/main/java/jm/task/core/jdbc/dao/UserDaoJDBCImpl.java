package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String queryCreateTable = "CREATE TABLE IF NOT EXISTS USER" +
                "(ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
                "NAME VARCHAR(40)," +
                "LASTNAME VARCHAR(40)," +
                "AGE INTEGER)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryCreateTable)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String queryDropTable = "DROP TABLE IF EXISTS USER";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryDropTable)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String querySaveUser = "INSERT INTO USER (NAME, LASTNAME, AGE) VALUES(?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(querySaveUser)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем - %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String queryDeleteUser = "DELETE FROM USER WHERE ID=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryDeleteUser)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String queryGetAllUsers = "SELECT * FROM USER";
        try (ResultSet usersSet = connection.createStatement().executeQuery(queryGetAllUsers)) {
            while (usersSet.next()) {
                User user = new User(usersSet.getString("NAME"),
                                        usersSet.getString("LASTNAME"),
                                        usersSet.getByte("AGE"));
                user.setId(usersSet.getLong("ID"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return users;
    }

    public void cleanUsersTable() {
        String queryCleanUsersTable = "TRUNCATE TABLE USER";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryCleanUsersTable)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
