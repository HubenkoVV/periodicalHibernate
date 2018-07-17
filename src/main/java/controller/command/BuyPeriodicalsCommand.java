package controller.command;

import model.entity.Payment;
import model.entity.Periodical;
import model.entity.User;
import model.entity.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.PaymentService;
import service.UserService;
import service.exception.IncorrectDataException;
import service.exception.NotEnoughMoneyException;
import util.constant.Attributes;
import util.constant.Exceptions;
import util.locale.LocalizeMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BuyPeriodicalsCommand implements Command {

    private static final Logger logger = LogManager.getLogger(BuyPeriodicalsCommand.class);
    private PaymentService paymentService;
    private UserService userService;

    public BuyPeriodicalsCommand(PaymentService paymentService, UserService userService) {
        this.paymentService = paymentService;
        this.userService = userService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute(Attributes.EXCEPTION,null);

        if(request.getSession().getAttribute(Attributes.USER_ROLE) == UserRole.GUEST){
            request.setAttribute(Attributes.EXCEPTION, LocalizeMessage.getException(Exceptions.NOT_LOGGED_IN));
            return (String) request.getSession().getAttribute(Attributes.PAGE);
        }

        final int[] price = {(Integer) request.getSession().getAttribute(Attributes.FULL_PRICE)};
        User user = (User) request.getSession().getAttribute(Attributes.USER);
        List<Periodical> periodicals = (List<Periodical>) request.getSession().getAttribute(Attributes.PERIODICALS_IN_BASKET);
        user.getPeriodicals().forEach(periodical -> {
            if(periodicals.contains(periodical)) {
                periodicals.remove(periodical);
                price[0] -= periodical.getPrice();
            }
        });

        try{
            Payment payment = new Payment()
                    .setIdUser(userService.getById(user.getId()))
                    .setPeriodicals(periodicals)
                    .setPrice(price[0]);
            payment = paymentService.createPayment(payment, user);
            userService.updateAccount(user, payment.getPrice());
        } catch (IncorrectDataException | NotEnoughMoneyException e) {
            request.setAttribute(Attributes.EXCEPTION, LocalizeMessage.getException(e.getMessage()));
            logger.info("Buying periodicals was failed");
            return (String) request.getSession().getAttribute(Attributes.PAGE);
        }

        request.getSession().setAttribute(Attributes.PERIODICALS_IN_BASKET, null);
        request.getSession().setAttribute(Attributes.USER, user);
        request.getSession().setAttribute(Attributes.FULL_PRICE, 0);
        logger.info("Periodicals from basket were bought. Price = " + price[0]);
        return (String) request.getSession().getAttribute(Attributes.PAGE);
    }
}
