package service;

import model.entity.User;
import model.hibPackage.UserHiberSession;
import org.hibernate.exception.ConstraintViolationException;
import service.exception.IncorrectDataException;
import util.constant.Exceptions;
import util.constant.RegexForUser;
import util.secure.SecurePasswordMD5;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Vladyslava_Hubenko on 7/12/2018.
 */
public class UserService {

    private UserHiberSession session = new UserHiberSession();

    public User getByLoginAndPassword(String login, String password) throws IncorrectDataException {
        User user = session.findByLogin(login);
        if (user == null) {
            throw new IncorrectDataException(Exceptions.WRONG_LOGIN);
        }
        checkPassword(password, user.getPassword());
        return user;
    }

    public User createUser(User user, String password, String repeatPassword) throws IncorrectDataException {
        checkRegistrationData(user, password, repeatPassword);
        try {
            user.setId(session.create(user));
            return user;
        } catch (ConstraintViolationException ex) {
            throw new IncorrectDataException(Exceptions.INCORRECT_DATA);
        }
    }

    public User updateAccount(User user, String money) throws IncorrectDataException {
        if(!isDataCorrect(money, RegexForUser.MONEY))
            throw new IncorrectDataException(Exceptions.WRONG_MONEY);
        user.setMoney(user.getMoney() + Integer.parseInt(money));
        session.updateMoney(user);
        return user;
    }

    public User getById(int id) {
        return session.findById(id);
    }

    void checkRegistrationData(User user, String password, String repeatPassword) throws IncorrectDataException {
        if (!isDataCorrect(user.getLogin(), RegexForUser.LOGIN)) {
            throw new IncorrectDataException(Exceptions.INCORRECT_LOGIN);
        }
        if (!isDataCorrect(password, RegexForUser.PASSWORD)) {
            throw new IncorrectDataException(Exceptions.INCORRECT_PASSWORD);
        }
        if (!user.getPhone().equals("") && !isDataCorrect(user.getPhone(),RegexForUser.PHONE)) {
            throw new IncorrectDataException(Exceptions.INCORRECT_PHONE);
        }
        if(!password.equals(repeatPassword)){
            throw new IncorrectDataException(Exceptions.INCORRECT_PASSWORD);
        }
    }

    private void checkPassword(String password, String userPassword) throws IncorrectDataException {
        if(!SecurePasswordMD5.verifyPassword(password, userPassword)){
            throw new IncorrectDataException(Exceptions.WRONG_PASSWORD);
        }
    }

    private boolean isDataCorrect(String data, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(data);
        return m.matches();
    }

}
