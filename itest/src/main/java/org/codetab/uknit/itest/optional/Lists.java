package org.codetab.uknit.itest.optional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Optional is empty at present. But it should be initialized with value.
 *
 * TODO L - refactor for proper initialization of Optional. Add more test cases.
 * Load list if any of misuse such as equals is called.
 *
 * @author Maithilish
 *
 */
class Lists {

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
