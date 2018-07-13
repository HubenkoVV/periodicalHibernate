package controller.command;

import model.entity.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.constant.Attributes;
import util.constant.Pages;

import javax.servlet.http.HttpServletRequest;

public class SignOutCommand implements Command {

    private static final Logger logger = LogManager.getLogger(SignOutCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().setAttribute(Attributes.USER_ROLE, UserRole.GUEST);
        request.getSession().setAttribute(Attributes.USER, null);
        logger.info("Sign out");
        request.getSession().setAttribute(Attributes.PAGE, Pages.HOME);
        return Pages.HOME;
    }
}
