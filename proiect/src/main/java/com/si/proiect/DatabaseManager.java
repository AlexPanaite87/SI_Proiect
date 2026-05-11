package com.si.proiect;

import com.si.proiect.dao.FrameworkDAO;
import com.si.proiect.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DatabaseManager {

    private static SessionFactory sessionFactory;

    public static void initialize() {
        try {
            Configuration configuration = new Configuration();

            configuration.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC");
            configuration.setProperty("hibernate.connection.url", "jdbc:sqlite:crypto_manager.db");
            configuration.setProperty("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");

            configuration.addAnnotatedClass(Algorithm.class);
            configuration.addAnnotatedClass(EncryptionKey.class);
            configuration.addAnnotatedClass(FileEntity.class);
            configuration.addAnnotatedClass(Framework.class);
            configuration.addAnnotatedClass(Result.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            bootstrapData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void bootstrapData() {
        try {
            FrameworkDAO fwDAO = new FrameworkDAO();
            if (fwDAO.findAll().isEmpty()) {
                fwDAO.save(new Framework("OpenSSL"));
                fwDAO.save(new Framework("JCA"));
                System.out.println("Frameworks initialized.");
            }
        } catch (Exception e) {
            System.err.println("Nu s-au putut popula framework-urile: " + e.getMessage());
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}