package org.codetab.uknit.core.make.method.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.method.body.Initializers;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Verify;
import org.codetab.uknit.core.make.model.When;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SimpleName;

public class VarEnablers {

    @Inject
    private Types types;
    @Inject
    private Nodes nodes;
    @Inject
    private Methods methods;
    @Inject
    private Initializers initializers;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private Packs packs;
    @Inject
    private Vars vars;

    public void collectVarNamesOfWhens(final Set<String> names,
            final Heap heap) {
        List<Invoke> invokes = packs.filterInvokes(heap.getPacks());
        for (Invoke invoke : invokes) {
            Optional<When> whenO = invoke.getWhen();
            if (whenO.isPresent()) {
                When when = whenO.get();
                for (IVar var : when.getReturnVars()) {
                    names.add(var.getName());
                }
                names.addAll(when.getNames());
            }
        }
    }

    public void collectVarNamesOfVerifies(final Set<String> names,
            final Heap heap) {
        List<Invoke> invokes = packs.filterInvokes(heap.getPacks());
        for (Invoke invoke : invokes) {
            Optional<Verify> verifyO = invoke.getVerify();
            if (verifyO.isPresent()) {
                Verify verify = verifyO.get();
                MethodInvocation mi = verify.getMi();
                for (String name : methods.getNames(mi)) {
                    names.add(name);
                }
            }
        }
    }

    public void collectVarNamesOfReturn(final Set<String> names,
            final Heap heap) {
        Optional<IVar> expectedVar =
                vars.getExpectedVar(packs.getReturnPack(heap.getPacks()), heap);
        expectedVar.ifPresent(v -> {
            if (!types.isBoolean(v.getType())) {
                names.add(v.getName());
            }
        });
    }

    public void enableVarsUsedInInitializers(final List<Expression> exps,
            final Heap heap) {
        for (Expression exp : exps) {
            List<String> names = new ArrayList<>();
            if (nodes.is(exp, CastExpression.class)) {
                Expression e =
                        nodes.as(exp, CastExpression.class).getExpression();
                if (nodes.is(e, SimpleName.class)) {
                    names.add(nodes.getName(e));
                }
            } else if (nodes.is(exp, PrefixExpression.class)) {
                Expression e =
                        nodes.as(exp, PrefixExpression.class).getOperand();
                if (nodes.is(e, SimpleName.class)) {
                    names.add(nodes.getName(e));
                }
            } else if (nodes.is(exp, PostfixExpression.class)) {
                Expression e =
                        nodes.as(exp, PostfixExpression.class).getOperand();
                if (nodes.is(e, SimpleName.class)) {
                    names.add(nodes.getName(e));
                }
            } else if (nodes.is(exp, InfixExpression.class)) {
                Expression e =
                        nodes.as(exp, InfixExpression.class).getLeftOperand();
                if (nodes.is(e, SimpleName.class)) {
                    names.add(nodes.getName(e));
                }
                e = nodes.as(exp, InfixExpression.class).getRightOperand();
                if (nodes.is(e, SimpleName.class)) {
                    names.add(nodes.getName(e));
                }
            } else if (nodes.is(exp, SimpleName.class)) {
                names.add(nodes.getName(exp));
            }
            for (String name : names) {
                vars.findVarByName(name, heap).setEnable(true);
            }
        }
    }

    public List<Expression> getInitializers(final Heap heap) {
        List<Expression> list = new ArrayList<>();
        List<IVar> usedVars = vars.filterVars(heap,
                v -> (v.isInferVar() || v.isLocalVar() || v.isReturnVar())
                        && v.isEnable());

        for (IVar usedVar : usedVars) {
            initializers.getInitializerExp(usedVar, heap)
                    .ifPresent(e -> list.add(e));
        }
        return list;
    }

    public void disableVars(final Set<String> names, final Heap heap) {

        List<IVar> localVars = vars.filterVars(heap,
                v -> (v.isInferVar() || v.isLocalVar() || v.isReturnVar()));

        localVars.forEach(v -> {
            if (!names.contains(v.getName())) {
                v.setEnable(false);
            }
        });
    }

    public void setEnableFromEnforce(final Heap heap) {

        List<IVar> enforcedVars =
                vars.filterVars(heap, v -> (v.getEnforce().isPresent()));

        enforcedVars.forEach(v -> {
            v.setEnable(v.getEnforce().get());
        });
    }

    /**
     * Create stand-in local var for disabled but used field.
     * @param field
     * @return
     */
    public IVar createStandinVar(final IVar field) {
        IVar var = modelFactory.createVar(Kind.LOCAL, field.getName(),
                field.getType(), field.isMock());
        var.setCreated(false);
        var.setDeepStub(false);
        var.setEnable(true);
        var.setEnforce(Optional.of(false));
        return var;
    }

}
