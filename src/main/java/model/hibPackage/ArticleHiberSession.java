package model.hibPackage;

import model.entity.Article;
import org.hibernate.Session;

import java.util.List;

import static util.hibernate.HibernateUtil.getSessionFactory;

/**
 * Class works with table "Article" in DB
 */
public class ArticleHiberSession{

    public int create(Article entity) {
        try(Session session = getSessionFactory().openSession()){
            session.beginTransaction();
            session.save(entity);
            return entity.getId();
        }
    }

    public Article findById(int id) {
        try(Session session = getSessionFactory().openSession()){
            session.beginTransaction();
            return session.get(Article.class, id);
        }
    }

    public List<Article> findAll() {
        try(Session session = getSessionFactory().openSession()){
            session.beginTransaction();
            return (List<Article>) session.createQuery("from Article").list();
        }
    }

    public List<Article> findByPeriodical(int idPeriodical) {
        try(Session session = getSessionFactory().openSession()){
            session.beginTransaction();
            return session.createQuery("SELECT i FROM Article i JOIN FETCH i.idPeriodical as periodical " +
                    "where periodical.id = :periodicalID")
                    .setParameter("periodicalID", idPeriodical).list();
        }
    }

    public List<Article> findByPeriodicalFixedNumberOfArticles(int id, int limit, int offset) {
        try(Session session = getSessionFactory().openSession()){
            return session.createQuery("SELECT i FROM Article i JOIN FETCH i.idPeriodical as periodical " +
                    "where periodical.id = :periodicalID")
                    .setParameter("periodicalID", id).setFirstResult(offset).setMaxResults(limit).list();
        }
    }


    public void update(Article entity, int id) {

    }

    public void delete(int id) {

    }
}
