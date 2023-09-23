package org.codetab.uknit.itest.flow.ifelse.ret;

import org.codetab.uknit.itest.flow.ifelse.ret.Model.Duck;

/**
 * TODO H - fix coverage issue.
 *
 * @author Maithilish
 *
 */
class IfElse {

    /**
     * STEPIN - In else path two contradictory stmts are generated
     * verify(duck).dive(state); and verify(duck, never()).dive(state); user has
     * to remove the second verify.
     *
     * The verify(duck, never()).dive(state); actually refers to the reassigned
     * state (say state2) which doesn't exists as we can't have
     * when(duck.fly("if canSwim").thenReturn(state2) which never gets invoked
     * in else test.
     *
     * @param duck
     * @param canSwim
     * @return
     */
    public String ifFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        String state = null;
        duck.dive(state);
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            duck.dive(state);
            return state;
        }
        duck.swim("end");
        return state;
    }

    public String ifElseFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        String state = null;
        duck.dive(state);
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            duck.dive(state);
            return state;
        } else {
            duck.swim("else canSwim");
            state = duck.fly("else canSwim");
            duck.dive(state);
            return state;
        }
    }

    public String ifElseFoo2(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        String state = null;
        duck.dive(state);
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            duck.dive(state);
            return state;
        } else {
            duck.swim("else canSwim");
            state = duck.fly("else canSwim");
            duck.dive(state);
        }
        duck.swim("end");
        return state;
    }

    public String ifNestIfFoo(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            duck.dive(state);
            if (done) {
                duck.swim("if done nest");
                state = duck.fly("if done nest");
                duck.dive(state);
                return state;
            } else {
                duck.swim("else done nest");
                state = duck.fly("else done nest");
                duck.dive(state);
                return state;
            }
        } else {
            duck.swim("else canSwim");
            state = duck.fly("else canSwim");
            duck.dive(state);
            return state;
        }
    }

    public String ifNestIfFoo2(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            duck.dive(state);
            if (done) {
                duck.swim("if done nest");
                state = duck.fly("if done nest");
                duck.dive(state);
                return state;
            } else {
                duck.swim("else done nest");
                state = duck.fly("else done nest");
                duck.dive(state);
                return state;
            }
        } else {
            duck.swim("else canSwim");
            state = duck.fly("else canSwim");
            duck.dive(state);
        }
        duck.swim("end");
        return state;
    }

    public String ifIfFoo(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            duck.dive(state);
            return state;
        } else {
            duck.swim("else canSwim");
            state = duck.fly("else canSwim");
            duck.dive(state);
        }
        if (done) {
            duck.swim("if done");
            state = duck.fly("if done");
            duck.dive(state);
            return state;
        } else {
            duck.swim("else done");
            state = duck.fly("else done");
            duck.dive(state);
            return state;
        }
    }

    public String ifIfFoo2(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            duck.dive(state);
            return state;
        } else {
            duck.swim("else canSwim");
            state = duck.fly("else canSwim");
            duck.dive(state);
        }
        if (done) {
            duck.swim("if done");
            state = duck.fly("if done");
            duck.dive(state);
            return state;
        } else {
            duck.swim("else done");
            state = duck.fly("else done");
            duck.dive(state);
        }
        duck.swim("end");
        return state;
    }

    public String ifNestIfIfFoo(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            duck.dive(state);
            if (canDive) {
                duck.swim("if canDive nest");
                state = duck.fly("if canDive nest");
                duck.dive(state);
                return state;
            } else {
                duck.swim("else canDive nest");
                state = duck.fly("else canDive nest");
                duck.dive(state);
            }
            if (done) {
                duck.swim("if done nest");
                state = duck.fly("if done nest");
                duck.dive(state);
                return state;
            } else {
                duck.swim("else done nest");
                state = duck.fly("else done nest");
                duck.dive(state);
                return state;
            }
        } else {
            duck.swim("else canSwim");
            state = duck.fly("else canSwim");
            duck.dive(state);
            return state;
        }
    }

    public String ifNestIfIfFoo2(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        String state = null;
        if (canSwim) {
            duck.swim("if canSwim");
            state = duck.fly("if canSwim");
            duck.dive(state);
            if (canDive) {
                duck.swim("if canDive nest");
                state = duck.fly("if canDive nest");
                duck.dive(state);
                return state;
            } else {
                duck.swim("else canDive nest");
                state = duck.fly("else canDive nest");
                duck.dive(state);
            }
            if (done) {
                duck.swim("if done nest");
                state = duck.fly("if done nest");
                duck.dive(state);
                return state;
            } else {
                duck.swim("else done nest");
                state = duck.fly("else done nest");
                duck.dive(state);
            }
        } else {
            duck.swim("else canSwim");
            state = duck.fly("else canSwim");
            duck.dive(state);
            return state;
        }
        duck.swim("end");
        return state;
    }

    // don't assign method invoke - verify instead of when
    public String ifNoAssignFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
            duck.fly("if canSwim");
            return duck.toString();
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifElseNoAssignFoo(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
            duck.fly("if canSwim");
            return duck.toString();
        } else {
            duck.swim("else canSwim");
            duck.fly("else canSwim");
            return duck.toString();
        }
    }

    public String ifElseNoAssignFoo2(final Duck duck, final boolean canSwim) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
            duck.fly("if canSwim");
            return duck.toString();
        } else {
            duck.swim("else canSwim");
            duck.fly("else canSwim");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifNestIfNoAssignFoo(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
            duck.fly("if canSwim");
            if (done) {
                duck.swim("if done nest");
                duck.fly("if done nest");
                duck.swim("if done nest end");
                return duck.toString();
            } else {
                duck.swim("else done nest");
                duck.fly("else done nest");
                duck.swim("else done nest end");
                return duck.toString();
            }
        } else {
            duck.swim("else canSwim");
            duck.fly("else canSwim");
            duck.swim("else canSwim end");
            return duck.toString();
        }
    }

    public String ifNestIfNoAssignFoo2(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
            duck.fly("if canSwim");
            if (done) {
                duck.swim("if done nest");
                duck.fly("if done nest");
                duck.swim("if done nest end");
                return duck.toString();
            } else {
                duck.swim("else done nest");
                duck.fly("else done nest");
                duck.swim("else done nest end");
            }
        } else {
            duck.swim("else canSwim");
            duck.fly("else canSwim");
            duck.swim("else canSwim end");
            return duck.toString();
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifIfNoAssignFoo(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
            duck.fly("if canSwim");
            return duck.toString();
        } else {
            duck.swim("else canSwim");
            duck.fly("else canSwim");
        }
        if (done) {
            duck.swim("if done");
            duck.fly("if done");
            return duck.toString();
        } else {
            duck.swim("else done");
            duck.fly("else done");
            return duck.toString();
        }
    }

    public String ifIfNoAssignFoo2(final Duck duck, final boolean canSwim,
            final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
            duck.fly("if canSwim");
            return duck.toString();
        } else {
            duck.swim("else canSwim");
            duck.fly("else canSwim");
        }
        if (done) {
            duck.swim("if done");
            duck.fly("if done");
            return duck.toString();
        } else {
            duck.swim("else done");
            duck.fly("else done");
        }
        duck.swim("end");
        return duck.toString();
    }

    public String ifNestIfIfNoAssignFoo(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
            duck.fly("if canSwim");
            if (canDive) {
                duck.swim("if canDive nest");
                duck.fly("if canDive nest");
                return duck.toString();
            } else {
                duck.swim("else canDive nest");
                duck.fly("else canDive nest");
            }
            if (done) {
                duck.swim("if done nest");
                duck.fly("if done nest");
                return duck.toString();
            } else {
                duck.swim("else done nest");
                duck.fly("else done nest");
                return duck.toString();
            }
        } else {
            duck.swim("else canSwim");
            duck.fly("else canSwim");
            return duck.toString();
        }
    }

    public String ifNestIfIfNoAssignFoo2(final Duck duck, final boolean canSwim,
            final boolean canDive, final boolean done) {
        duck.swim("start");
        if (canSwim) {
            duck.swim("if canSwim");
            duck.fly("if canSwim");
            if (canDive) {
                duck.swim("if canDive nest");
                duck.fly("if canDive nest");
                return duck.toString();
            } else {
                duck.swim("else canDive nest");
                duck.fly("else canDive nest");
            }
            if (done) {
                duck.swim("if done nest");
                duck.fly("if done nest");
                return duck.toString();
            } else {
                duck.swim("else done nest");
                duck.fly("else done nest");
                return duck.toString();
            }
        } else {
            duck.swim("else canSwim");
            duck.fly("else canSwim");
        }
        duck.swim("end");
        return duck.toString();
    }
}
