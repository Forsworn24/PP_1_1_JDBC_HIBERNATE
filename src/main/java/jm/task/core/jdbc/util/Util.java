package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Util {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        sessionFactory = new Configuration().addAnnotatedClass(User.class).buildSessionFactory();

        return sessionFactory;
    }
}
