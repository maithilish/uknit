package org.codetab.uknit.itest.flow.nosplit;

import org.codetab.uknit.itest.flow.nosplit.Model.Duck;

class TryCatchFinally {

    public String tryFoo(final Duck duck) {
        duck.swim("start");
        try {
            duck.swim("try");
        } catch (IllegalArgumentException e) {
            duck.swim("catch illegal arg");
        } finally {
            duck.swim("finally");
        }
        duck.swim("end");
        return duck.toString();
    }

    /**
     * Branches
     * <p>
     * 1st try - 2nd try - 3rd try
     * <p>
     * 1st try - 2nd try 1st catch - 3rd try
     * <p>
     * 1st try - 2nd try 2nd catch - 3rd try
     * <p>
     * 1st try 1st catch - 2nd try - 3rd try
     * <p>
     * 1st try 2nd catch - 2nd try - 3rd try
     *
     * @param duck
     * @return
     */
    public String tryMultiCatchFinallyFoo(final Duck duck) {
        duck.swim("start");
        try {
            duck.swim("try");
        } catch (IllegalStateException e) {
            duck.swim("catch illegal state");
        } catch (IllegalArgumentException e) {
            duck.swim("catch illegal arg");
        } finally {
            duck.swim("finally");
        }

        try {
            duck.swim("try 2");
        } catch (IllegalStateException e) {
            duck.swim("catch 2 illegal state");
        } catch (IllegalArgumentException e) {
            duck.swim("catch 2 illegal arg");
        } finally {
            duck.swim("finally 2");
        }

        try {
            duck.swim("try 3");
        } finally {
            duck.swim("finally 3");
        }
        duck.swim("end");
        return duck.toString();
    }
}
