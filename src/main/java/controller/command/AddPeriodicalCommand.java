package controller.command;

import model.entity.Periodical;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.PeriodicalService;
import service.exception.IncorrectDataException;
import util.constant.*;
import util.locale.LocalizeMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class AddPeriodicalCommand implements Command {

    private static final Logger logger = LogManager.getLogger(AddPeriodicalCommand.class);
    private PeriodicalService periodicalService;

    AddPeriodicalCommand(PeriodicalService periodicalService) {
        this.periodicalService = periodicalService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute(Attributes.EXCEPTION, null);
        request.setAttribute(Attributes.MESSAGE, null);
        Periodical periodical = new Periodical()
                .setName(request.getParameter(Attributes.PERIODICAL_NAME))
                .setShortDescription(request.getParameter(Attributes.PERIODICAL_DESCRIPTION));

        try {
            String price = request.getParameter(Attributes.PERIODICAL_PRICE);
            checkPriceValue(price);
            periodical.setPrice(Integer.parseInt(price));
            periodicalService.createPeriodical(periodical);
        } catch (IncorrectDataException e) {
            logger.info("Failed adding periodical");
            request.setAttribute(Attributes.EXCEPTION, LocalizeMessage.getException(e.getMessage()));
            request.getSession().setAttribute(Attributes.PAGE, Commands.ADD_PERIODICAL + "?"+
                    Attributes.PERIODICAL_NAME + "=" + periodical.getName() + "&" + Attributes.PERIODICAL_DESCRIPTION + "="
                    + periodical.getShortDescription());
            return (String) request.getSession().getAttribute(Attributes.PAGE);
        }

        logger.info("Added periodical");
        request.setAttribute(Attributes.MESSAGE, LocalizeMessage.getMessage(Messages.ADD_PERIODICAL));
        request.getSession().setAttribute(Attributes.PAGE, Commands.PERIODICALS + "?" + Attributes.PERIODICAL_PAGE
                + "=" + 1);
        return (String) request.getSession().getAttribute(Attributes.PAGE);
    }

    private void checkPriceValue(String price) throws IncorrectDataException {
        if(!Pattern.compile(RegexForUser.MONEY).matcher(price).matches())
            throw new IncorrectDataException(Exceptions.WRONG_MONEY);
    }
}
