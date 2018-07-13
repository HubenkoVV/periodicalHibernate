package controller.command;



import model.entity.Article;
import model.entity.Periodical;
import model.entity.UserRole;
import service.ArticleService;
import service.PeriodicalService;
import util.constant.Attributes;
import util.constant.Commands;
import util.constant.Exceptions;
import util.constant.Pages;
import util.locale.LocalizeMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class ArticleListCommand implements Command {

    private ArticleService articleService;
    private PeriodicalService periodicalService;

    ArticleListCommand(ArticleService articleService, PeriodicalService periodicalService) {
        this.articleService = articleService;
        this.periodicalService = periodicalService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        if(request.getSession().getAttribute(Attributes.USER_ROLE) == UserRole.GUEST){
            request.setAttribute(Attributes.EXCEPTION, LocalizeMessage.getException(Exceptions.NOT_LOGGED_IN));
            return (String) request.getSession().getAttribute(Attributes.PAGE);
        }

        String idPeriodical = request.getParameter(Attributes.ID_PERIODICAL);
        Periodical periodical = periodicalService.getById(Integer.parseInt(idPeriodical));

        String articlesPage = request.getParameter(Attributes.ARTICLES_PAGE);

        Map<Integer, List<Article>> articlesOnPage = articleService.getArticlesOnPagesByPeriodical(Integer.parseInt(idPeriodical),4);

        if (articlesPage == null || articlesPage.isEmpty()) articlesPage = "1";
        List<Article> articlesList = articlesOnPage.getOrDefault(Integer.parseInt(articlesPage), articlesOnPage.get(1));

        request.setAttribute(Attributes.PERIODICAL, periodical);
        request.setAttribute(Attributes.ARTICLE_LIST, articlesList);
        request.setAttribute(Attributes.PAGES, articlesOnPage.keySet());
        request.setAttribute(Attributes.CURRENT_PAGE, articlesPage);
        request.getSession().setAttribute(Attributes.PAGE, Commands.ARTICLES + "?" + Attributes.ARTICLES_PAGE
                + "=" + articlesPage + "&" + Attributes.ID_PERIODICAL + "=" + periodical.getId());
        return Pages.ARTICLES;
    }
}
