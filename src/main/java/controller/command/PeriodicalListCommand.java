package controller.command;


import model.entity.Periodical;
import model.entity.User;
import org.hibernate.Hibernate;
import service.PeriodicalService;
import service.UserService;
import util.constant.Attributes;
import util.constant.Commands;
import util.constant.Pages;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PeriodicalListCommand implements Command {

    private PeriodicalService periodicalService;
    private UserService userService;

    PeriodicalListCommand(PeriodicalService periodicalService, UserService userService) {
        this.periodicalService = periodicalService;
        this.userService = userService;
    }
    @Override
    public String execute(HttpServletRequest request) {

        String searchName = request.getParameter(Attributes.PERIODICAL_SEARCH);
        List<Periodical> periodicalsOfUser;
        Map<Integer, List<Periodical>> periodicalsOnPage;
        if(searchName == null || searchName.isEmpty() || searchName.equals("null")) {
            periodicalsOnPage = periodicalService.getPeriodicalsOnPages(4);
        }
        else {
            periodicalsOnPage = periodicalService.searchPeriodicals(searchName,4);
        }

        String periodicalPage = request.getParameter(Attributes.PERIODICAL_PAGE);

        if (periodicalPage == null || periodicalPage.isEmpty()) periodicalPage = "1";

        List<Periodical> periodicalList =
                periodicalsOnPage.getOrDefault(Integer.parseInt(periodicalPage), periodicalsOnPage.get(1));

        request.setAttribute(Attributes.PAGES, periodicalsOnPage.keySet());
        request.setAttribute(Attributes.CURRENT_PAGE, periodicalPage);
        request.setAttribute(Attributes.PERIODICAL_LIST, periodicalList);

        User user =  (User) request.getSession().getAttribute(Attributes.USER);

        if (user != null) {
            periodicalsOfUser = userService.getById(user.getId()).getPeriodicals();
        } else {
            periodicalsOfUser = new ArrayList<>();
        }
        request.setAttribute(Attributes.PERIODICALS_USER, periodicalsOfUser);
        request.getSession().setAttribute(Attributes.PAGE, Commands.PERIODICALS + "?" + Attributes.PERIODICAL_PAGE
                + "=" + periodicalPage + "&" + Attributes.PERIODICAL_SEARCH + "=" + searchName);
        return Pages.PERIODICALS;
    }
}
