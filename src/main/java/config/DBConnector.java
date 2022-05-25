package config;

import data.AppDAO;
import model.Medicao;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;


import java.util.Properties;

public class DBConnector {
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "org.mariadb.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mariadb://127.0.0.1:3306/TI5");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "raissalinda");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MariaDB103Dialect");
                settings.put(Environment.SHOW_SQL, "false");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "validate");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(Medicao.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void main(String[] args) {
        AppDAO<Medicao> dao = new AppDAO<>(Medicao.class);

        System.out.println(dao.getAll());
    }
}
