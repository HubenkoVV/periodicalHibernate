package controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import model.entity.Article;
import service.ArticleService;
import service.PeriodicalService;
import service.exception.IncorrectDataException;
import util.constant.Attributes;
import util.constant.Commands;
import util.constant.Messages;
import util.locale.LocalizeMessage;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

public class AddArticleCommand implements Command{

    private ArticleService articleService;
    private PeriodicalService periodicalService;
    private static final Logger logger = LogManager.getLogger(AddArticleCommand.class);

    public AddArticleCommand(ArticleService articleService, PeriodicalService periodicalService) {
        this.articleService = articleService;
        this.periodicalService = periodicalService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute(Attributes.EXCEPTION, null);
        request.setAttribute(Attributes.MESSAGE, null);

        Article article = new Article()
                .setDate(LocalDate.now())
                .setIdPeriodical(periodicalService
                        .getById(Integer.parseInt(request.getParameter(Attributes.ID_PERIODICAL))))
                .setName(request.getParameter(Attributes.ARTICLE_NAME))
                .setText(request.getParameter(Attributes.ARTICLE_TEXT));

        try {
            article = articleService.createArticle(article);
        } catch (IncorrectDataException e) {
            logger.info("Failed adding article");

            request.setAttribute(Attributes.EXCEPTION, LocalizeMessage.getException(e.getMessage()));
            request.getSession().setAttribute(Attributes.PAGE, Commands.ADD_ARTICLE + "?"+
                    Attributes.ARTICLE_NAME + "=" + article.getName() + "&" + Attributes.ARTICLE_TEXT + "=" + article.getText()
                    + "&" + Attributes.ID_PERIODICAL + "=" + article.getIdPeriodical());
            return (String) request.getSession().getAttribute(Attributes.PAGE);
        }

        request.setAttribute(Attributes.ARTICLE, article);
        request.setAttribute(Attributes.MESSAGE, LocalizeMessage.getMessage(Messages.ADD_ARTICLE));
        request.getSession().setAttribute(Attributes.PAGE, Commands.SHOW_ARTICLE + "?"+ Attributes.ARTICLE_ID + "=" + article.getId() );
        logger.info("Added article");
        return (String) request.getSession().getAttribute(Attributes.PAGE);
    }
}
