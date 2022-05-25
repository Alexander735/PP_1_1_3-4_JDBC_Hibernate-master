package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

//    UserDaoJDBCImpl user = new UserDaoJDBCImpl();
    UserDaoHibernateImpl userHibernate = new UserDaoHibernateImpl();

    public void createUsersTable() {
        userHibernate.createUsersTable();
    }

    public void dropUsersTable() {
        userHibernate.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userHibernate.saveUser(name,lastName,age);
    }

    public void removeUserById(long id) {
        userHibernate.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return userHibernate.getAllUsers();
    }

    public void cleanUsersTable() {
        userHibernate.cleanUsersTable();
    }
}
