package controller.command;


import util.constant.Attributes;
import util.constant.Commands;
import util.constant.Pages;

import javax.servlet.http.HttpServletRequest;

public class AddPeriodicalPageCommand implements Command{

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter(Attributes.PERIODICAL_NAME);
        String description = request.getParameter(Attributes.PERIODICAL_DESCRIPTION);

        request.setAttribute(Attributes.PERIODICAL_NAME, name);
        request.setAttribute(Attributes.PERIODICAL_DESCRIPTION, description);
        request.getSession().setAttribute(Attributes.PAGE, Commands.ADD_PERIODICAL + "&" + Attributes.PERIODICAL_NAME + "="
                + name + "&" + Attributes.PERIODICAL_DESCRIPTION + "=" + description);
        return Pages.ADD_PERIODICAL;
    }
}
