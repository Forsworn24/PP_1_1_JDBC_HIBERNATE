package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        String queryCreateTable = "CREATE TABLE IF NOT EXISTS USER" +
                "(ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
                "NAME VARCHAR(40)," +
                "LASTNAME VARCHAR(40)," +
                "AGE TINYINT)";

        try (sessionFactory; session) {
            session.beginTransaction();
            session.createSQLQuery(queryCreateTable).executeUpdate();
            session.getTransaction().commit();
        } catch (IllegalStateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        String queryDropTable = "DROP TABLE IF EXISTS USER";

        try (sessionFactory; session) {
            session.beginTransaction();
            session.createSQLQuery(queryDropTable).executeUpdate();
            session.getTransaction().commit();
        } catch (IllegalStateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try (sessionFactory; session) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (IllegalStateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        String queryDeleteUser = "DELETE FROM USER WHERE ID = :id";

        try (sessionFactory; session) {
            session.beginTransaction();
            session.createQuery(queryDeleteUser).setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
        } catch (IllegalStateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        String queryGetAllUsers = "FROM User";
        List<User> users = new ArrayList<>();

        try (sessionFactory; session) {
            session.beginTransaction();
            users = session.createQuery(queryGetAllUsers, User.class).getResultList();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        String queryCleanUsersTable = "TRUNCATE TABLE USER";

        try (sessionFactory; session) {
            session.beginTransaction();
            session.createSQLQuery(queryCleanUsersTable).executeUpdate();
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
