package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USERNAME = "root1";
    private static final String PASSWORD = "root1";


    public static Connection getConnection () throws SQLException {

        Driver driver = new com.mysql.jdbc.Driver();
        DriverManager.registerDriver(driver);

        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        return connection;
    }

    public static void closeConnection (Connection connection) throws SQLException {

        connection.close();
    }

}
