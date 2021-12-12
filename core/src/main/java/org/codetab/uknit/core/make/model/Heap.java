package org.codetab.uknit.core.make.model;

import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.Expression;

public class Heap {

    /*
     * List of vars - localVars, parameters, fields, returnVar. Note: Normally
     * one of the localVar acts as returnVar, however for real type fields
     * explicit ReturnVar is created as they are not injected as mocks
     */
    @Inject
    private List<IVar> vars;
    @Inject
    private List<When> whens;
    @Inject
    private List<Invoke> invokes;
    @Inject
    private List<Verify> verifies;
    /*
     * list of expressions that eventually maps to a var
     */
    @Inject
    private List<ExpVar> expVars;

    private Call call; // test method call

    // var returned by the method, used to generate assert statement
    private Optional<IVar> expectedVar = Optional.empty();

    public List<IVar> getVars() {
        return vars;
    }

    public List<When> getWhens() {
        return whens;
    }

    public List<Verify> getVerifies() {
        return verifies;
    }

    public List<Invoke> getInvokes() {
        return invokes;
    }

    public Call getCall() {
        return call;
    }

    public void setCall(final Call call) {
        this.call = call;
    }

    public Optional<IVar> getExpectedVar() {
        return expectedVar;
    }

    public void setExpectedVar(final Optional<IVar> expectedVar) {
        this.expectedVar = expectedVar;
    }

    public List<IVar> getVars(final Predicate<IVar> filter) {
        return vars.stream().filter(filter).collect(Collectors.toList());
    }

    public Optional<IVar> getReturnVar() {
        return vars.stream().filter(IVar::isReturnVar).findFirst();
    }

    public Optional<When> findWhen(final String callSignature) {
        return whens.stream()
                .filter(w -> w.getMethodSignature().equals(callSignature))
                .findAny();
    }

    public Optional<Invoke> findInvoke(final Expression exp) {
        return invokes.stream().filter(n -> n.getMi().equals(exp)).findFirst();
    }

    public Optional<Invoke> findInvoke(final IVar var) {
        return invokes.stream().filter(n -> {
            return n.getCallVar().getName().equals(var.getName());
        }).findFirst();
    }

    public Optional<Invoke> findInvokeByInferVar(final IVar var) {
        return invokes.stream().filter(n -> {
            Optional<IVar> v = n.getReturnVar();
            if (v.isPresent()) {
                return v.get().getName().equals(var.getName());
            } else {
                return false;
            }
        }).findFirst();
    }

    public List<ExpVar> getVarExps() {
        return expVars;
    }

    public Optional<ExpVar> findByRightExp(final Expression exp) {
        return expVars.stream().filter(n -> n.getRightExp().equals(exp))
                .findFirst();
    }

    public Optional<ExpVar> findByLeftVar(final IVar var) {
        return expVars.stream().filter(n -> {
            Optional<IVar> leftVar = n.getLeftVar();
            if (leftVar.isPresent()) {
                return leftVar.get().getName().equals(var.getName());
            } else {
                return false;
            }
        }).findFirst();
    }

    /**
     * Search and return named var from list. Search precedence return var,
     * local vars, parameters and fields.
     * <p>
     * Note: In Java, we can't name local variable same as parameter. However,
     * local variable name can be same as an instance variable i.e. field, then
     * local shadows the field in that method.
     * <p>
     * @param varName
     * @param vars
     * @return
     */
    public IVar findVar(final String varName) {
        List<IVar> matchedNameVars =
                vars.stream().filter(v -> v.getName().equals(varName))
                        .collect(Collectors.toList());

        long rvCount =
                matchedNameVars.stream().filter(IVar::isReturnVar).count();
        long lvCount =
                matchedNameVars.stream().filter(IVar::isLocalVar).count();
        long ivCount =
                matchedNameVars.stream().filter(IVar::isInferVar).count();
        long pCount =
                matchedNameVars.stream().filter(IVar::isParameter).count();
        long fCount = matchedNameVars.stream().filter(IVar::isField).count();

        if (rvCount > 1 || lvCount > 1 || ivCount > 1 || pCount > 1
                || fCount > 1) {
            String message =
                    spaceit("multiple var declaration found for", varName);
            throw new IllegalStateException(message);
        }
        // search precedence return, local, parameter and field
        if (rvCount == 1) {
            return matchedNameVars.stream().filter(IVar::isReturnVar).findAny()
                    .get();
        }

        if (lvCount == 1) {
            return matchedNameVars.stream().filter(IVar::isLocalVar).findAny()
                    .get();
        }

        if (ivCount == 1) {
            return matchedNameVars.stream().filter(IVar::isInferVar).findAny()
                    .get();
        }

        if (pCount == 1) {
            return matchedNameVars.stream().filter(IVar::isParameter).findAny()
                    .get();
        }

        if (fCount == 1) {
            return matchedNameVars.stream().filter(IVar::isField).findAny()
                    .get();
        }

        String message = spaceit("var declaration not found for", varName);
        throw new IllegalStateException(message);
    }
}
