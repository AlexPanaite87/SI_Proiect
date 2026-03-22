package com.si.proiect.dao;
import com.si.proiect.DatabaseManager;
import com.si.proiect.entity.Result;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ResultDAO {
    public void save(Result result) {
        Transaction tx = null;
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(result);
            tx.commit();
        } catch (Exception e) { if (tx != null) tx.rollback(); e.printStackTrace(); }
    }
    public List<Result> findAll() {
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            return session.createQuery("from Result", Result.class).list();
        }
    }
}