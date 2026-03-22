package com.si.proiect.dao;

import com.si.proiect.DatabaseManager;
import com.si.proiect.entity.Algorithm;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AlgorithmDAO {

    public void save(Algorithm algorithm) {
        Transaction transaction = null;
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(algorithm);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Algorithm> findAll() {
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            return session.createQuery("from Algorithm", Algorithm.class).list();
        }
    }
}