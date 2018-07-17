package model.hibPackage;

import model.entity.Payment;
import model.entity.Periodical;
import model.entity.User;
import org.hibernate.Session;

import javax.persistence.Query;
import java.io.Serializable;
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
            session.saveOrUpdate(entity);

            User user = session.get(User.class, entity.getIdUser().getId());
            user.getPayments().add(entity);

            for (Periodical p: entity.getPeriodicals()) {
                p.getUsers().add(user);
                session.saveOrUpdate(p);
            }
            session.getTransaction().commit();
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
            return session.createQuery("SELECT i FROM Payment i JOIN FETCH i.idUser as user " +
                    "where user.id = :IdUser")
                    .setParameter("IdUser", idUser).list();
        }
    }

    public List<Payment> findByPeriodical(int idPeriodical) {
        try(Session session = getSessionFactory().openSession()){
            session.beginTransaction();
            return session.createQuery("SELECT i FROM Payment i JOIN FETCH i.periodicals as periodical " +
                    "where periodical.id = :periodicalID").setParameter("periodicalID", idPeriodical).list();
        }
    }

    public void update(Payment entity, int id) {

    }

    public void delete(int id) {

    }

}
