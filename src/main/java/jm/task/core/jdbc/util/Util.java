package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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


    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() throws Exception {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();

            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL, "jdbc:mysql://localhost:3306/test2?useSSL=false");
            settings.put(Environment.USER, "root1");
            settings.put(Environment.PASS, "root1");
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
//            settings.put(Environment.SHOW_SQL, "true");
//            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

            configuration.setProperties(settings);
            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }

}
