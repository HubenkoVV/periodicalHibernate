package controller.command;

import model.entity.Article;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ArticleService;
import util.constant.Attributes;
import util.constant.Commands;
import util.constant.Pages;

import javax.servlet.http.HttpServletRequest;

public class ShowArticleCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ShowArticleCommand.class);
    private ArticleService articleService;

    ShowArticleCommand(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int idArticle = Integer.parseInt(request.getParameter(Attributes.ARTICLE_ID));
        Article article = articleService.getById(idArticle);

        request.setAttribute(Attributes.PERIODICAL, article.getIdPeriodical());
        request.setAttribute(Attributes.ARTICLE, article);
        request.getSession().setAttribute(Attributes.PAGE, Commands.SHOW_ARTICLE + "?"+ Attributes.ARTICLE_ID + "=" + idArticle );
        logger.info("Show article with id = " + idArticle);
        return Pages.ARTICLE;
    }
}
