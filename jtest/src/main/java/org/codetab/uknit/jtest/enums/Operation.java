package org.codetab.uknit.jtest.enums;

/**
 * TODO H - fix the critical error.
 *
 * @author m
 *
 */
public enum Operation {
    PLUS {
        @Override
        double eval(final double x, final double y) {
            return x + y;
        }
    },

    MINUS {
        @Override
        double eval(final double x, final double y) {
            return x - y;
        }
    },

    TIMES {
        @Override
        double eval(final double x, final double y) {
            return x * y;
        }
    },

    DIVIDED_BY {
        @Override
        double eval(final double x, final double y) {
            return x / y;
        }
    };

    private Log log;

    abstract double eval(double x, double y);

    public void op(final int x, final int y) {
        for (Operation op : Operation.values()) {
            log.debug(x + " " + op + " " + y + " = " + op.eval(x, y));
        }
    }

    interface Log {
        void debug(String msg);
    }
}
