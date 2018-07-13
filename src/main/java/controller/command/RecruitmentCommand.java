package controller.command;

import model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import service.exception.IncorrectDataException;
import util.constant.Attributes;
import util.locale.LocalizeMessage;


import javax.servlet.http.HttpServletRequest;

public class RecruitmentCommand implements Command {

    private static final Logger logger = LogManager.getLogger(RecruitmentCommand.class);
    private UserService userService;

    RecruitmentCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute(Attributes.EXCEPTION, null);
        User user = (User) request.getSession().getAttribute(Attributes.USER);
        try {
            user = userService.updateAccount(user, request.getParameter(Attributes.MONEY));
            request.getSession().setAttribute(Attributes.USER, user);
        } catch (IncorrectDataException e) {
            logger.info("Update account for \'" + user.getLogin() + "\' was failed");
            request.setAttribute(Attributes.EXCEPTION, LocalizeMessage.getException(e.getMessage()));
            return (String) request.getSession().getAttribute(Attributes.PAGE);
        }
        logger.info("Successful updating account for \'" + user.getLogin() + "\'");
        return (String) request.getSession().getAttribute(Attributes.PAGE);
    }
}
