package com.si.proiect.dao;

import com.si.proiect.DatabaseManager;
import com.si.proiect.entity.Framework;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FrameworkDAO {

    public void save(Framework framework) {
        Transaction transaction = null;
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(framework);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Framework> findAll() {
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            return session.createQuery("from Framework", Framework.class).list();
        }
    }
}