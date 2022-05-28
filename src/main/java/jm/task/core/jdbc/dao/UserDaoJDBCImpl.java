package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;


import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    Connection connection;

    {
        try {
            connection = Util.getConnection();
        } catch (SQLException e) {

        }
    }



    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        String SQL = """
                    CREATE TABLE Users (
                        id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
                        name varchar(255) NOT NULL,
                        lastName varchar(255),
                        age TINYINT
                    )
                    """;

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(SQL);

        } catch (SQLException e) {

        }
    }

    @Override
    public void dropUsersTable() {
        String SQL = "DROP TABLE Users";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(SQL);

        } catch (SQLException e) {

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO Users (name, lastName, age) VALUES (?, ?, ?)");

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Users WHERE id=?");
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List <User> usersList = new ArrayList<>();

        Statement statement;

        try {
            String SQL = "SELECT * FROM Users";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                usersList.add(user);
            }

        } catch (SQLException e) {
            printSQLException(e);
        }

        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        try {
            Statement statement = connection.createStatement();
            String SQL = "DELETE FROM Users";

            statement.executeUpdate(SQL);

        } catch (SQLException e) {
            printSQLException(e);
        }
    }
}
