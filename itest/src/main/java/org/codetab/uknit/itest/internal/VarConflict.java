package org.codetab.uknit.itest.internal;

import java.util.Date;
import java.util.Locale;

/**
 * Two private methods with similarly named local vars.
 * <p>
 * To avoid conflict between vars Date other and Local other, the Local other is
 * named as otherApple. The final set vars: <code>
 *      Date date = Mockito.mock(Date.class);
 *      Date other = Mockito.mock(Date.class);
 *      Locale locale = Mockito.mock(Locale.class);
 *      Locale otherApple = Mockito.mock(Locale.class);
 * </code>
 * @author Maithilish
 *
 */
public class VarConflict {

    private Provider provider;
    private Holder otherHolder;

    public void compare() {
        Holder holder = provider.getHolder();
        compare(holder);
    }

    private void compare(final Holder holder) {
        compareDate(holder.getDate());
        compareLocale(holder.getLocale());
    }

    private void compareDate(final Date date) {
        Date other = otherHolder.getDate();
        date.compareTo(other);
    }

    private void compareLocale(final Locale locale) {
        Locale other = otherHolder.getLocale();
        locale.getDisplayCountry(other);
    }

}

interface Provider {
    Holder getHolder();
}

class Holder {

    private Date date;
    private Locale locale;

    public Date getDate() {
        return date;
    }

    public Locale getLocale() {
        return locale;
    }
}
