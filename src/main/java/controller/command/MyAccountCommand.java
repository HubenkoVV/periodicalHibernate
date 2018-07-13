package controller.command;

import util.constant.Attributes;
import util.constant.Pages;

import javax.servlet.http.HttpServletRequest;

public class MyAccountCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().setAttribute(Attributes.PAGE, Pages.ACCOUNT);
        return Pages.ACCOUNT;
    }
}
