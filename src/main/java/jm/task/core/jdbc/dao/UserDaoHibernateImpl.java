package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Entity;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao{
    Util util = new Util();
    SessionFactory sessionFactory = util.getSessionFactory();

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction trans = session.beginTransaction();
        try {

            session.createSQLQuery("CREATE TABLE IF NOT EXISTS `users` (\n" +
                    "  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(80) NOT NULL,\n" +
                    "  `lastName` VARCHAR(80) NOT NULL,\n" +
                    "  `age` INT(3) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`));").executeUpdate();
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        }
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction trans = session.beginTransaction();

        session.createSQLQuery("DROP TABLE IF EXISTS users;");
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.close();

    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(session.load(User.class, id));
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<User> users = (List<User>)session.createCriteria(User.class).list();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction trans = session.beginTransaction();
        List<?> users = session.createCriteria(User.class).list();
        for(Object user : users) {
            session.delete(user);
        }
        trans.commit();
        session.close();
    }
    public void close() {
        sessionFactory.close();
        util.close();
    }
}
