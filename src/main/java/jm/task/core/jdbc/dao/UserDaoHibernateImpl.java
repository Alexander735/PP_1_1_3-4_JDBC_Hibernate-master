package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory;
    Session session;
    Transaction transaction;

    {
        try {
            sessionFactory = Util.getSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String SQL = """
                    CREATE TABLE IF NOT EXISTS User (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        lastName VARCHAR(255) NOT NULL,
                        age TINYINT NOT NULL
                    )
                    """;
            session.createSQLQuery(SQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }

    }

    @Override
    public void dropUsersTable() {

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String SQL = "DROP TABLE IF EXISTS User";
            session.createSQLQuery(SQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();

            System.out.println("Пользователь добавлен: имя--" + name
                        + " фамилия--" + lastName
                        + " возраст--" + age);
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }

    }

    @Override
    public void removeUserById(long id) {

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();

            System.out.println("Пользователь удалён: id == " + id);
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List <User> usersList = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            usersList = session.createQuery("from User").list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usersList;
    }

    @Override
    public void cleanUsersTable() {

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }

    }
}
