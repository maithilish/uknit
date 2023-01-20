package org.codetab.uknit.itest.internal;

import java.time.Instant;
import java.util.Locale;

import org.codetab.uknit.itest.internal.Model.Holder;
import org.codetab.uknit.itest.internal.Model.Provider;

/**
 * Two internal methods with similarly named local vars.
 *
 * @author Maithilish
 *
 */
class VarConflict {

    private Provider provider;
    private Holder otherHolder;

    /**
     * Calls a method that uses Instant whose var named other.
     */
    public void callSameTwice() {
        Holder holder = provider.getHolder();
        callSameTwice(holder);
    }

    private void callSameTwice(final Holder holder) {
        compareInstant(holder.getInstant());
        compareInstant(holder.getInstant());
    }

    /**
     * Two methods uses Instant with same local var named other.
     */
    public void callTwoMethodsOfSameType() {
        Holder holder = provider.getHolder();
        callTwoMethodsOfSameType(holder);
    }

    private void callTwoMethodsOfSameType(final Holder holder) {
        compareInstant(holder.getInstant());
        compareInstant2(holder.getInstant());
    }

    /**
     * One method uses Instant and another Locale but with same local var named
     * other.
     */
    public void callTwoMethodsOfDiffTypes() {
        Holder holder = provider.getHolder();
        callTwoMethodsOfDiffTypes(holder);
    }

    private void callTwoMethodsOfDiffTypes(final Holder holder) {
        compareInstant(holder.getInstant());
        compareLocale(holder.getLocale());
    }

    private void compareInstant(final Instant instant) {
        Instant other = otherHolder.getInstant();
        instant.compareTo(other);
    }

    private void compareInstant2(final Instant instant) {
        Instant other = otherHolder.getInstant();
        instant.compareTo(other);
    }

    private void compareLocale(final Locale locale) {
        Locale other = otherHolder.getLocale();
        locale.getDisplayCountry(other);
    }

}
