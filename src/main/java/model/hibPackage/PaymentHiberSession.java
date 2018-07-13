package model.hibPackage;

import model.entity.Payment;
import org.hibernate.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static util.hibernate.HibernateUtil.getSessionFactory;

/**
 * Class works with table "Payment" in DB
 */
public class PaymentHiberSession{

    public int create(Payment entity) {
        try(Session session = getSessionFactory().openSession()){
            session.beginTransaction();
            session.save(entity);
            return entity.getId();
        }
    }

    public Payment findById(int id) {
        try(Session session = getSessionFactory().openSession()){
            session.beginTransaction();
            return session.get(Payment.class, id);
        }
    }

    public List<Payment> findAll() {
        try(Session session = getSessionFactory().openSession()){
            session.beginTransaction();
            return (List<Payment>) session.createQuery("from Payment").list();
        }
    }

    public List<Payment> findByUser(int idUser) {
        try(Session session = getSessionFactory().openSession()){
            session.beginTransaction();
            return session.createQuery("from Payment payment where payment.idUser = :IdUser")
                    .setParameter("IdUser", idUser).list();
        }
    }

    public List<Payment> findByPeriodical(int idPeriodical) {
        try(Session session = getSessionFactory().openSession()){
            session.beginTransaction();
            return session.createQuery("from Article article join article.periodicals p " +
                    "where p.id = :idPeriodical").setParameter("idPeriodical", idPeriodical).list();
        }
    }

    public void update(Payment entity, int id) {

    }

    public void delete(int id) {

    }

}
