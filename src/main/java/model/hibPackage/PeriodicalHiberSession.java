package model.hibPackage;

import model.entity.Periodical;
import org.hibernate.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static util.hibernate.HibernateUtil.getSessionFactory;

/**
 * Class works with table "Periodical" in DB
 */
public class PeriodicalHiberSession {

    public int create(Periodical entity) {
        try(Session session = getSessionFactory().openSession()){
            session.beginTransaction();
            session.save(entity);
            return entity.getId();
        }
    }

    public Periodical findById(int id) {
        try(Session session = getSessionFactory().openSession()){
            session.beginTransaction();
            return session.get(Periodical.class, id);
        }
    }

    public List<Periodical> findAll() {
        try(Session session = getSessionFactory().openSession()){
            session.beginTransaction();
            return (List<Periodical>) session.createQuery("from Periodical").list();
        }
    }

    public List<Periodical> findByUser(int idUser) {
        try(Session session = getSessionFactory().openSession()){
            session.beginTransaction();
            return session.createQuery("from Periodical periodical join periodical.users u" +
                    " where u.id = :idUser").setParameter("idUser", idUser).list();
        }
    }

    public List<Periodical> findByPayment(int idPayment) {
        try(Session session = getSessionFactory().openSession()){
            session.beginTransaction();
            return session.createQuery("from Periodical periodical join periodical.payments p " +
                    "where p.id = :idPayment").setParameter("idPayment", idPayment).list();
        }
    }

    public List<Periodical> findFixedNumberOfPeriodicals(int limit, int offset) {
        try(Session session = getSessionFactory().openSession()){
            return session.createQuery("from Periodical").setFirstResult(offset).setMaxResults(limit).list();
        }
    }

    public List<Periodical> searchPeriodicals(String name, int limit, int offset) {
        try(Session session = getSessionFactory().openSession()){
            return session.createQuery("from Periodical periodical where periodical.name like :Name")
                    .setParameter("Name", "%" + name + "%").setFirstResult(offset).setMaxResults(limit).list();
        }
    }

    public void update(Periodical entity, int id) {
    }

    public void delete(int id) {
    }
}
