package com.si.proiect.dao;
import com.si.proiect.DatabaseManager;
import com.si.proiect.entity.FileEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class FileEntityDAO {
    public void save(FileEntity file) {
        Transaction tx = null;
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(file);
            tx.commit();
        } catch (Exception e) { if (tx != null) tx.rollback(); e.printStackTrace(); }
    }
    public List<FileEntity> findAll() {
        try (Session session = DatabaseManager.getSessionFactory().openSession()) {
            return session.createQuery("from FileEntity", FileEntity.class).list();
        }
    }
}