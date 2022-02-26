package org.codetab.uknit.core.make.model;

import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.ASTNode;
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
    @Inject
    private List<Patch> patchs;

    private Call call; // test method call

    // var returned by the method, used to generate assert statement
    private Optional<IVar> expectedVar = Optional.empty();

    private String selfFieldName; // class under test

    private Heap parent;

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

    public List<Patch> getPatches() {
        return patchs;
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

    /**
     * Name of class under test or system under test (SUT)
     * @return name
     */
    public String getSelfFieldName() {
        return selfFieldName;
    }

    public void setSelfFieldName(final String selfFieldName) {
        this.selfFieldName = selfFieldName;
    }

    public List<IVar> getVars(final Predicate<IVar> filter) {
        return vars.stream().filter(filter).collect(Collectors.toList());
    }

    public Optional<IVar> getReturnVar() {
        return vars.stream().filter(IVar::isReturnVar).findFirst();
    }

    public Heap getParent() {
        return parent;
    }

    public void setParent(final Heap parent) {
        this.parent = parent;
    }

    // finders
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

    public Optional<ExpReturnType> findExpReturnType(final Expression exp) {
        Optional<Invoke> invoke =
                invokes.stream().filter(n -> n.getMi().equals(exp)).findFirst();
        if (invoke.isPresent()) {
            return invoke.get().getExpReturnType();
        } else {
            return Optional.empty();
        }
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

    public Optional<ExpVar> findByLeftVar(final String name) {
        return expVars.stream().filter(n -> {
            Optional<IVar> leftVar = n.getLeftVar();
            if (leftVar.isPresent()) {
                return leftVar.get().getName().equals(name);
            } else {
                return false;
            }
        }).findFirst();
    }

    public Optional<IVar> findLeftVarByRightExp(final Expression exp) {
        Optional<IVar> leftVar = Optional.empty();
        Optional<ExpVar> expVar = expVars.stream()
                .filter(n -> n.getRightExp().equals(exp)).findFirst();
        if (expVar.isPresent()) {
            leftVar = expVar.get().getLeftVar();
        }
        return leftVar;
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

        // search precedence return, local, parameter and field
        Optional<IVar> var =
                matchedNameVars.stream().filter(IVar::isReturnVar).findAny();
        if (var.isPresent()) {
            return var.get();
        }
        var = matchedNameVars.stream().filter(IVar::isLocalVar).findAny();
        if (var.isPresent()) {
            return var.get();
        }
        var = matchedNameVars.stream().filter(IVar::isInferVar).findAny();
        if (var.isPresent()) {
            return var.get();
        }
        var = matchedNameVars.stream().filter(IVar::isParameter).findAny();
        if (var.isPresent()) {
            return var.get();
        }
        var = matchedNameVars.stream().filter(IVar::isField).findAny();
        if (var.isPresent()) {
            return var.get();
        } else {
            String message = spaceit("var declaration not found for", varName);
            throw new IllegalStateException(message);
        }
    }

    public Optional<IVar> findLocalVar(final String varName) {
        return vars.stream()
                .filter(v -> v.getName().equals(varName) && v.isLocalVar())
                .findAny();
    }

    public Optional<IVar> findField(final String varName) {
        return vars.stream()
                .filter(v -> v.getName().equals(varName) && v.isField())
                .findAny();
    }

    /**
     * Initialize this heap from other.
     * @param other
     */
    public void initialize(final Heap other) {
        vars.addAll(other.getVars());
        selfFieldName = other.getSelfFieldName();
    }

    /**
     * Merge other heap contents to this.
     * @param other
     */
    public void merge(final Heap other) {
        // add vars that are not in list
        List<IVar> oVars = other.getVars();
        for (IVar oVar : oVars) {
            if (!vars.contains(oVar)) {
                vars.add(oVar);
            }
        }

        whens.addAll(other.getWhens());
        verifies.addAll(other.getVerifies());
        expectedVar = other.getExpectedVar();
    }

    /**
     * Get list of patches for a node.
     * @param node
     * @return
     */
    public List<Patch> findPatches(final ASTNode node) {
        return patchs.stream().filter(r -> r.getNode().equals(node))
                .collect(Collectors.toList());
    }

    /**
     * Get patch for a node and expression.
     * @param node
     * @param exp
     * @return
     */
    public Optional<Patch> findPatch(final ASTNode node, final Expression exp) {
        return patchs.stream()
                .filter(r -> r.getNode().equals(node) && r.getExp().equals(exp))
                .findAny();
    }
}
