package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory;

    {
        try {
            sessionFactory = Util.getSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public UserDaoHibernateImpl() {

    }

// Прописать (1--ДА)создание и (2--ДА)удаление таблицы, (3--НЕТ)добавление и (4--ДА)удаление пользователя,
// (5--НЕТ)вывод и (6--ДА)удаление всех пользователей

    @Override
    public void createUsersTable() {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            String SQL = """
                    CREATE TABLE Users (
                        id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
                        name varchar(255) NOT NULL,
                        lastName varchar(255),
                        age TINYINT
                    )
                    """;
            session.createSQLQuery(SQL).addEntity(User.class);

            session.getTransaction().commit();
            sessionFactory.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            String SQL = "DROP TABLE IF EXISTS users";
            Query query = session.createSQLQuery(SQL).addEntity(User.class);

            session.getTransaction().commit();
            sessionFactory.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            session.save(new User(name, lastName, age));

            session.getTransaction().commit();
            sessionFactory.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            session.remove(session.get(User.class, id));

            session.getTransaction().commit();
            sessionFactory.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List <User> usersList = new ArrayList<>();
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            usersList = session.createQuery("FROM User", User.class).getResultList();

            session.getTransaction().commit();
            sessionFactory.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            session.createQuery("delete User").executeUpdate();

            session.getTransaction().commit();
            sessionFactory.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
