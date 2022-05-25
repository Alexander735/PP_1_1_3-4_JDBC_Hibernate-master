package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory;

    {
        try {
            sessionFactory = Util.getSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UserDaoHibernateImpl() {

    }

// Прописать (1--ДА)создание и (2--НЕТ)удаление таблицы, (3--ДА)добавление и (4--ДА)удаление пользователя,
// (5--ДА)вывод и (6--ДА)удаление всех пользователей

    @Override
    public void createUsersTable() {
        Transaction tx = null;
        Session session = sessionFactory.openSession();

        try {
            tx = session.beginTransaction();
            String SQL = """
                    CREATE TABLE Users (
                        id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
                        name varchar(255) NOT NULL,
                        lastName varchar(255),
                        age TINYINT
                    )
                    """;
            session.createSQLQuery(SQL).addEntity(User.class);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction tx = null;
        Session session = sessionFactory.openSession();

        try {
            tx = session.beginTransaction();
            String SQL = "DROP TABLE IF EXISTS users";
            Query query = session.createSQLQuery(SQL).addEntity(User.class);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = null;
        Session session = sessionFactory.openSession();

        try {
            tx = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tx.commit();

            System.out.println("Пользователь добавлен: имя " + name
                            + "--фамилия " + lastName
                            + "--возраст "+ age);

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        Session session = sessionFactory.openSession();

        try {
            tx = session.beginTransaction();
            session.remove(session.get(User.class, id));
            tx.commit();

            System.out.println("Пользователь удалён: id == " + id);

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List <User> usersList = new ArrayList<>();
        Transaction tx = null;
        Session session = sessionFactory.openSession();

        try {
            tx = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);

            Query query = session.createQuery(cq);
            usersList = query.getResultList();

            tx.commit();
//          usersList = session.createCriteria(User.class).list(); -- не работает
//          usersList = session.createQuery("from User").getResultList(); -- не работает
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            session.close();
        }

        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction tx = null;
        Session session = sessionFactory.openSession();

        try {
            tx = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
                throw e;
            }
        } finally {
            session.close();
        }
    }
}
