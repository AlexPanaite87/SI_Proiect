package com.si.proiect.dao;

import com.si.proiect.DatabaseManager;
import com.si.proiect.entity.EncryptionKey;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class EncryptionKeyDAO {
    public void save(EncryptionKey key) {
        Transaction transaction = null;
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(key);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<EncryptionKey> findAll() {
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            return session.createQuery("from EncryptionKey", EncryptionKey.class).list();
        }
    }
}