package service;

import model.entity.Article;
import model.hibPackage.ArticleHiberSession;
import org.hibernate.exception.ConstraintViolationException;
import service.exception.IncorrectDataException;
import util.constant.Exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleService {

    private ArticleHiberSession session = new ArticleHiberSession();

    public Article createArticle(Article article) throws IncorrectDataException {
        try {
            article.setId(session.create(article));
            return article;
        } catch (ConstraintViolationException ex) {
            throw new IncorrectDataException(Exceptions.INCORRECT_DATA);
        }
    }

    public Article getById(int id) {
        return session.findById(id);
    }

    public Map<Integer, List<Article>> getArticlesOnPagesByPeriodical(int id, int articlesOnPage) {
        Map<Integer, List<Article>> result = new HashMap<>();
        List<Article> pageOfArticles = session.findByPeriodicalFixedNumberOfArticles(id, articlesOnPage, 0);
        for (int pageNumber = 1; !pageOfArticles.isEmpty(); pageNumber++) {
            result.put(pageNumber, pageOfArticles);
            pageOfArticles = session
                    .findByPeriodicalFixedNumberOfArticles(id, articlesOnPage, articlesOnPage*pageNumber);
        }
        return result;
    }

    String textareaToHTML(String text){
        String textInHTML = text.replaceAll("\r\n", "<p>");
        return "<p>" + textInHTML;
    }
}
