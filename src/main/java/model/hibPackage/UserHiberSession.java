package model.hibPackage;

import model.entity.User;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

import static util.hibernate.HibernateUtil.getSessionFactory;

/**
 * Created by Vladyslava_Hubenko on 7/12/2018.
 */
public class UserHiberSession{

    public User findByLogin(String login) {
        try(Session session = getSessionFactory().openSession()){
            session.beginTransaction();
            return (User) session.createCriteria(User.class)
                    .add(Restrictions.eq("login", login)).uniqueResult();
        }
    }

    public void updateMoney(User user) {
        try(Session session = getSessionFactory().openSession()){
            session.beginTransaction();
            session.saveOrUpdate(user);
        }
    }

    
    public List<User> findByPeriodical(int idPeriodical) {
        try(Session session = getSessionFactory().openSession()){
            session.beginTransaction();
            return session.createQuery("from User user join user.periodicals p " +
                    "where p.id = :idPeriodical").setParameter("idPeriodical", idPeriodical).list();
        }
    }

    public int create(User entity) {
        try(Session session = getSessionFactory().openSession()){
            session.beginTransaction();
            session.save(entity);
            return entity.getId();
        }
    }

    public User findById(int id) {
        try(Session session = getSessionFactory().openSession()){
            session.beginTransaction();
            return session.get(User.class, id);
        }
    }

    public List<User> findAll() {
        try(Session session = getSessionFactory().openSession()){
            session.beginTransaction();
            return (List<User>) session.createQuery("from User").list();
        }
    }

    public void update(User entity, int id) {
        try(Session session = getSessionFactory().openSession()){
            session.beginTransaction();
            session.saveOrUpdate(entity);
        }
    }

    public void delete(int id) {
        try(Session session = getSessionFactory().openSession()){
            session.beginTransaction();
            session.delete(findById(id));
        }
    }
}
