package org.codetab.uknit.itest.flow.trycatch;

import org.codetab.uknit.itest.flow.trycatch.Model.Duck;

class TryNestTry {

    public String tryNestTryFoo(final Duck duck) {
        duck.swim("start");
        try {
            duck.swim("try");
            try {
                duck.swim("try try");
            } catch (IllegalStateException e) {
                duck.swim("try try catch");
            } finally {
                duck.swim("try try finally");
            }
        } catch (IllegalStateException e) {
            duck.swim("catch");
        } finally {
            duck.swim("finally");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String tryCatchNestTryFoo(final Duck duck) {
        duck.swim("start");
        try {
            duck.swim("try");
        } catch (IllegalStateException e) {
            duck.swim("catch");
            try {
                duck.swim("catch try");
            } catch (IllegalStateException e1) {
                duck.swim("catch catch");
            } finally {
                duck.swim("catch finally");
            }
        } finally {
            duck.swim("finally");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String tryFinallyNestTryFoo(final Duck duck) {
        duck.swim("start");
        try {
            duck.swim("try");
        } catch (IllegalStateException e) {
            duck.swim("catch");
        } finally {
            duck.swim("finally");
            try {
                duck.swim("finally try");
            } catch (IllegalStateException e) {
                duck.swim("finally catch");
            } finally {
                duck.swim("finally finally");
            }
        }
        duck.swim("end");
        return duck.toString();
    }

    public String tryNestTryPlusIfFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        try {
            duck.swim("try");
            try {
                duck.swim("try try");
            } catch (IllegalStateException e) {
                duck.swim("try try catch");
            } finally {
                duck.swim("try try finally");
            }
        } catch (IllegalStateException e) {
            duck.swim("catch");
        } finally {
            duck.swim("finally");
        }
        if (canSwim) {
            duck.swim("if");
        } else {
            duck.swim("else");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String tryCatchNestTryPlusIfFoo(final Duck duck,
            final boolean canSwim) {
        duck.swim("start");
        try {
            duck.swim("try");
        } catch (IllegalStateException e) {
            duck.swim("catch");
            try {
                duck.swim("catch try");
            } catch (IllegalStateException e1) {
                duck.swim("catch catch");
            } finally {
                duck.swim("catch finally");
            }
        } finally {
            duck.swim("finally");
        }
        if (canSwim) {
            duck.swim("if");
        } else {
            duck.swim("else");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String tryFinallyNestTryPlusIfFoo(final Duck duck,
            final boolean canSwim) {
        duck.swim("start");
        try {
            duck.swim("try");
        } catch (IllegalStateException e) {
            duck.swim("catch");
        } finally {
            duck.swim("finally");
            try {
                duck.swim("finally try");
            } catch (IllegalStateException e) {
                duck.swim("finally catch");
            } finally {
                duck.swim("finally finally");
            }
        }
        if (canSwim) {
            duck.swim("if");
        } else {
            duck.swim("else");
        }
        duck.swim("end");
        return duck.toString();
    }
}
