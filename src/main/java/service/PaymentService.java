package service;


import model.entity.Payment;
import model.entity.User;
import model.hibPackage.PaymentHiberSession;
import org.hibernate.exception.ConstraintViolationException;
import service.exception.IncorrectDataException;
import service.exception.NotEnoughMoneyException;
import util.constant.Exceptions;

public class PaymentService {

    private PaymentHiberSession session = new PaymentHiberSession();

    public Payment createPayment(Payment payment, User user) throws IncorrectDataException, NotEnoughMoneyException {
        checkUserBalance(payment.getPrice(), user.getMoney());
        try {
            payment.setId(session.create(payment));
            return payment;
        } catch (ConstraintViolationException ex){
            throw new IncorrectDataException(Exceptions.INCORRECT_DATA);
        }
    }

    private void checkUserBalance(int price, int balance) throws NotEnoughMoneyException {
        if(price > balance)
            throw new NotEnoughMoneyException(Exceptions.NOT_ENOUGH_MONEY);
    }
}
