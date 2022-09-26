package org.codetab.uknit.itest.optional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Optional is empty at present. But it should be initialized with value.
 *
 * TODO - refactor for proper initialization with value. Add more test cases.
 *
 * @author m
 *
 */
public class OptionalLists {

    public boolean collect(final OptionalListHolder holder, final Date inDate) {
        Optional<List<Date>> dates = holder.getDates();

        if (dates.isPresent()) {
            for (Date date : dates.get()) {
                if (date.equals(inDate)) {
                    return true;
                }
            }
        }
        return false;
    }
}

class OptionalListHolder {

    private List<Date> dates;

    public Optional<List<Date>> getDates() {
        return Optional.of(dates);
    }
}
