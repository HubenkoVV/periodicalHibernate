package controller.command;

import model.entity.User;
import model.entity.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import service.exception.IncorrectDataException;
import util.constant.Attributes;
import util.locale.LocalizeMessage;
import util.secure.SecurePasswordMD5;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {

    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);
    private UserService userService;

    RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String email = request.getParameter(Attributes.LOGIN);
        request.setAttribute(Attributes.EXCEPTION, null);
        request.setAttribute(Attributes.MESSAGE, null);
        try {
            User user = userService.createUser(
                    new User().setLogin(request.getParameter(Attributes.LOGIN))
                            .setName(request.getParameter(Attributes.NAME))
                            .setSurname(request.getParameter(Attributes.SURNAME))
                            .setPassword(SecurePasswordMD5.getSecurePassword(request.getParameter(Attributes.PASSWORD)))
                            .setRole(UserRole.USER)
                            .setMoney(0)
                            .setPhone(request.getParameter(Attributes.PHONE))
                    , request.getParameter(Attributes.PASSWORD), request.getParameter(Attributes.PASSWORD_AGAIN));
            request.getSession().setAttribute(Attributes.USER, user);
            request.getSession().setAttribute(Attributes.USER_ROLE, user.getRole());
        } catch (IncorrectDataException e) {
            request.setAttribute(Attributes.REG_EXCEPTION, LocalizeMessage.getException(e.getMessage()));
            logger.info("Registration by \'" + email + "\' was failed");
            request.setAttribute(Attributes.OPEN_REGISTRATION, "$(\"#registration_modal\").modal({show: true});");
            return (String) request.getSession().getAttribute(Attributes.PAGE);
        }

        logger.info("Registration by \'" + email + "\'");
        return (String) request.getSession().getAttribute(Attributes.PAGE);
    }
}
