package org.codetab.uknit.itest.optional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * When Optional contains list then Optional.get() in arg is not patched and
 * NoSuchElement exception is thrown. Also, list is not defined.
 *
 * TODO - Refactor to fix the issue.
 *
 * @author Maithilish
 *
 */
class Ops {

    static class Dates {
        public Optional<List<Date>> getDates() {
            return null;
        }

        public void doSomething(final List<Date> date, final String format) {

        }
    }

    private Dates dates;

    public void foo(final Date date) {
        Optional<List<Date>> dateO = dates.getDates();
        String format = "foo";
        if (dateO.isPresent()) {
            dates.doSomething(dateO.get(), format);
        }
    }
}
