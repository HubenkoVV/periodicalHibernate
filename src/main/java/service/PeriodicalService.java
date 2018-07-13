package service;

import model.entity.Periodical;
import model.hibPackage.PeriodicalHiberSession;
import org.hibernate.exception.ConstraintViolationException;
import service.exception.IncorrectDataException;
import util.constant.Exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeriodicalService {
    
    private PeriodicalHiberSession session = new PeriodicalHiberSession();
    
    public Periodical createPeriodical(Periodical periodical) throws IncorrectDataException {
        try {
            periodical.setId(session.create(periodical));
            return periodical;
        } catch (ConstraintViolationException ex) {
            throw new IncorrectDataException(Exceptions.INCORRECT_DATA);
        }
    }

    public Periodical getById(int id) {
        return session.findById(id);
    }

    public Map<Integer, List<Periodical>> getPeriodicalsOnPages(int periodicalsOnPage) {
        Map<Integer, List<Periodical>> result = new HashMap<>();
        List<Periodical> pageOfPeriodicals = session.findFixedNumberOfPeriodicals(periodicalsOnPage, 0);

        for (int pageNumber = 1; !pageOfPeriodicals.isEmpty(); pageNumber++) {
            result.put(pageNumber, pageOfPeriodicals);
            pageOfPeriodicals = session.findFixedNumberOfPeriodicals(periodicalsOnPage, periodicalsOnPage*pageNumber);
        }
        return result;
    }

    public Map<Integer, List<Periodical>> searchPeriodicals(String name, int periodicalsOnPage) {
        Map<Integer, List<Periodical>> result = new HashMap<>();
        List<Periodical> pageOfPeriodicals = session.searchPeriodicals(name, periodicalsOnPage, 0);

        for (int pageNumber = 1; !pageOfPeriodicals.isEmpty(); pageNumber++) {
            result.put(pageNumber, pageOfPeriodicals);
            pageOfPeriodicals = session.searchPeriodicals(name, periodicalsOnPage, periodicalsOnPage*pageNumber);
        }
        return result;
    }


}
