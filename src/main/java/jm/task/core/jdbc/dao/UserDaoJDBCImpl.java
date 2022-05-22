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

// Прописать (1)создание и (2)удаление таблицы, (да 3)добавление и (да 4)удаление пользователя, (да 5)вывод всех пользователей

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String SQL = "CREATE TABLE Users " +
                "(id LONG PRIMARY KEY AUTO_INCREMENT, " +
                " name VARCHAR(20) NOT NULL, " +
                " lastName VARCHAR(20) " +
                " age BYTE)";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(SQL);

        } catch (SQLException e) {

        }
    }

    public void dropUsersTable() {
        String SQL = "DROP TABLE Users";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(SQL);

        } catch (SQLException e) {

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO `Users` (`name`, `lastName`, `age`) VALUES (?, ?, ?)");

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            int resultOfStatement = preparedStatement.executeUpdate();

            if (resultOfStatement > 0) {
                System.out.println("User с именем " + name + " добавлен в базу данных");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Users WHERE id=?");
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

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
