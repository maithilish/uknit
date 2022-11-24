package org.codetab.uknit.core.make.method.patch;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Patch;
import org.codetab.uknit.core.make.model.ReturnType;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class Patchers {

    @Inject
    private ListPatcher listPatcher;
    @Inject
    private Nodes nodes;
    @Inject
    private Methods methods;

    /**
     * Returns list of expressions in an expression.
     *
     * The three methods - patchExpWithVar(), getExpIndex(), getExps() - works
     * in tandem. While adding any new node type add in all three.
     *
     * @param node
     * @return
     */
    public List<Expression> getExps(final ASTNode node) {

        checkNotNull(node);

        List<Expression> exps = new ArrayList<>();

        if (nodes.isName(node)) {
            return exps;
        }

        if (nodes.is(node, ReturnStatement.class)) {
            ReturnStatement rs = nodes.as(node, ReturnStatement.class);
            if (nonNull(rs.getExpression())) {
                exps.add(rs.getExpression());
            }
        } else if (nodes.is(node, MethodInvocation.class)) {
            MethodInvocation mi = nodes.as(node, MethodInvocation.class);
            exps.add(mi.getExpression());
            @SuppressWarnings("unchecked")
            List<Expression> args = mi.arguments();
            exps.addAll(args);
        } else if (nodes.is(node, ClassInstanceCreation.class)) {
            @SuppressWarnings("unchecked")
            List<Expression> args =
                    nodes.as(node, ClassInstanceCreation.class).arguments();
            exps.addAll(args);
        } else if (nodes.is(node, ArrayCreation.class)) {
            /*
             * The exp list is combined list of dimensions and initializer exps.
             */
            ArrayCreation ac = nodes.as(node, ArrayCreation.class);
            @SuppressWarnings("unchecked")
            List<Expression> args = ac.dimensions();
            exps.addAll(args);
            // combine initializer exps
            ArrayInitializer initializer = ac.getInitializer();
            if (nonNull(initializer)) {
                @SuppressWarnings("unchecked")
                List<Expression> initArgs = initializer.expressions();
                exps.addAll(initArgs);
            }
        } else if (nodes.is(node, ArrayInitializer.class)) {
            @SuppressWarnings("unchecked")
            List<Expression> args =
                    nodes.as(node, ArrayInitializer.class).expressions();
            exps.addAll(args);
        } else if (nodes.is(node, ArrayAccess.class)) {
            ArrayAccess aa = nodes.as(node, ArrayAccess.class);
            exps.add(aa.getArray());
            exps.add(aa.getIndex());
        } else if (nodes.is(node, Assignment.class)) {
            Assignment assignment = nodes.as(node, Assignment.class);
            exps.add(assignment.getLeftHandSide());
            exps.add(assignment.getRightHandSide());
        } else if (nodes.is(node, CastExpression.class)) {
            CastExpression ce = nodes.as(node, CastExpression.class);
            exps.add(ce.getExpression());
        } else if (nodes.is(node, ParenthesizedExpression.class)) {
            ParenthesizedExpression pe =
                    nodes.as(node, ParenthesizedExpression.class);
            exps.add(pe.getExpression());
        } else if (nodes.is(node, InfixExpression.class)) {
            InfixExpression infix = nodes.as(node, InfixExpression.class);
            exps.add(infix.getLeftOperand());
            exps.add(infix.getRightOperand());
            @SuppressWarnings("unchecked")
            List<Expression> extOperands = infix.extendedOperands();
            exps.addAll(extOperands);
        } else if (nodes.is(node, ConditionalExpression.class)) {
            ConditionalExpression cond =
                    nodes.as(node, ConditionalExpression.class);
            exps.add(cond.getExpression());
            exps.add(cond.getThenExpression());
            exps.add(cond.getElseExpression());
        }
        return exps;
    }

    /**
     * Returns index of exp in node. For example, MI exp it is 0 and args starts
     * from 1 whereas for SMI there is no exp and args start from 0.
     *
     * The three methods - patchExpWithVar(), getExpIndex(), getExps() - works
     * in tandem. While adding any new node type add in all three.
     *
     * @param node
     * @param exp
     * @return
     */
    public int getExpIndex(final ASTNode node, final Expression exp) {
        checkNotNull(node);
        checkNotNull(exp);

        if (nodes.is(node, ReturnStatement.class,
                VariableDeclarationFragment.class,
                EnhancedForStatement.class)) {
            return 0;
        } else if (nodes.is(node, MethodInvocation.class)) {
            MethodInvocation mi = nodes.as(node, MethodInvocation.class);
            @SuppressWarnings("unchecked")
            List<Expression> args = mi.arguments();
            /*
             * If IMC or static import call is arg for invoke then exp is null
             */
            if (nonNull(mi.getExpression()) && mi.getExpression().equals(exp)) {
                return 0;
            } else if (args.contains(exp)) {
                // starts from 1
                return args.indexOf(exp) + 1;
            } else {
                return -1;
            }
        } else if (nodes.is(node, SuperMethodInvocation.class)) {
            SuperMethodInvocation smi =
                    nodes.as(node, SuperMethodInvocation.class);
            @SuppressWarnings("unchecked")
            List<Expression> args = smi.arguments();
            if (args.contains(exp)) {
                // zero indexed
                return args.indexOf(exp);
            } else {
                return -1;
            }
        } else if (nodes.is(node, ClassInstanceCreation.class)) {
            @SuppressWarnings("unchecked")
            List<Expression> args =
                    nodes.as(node, ClassInstanceCreation.class).arguments();
            if (args.contains(exp)) {
                // zero indexed
                return args.indexOf(exp);
            } else {
                return -1;
            }
        } else if (nodes.is(node, ArrayCreation.class)) {
            /*
             * The exp index is obtained from combined list of dimensions and
             * initializer exps.
             */
            ArrayCreation ac = nodes.as(node, ArrayCreation.class);
            @SuppressWarnings("unchecked")
            List<Expression> dims = ac.dimensions();
            List<Expression> args = new ArrayList<>(dims);
            // combine initializer expressions
            if (nonNull(ac.getInitializer())) {
                @SuppressWarnings("unchecked")
                List<Expression> initArgs = ac.getInitializer().expressions();
                args.addAll(initArgs);
            }
            if (args.contains(exp)) {
                // starts from 0
                return args.indexOf(exp);
            } else {
                return -1;
            }
        } else if (nodes.is(node, ArrayInitializer.class)) {
            @SuppressWarnings("unchecked")
            List<Expression> args =
                    nodes.as(node, ArrayInitializer.class).expressions();
            if (args.contains(exp)) {
                // zero indexed
                return args.indexOf(exp);
            } else {
                return -1;
            }
        } else if (nodes.is(node, ArrayAccess.class)) {
            ArrayAccess aa = nodes.as(node, ArrayAccess.class);
            if (aa.getArray().equals(exp)) {
                return 0;
            } else if (aa.getIndex().equals(exp)) {
                return 1;
            } else {
                return -1;
            }
        } else if (nodes.is(node, InfixExpression.class)) {
            InfixExpression infix = nodes.as(node, InfixExpression.class);
            if (infix.getLeftOperand().equals(exp)) {
                return 0;
            } else if (infix.getRightOperand().equals(exp)) {
                return 1;
            } else {
                final int operandOffset = 2;
                int index = infix.extendedOperands().indexOf(exp);
                return operandOffset + index;
            }
        } else if (nodes.is(node, Assignment.class)) {
            Assignment assign = nodes.as(node, Assignment.class);
            if (assign.getLeftHandSide().equals(exp)) {
                return 0;
            } else if (assign.getRightHandSide().equals(exp)) {
                return 1;
            } else {
                return -1;
            }
        } else if (nodes.is(node, CastExpression.class)) {
            CastExpression ce = nodes.as(node, CastExpression.class);
            if (ce.getExpression().equals(exp)) {
                return 0;
            } else {
                return -1;
            }
        } else if (nodes.is(node, ParenthesizedExpression.class)) {
            ParenthesizedExpression pe =
                    nodes.as(node, ParenthesizedExpression.class);
            if (pe.getExpression().equals(exp)) {
                return 0;
            } else {
                return -1;
            }
        } else if (nodes.is(node, ConditionalExpression.class)) {
            ConditionalExpression cond =
                    nodes.as(node, ConditionalExpression.class);
            if (cond.getExpression().equals(exp)) {
                return 0;
            } else if (cond.getThenExpression().equals(exp)) {
                return 1;
            } else if (cond.getElseExpression().equals(exp)) {
                return 2;
            } else {
                return -1;
            }
        }
        throw new CodeException(nodes.noImplmentationMessage(node));
    }

    /**
     * Patch expression with var - arg expressions in MethodInvocation,
     * ClassInstanceCreation and ArrayCreation and expression in
     * MethodInvocation and ReturnStatement.
     * <p>
     * Use expIndex in the same logic as returned by getExpIndex().
     * <p>
     * foo.x().bar(baz.x()) - apple.bar(orange)
     * <p>
     * return foo.bar() - return apple
     * <p>
     * new String[group.size()] - new String[apple]
     * <p>
     * new Date(person.getDob()) - new Date(apple)
     *
     * <p>
     *
     * The three methods - patchExpWithVar(), getExpIndex(), getExps() - works
     * in tandem. While adding any new node type add in all three.
     *
     * @param node
     * @param exp
     * @param name
     * @return
     */
    public boolean patchExpWithVar(final ASTNode node, final Patch patch) {
        checkNotNull(node);
        checkNotNull(patch);

        if (nodes.isName(node)) {
            return false;
        }

        String name = patch.getName();
        if (nodes.is(node, ReturnStatement.class)) {
            ReturnStatement rs = nodes.as(node, ReturnStatement.class);
            rs.setExpression(rs.getAST().newSimpleName(name));
            return true;
        } else if (nodes.is(node, MethodInvocation.class)) {
            MethodInvocation mi = nodes.as(node, MethodInvocation.class);
            if (patch.getExpIndex() == 0) {
                mi.setExpression(mi.getAST().newSimpleName(name));
            } else if (patch.getExpIndex() > 0) {
                @SuppressWarnings("unchecked")
                List<Expression> args = mi.arguments();
                listPatcher.patch(args, patch.getExpIndex() - 1, patch);
            }
            return true;
        } else if (nodes.is(node, ClassInstanceCreation.class)) {
            @SuppressWarnings("unchecked")
            List<Expression> args =
                    nodes.as(node, ClassInstanceCreation.class).arguments();
            listPatcher.patch(args, patch.getExpIndex(), patch);
            return true;
        } else if (nodes.is(node, ArrayCreation.class)) {
            /*
             * The ArrayCreation can have two args type lists - dimension list
             * and optional initializers list. If expIndex is less than dim list
             * size then patch dim list else the initializers list. Don't merge
             * or create new list then changes will not reflect in the node as
             * ListPatcher.patch() requires the list obtained from the node. Ex:
             * new String[] {foo.name(), bar.name()} the [] part is dimension
             * and {foo.name()..} part is the initializer list.
             */
            int expIndex = patch.getExpIndex();
            ArrayCreation ac = nodes.as(node, ArrayCreation.class);
            @SuppressWarnings("unchecked")
            List<Expression> dims = ac.dimensions();
            if (expIndex < dims.size()) {
                listPatcher.patch(dims, expIndex, patch);
            } else if (nonNull(ac.getInitializer())) {
                @SuppressWarnings("unchecked")
                List<Expression> initialierArgs =
                        ac.getInitializer().expressions();
                listPatcher.patch(initialierArgs, expIndex, patch);
            }
            return true;
        } else if (nodes.is(node, ArrayInitializer.class)) {
            @SuppressWarnings("unchecked")
            List<Expression> args =
                    nodes.as(node, ArrayInitializer.class).expressions();
            listPatcher.patch(args, patch.getExpIndex(), patch);
            return true;
        } else if (nodes.is(node, ArrayAccess.class)) {
            ArrayAccess aa = nodes.as(node, ArrayAccess.class);
            if (patch.getExpIndex() == 0) {
                aa.setArray(aa.getAST().newSimpleName(name));
            } else if (patch.getExpIndex() == 1) {
                aa.setIndex(aa.getAST().newSimpleName(name));
            }
            return true;
        } else if (nodes.is(node, InfixExpression.class)) {
            InfixExpression infix = nodes.as(node, InfixExpression.class);
            if (patch.getExpIndex() == 0) {
                infix.setLeftOperand(infix.getAST().newSimpleName(name));
            } else if (patch.getExpIndex() == 1) {
                infix.setRightOperand(infix.getAST().newSimpleName(name));
            } else if (patch.getExpIndex() > 1) {
                final int offset = 2;
                @SuppressWarnings("unchecked")
                List<Expression> extOperands = infix.extendedOperands();
                int index = patch.getExpIndex() - offset;
                extOperands.remove(index);
                extOperands.add(index, infix.getAST().newSimpleName(name));
            }
            return true;
        } else if (nodes.is(node, Assignment.class)) {
            Assignment assignment = nodes.as(node, Assignment.class);
            if (patch.getExpIndex() == 0) {
                assignment.setLeftHandSide(
                        assignment.getAST().newSimpleName(name));
            } else if (patch.getExpIndex() == 1) {
                assignment.setRightHandSide(
                        assignment.getAST().newSimpleName(name));
            }
            return true;
        } else if (nodes.is(node, CastExpression.class)) {
            CastExpression ce = nodes.as(node, CastExpression.class);
            if (patch.getExpIndex() == 0) {
                ce.setExpression(ce.getAST().newSimpleName(name));
            }
            return true;
        } else if (nodes.is(node, ParenthesizedExpression.class)) {
            ParenthesizedExpression pe =
                    nodes.as(node, ParenthesizedExpression.class);
            if (patch.getExpIndex() == 0) {
                pe.setExpression(pe.getAST().newSimpleName(name));
            }
            return true;
        } else if (nodes.is(node, ConditionalExpression.class)) {
            ConditionalExpression cond =
                    nodes.as(node, ConditionalExpression.class);
            if (patch.getExpIndex() == 0) {
                cond.setExpression(cond.getAST().newSimpleName(name));
            } else if (patch.getExpIndex() == 1) {
                cond.setThenExpression(cond.getAST().newSimpleName(name));
            } else if (patch.getExpIndex() == 2) {
                cond.setElseExpression(cond.getAST().newSimpleName(name));
            }
            return true;
        }

        throw new CodeException(nodes.noImplmentationMessage(node));
    }

    public boolean patchable(final Invoke invoke) {
        boolean patch = false;
        if (nodes.is(invoke.getExp(), MethodInvocation.class)) {
            patch = true;
            MethodInvocation mi =
                    nodes.as(invoke.getExp(), MethodInvocation.class);
            if (nodes.is(mi.getParent(), MethodInvocation.class)) {
                /*
                 * static calls: return Byte.valueOf("100"); replace
                 * date.compareTo(LocalDate.now()) == 1; don't replace
                 */
                if (methods.isStaticCall(mi)) {
                    patch = false;
                }

                /*
                 * return s2.append(file.getName().toLowerCase()); toLowerCase
                 * on string is real returning real, don't replace it.
                 */
                Optional<ReturnType> ro = invoke.getReturnType();
                if (ro.isPresent()) {
                    boolean returnReal = !ro.get().isMock();
                    Optional<IVar> varO = invoke.getCallVar();
                    if (varO.isPresent()) {
                        if (!varO.get().isMock() && returnReal) {
                            patch = false;
                        }
                    }
                }
            }
        }
        return patch;
    }
}

/**
 * Patches exp in the list to patch name.
 *
 * @author Maithilish
 *
 */
class ListPatcher {

    @Inject
    private NodeFactory nodeFactory;

    /**
     * From exp list replaces exp at expIndex with a newly created Name
     * expression from patch name.
     *
     * Ensure that the exp list is obtained from the node to which patch is
     * being applied. By replacing in the list the changes are reflected in the
     * node. Don't create a new list from the original list as any updates to
     * the list will not reflect in the node.
     *
     * Ex: Pack [exp: new String[] {foo.name()}], Patch [name: apple, exp:
     * foo.name(), expIndex: 0] and the list obtained from the node {foo.name}.
     * In the list item foo.name at index 0 is replaced with apple.
     *
     * @param exps
     * @param expIndex
     * @param patch
     */
    public void patch(final List<Expression> exps, final int expIndex,
            final Patch patch) {
        if (expIndex >= 0) {
            AST ast = exps.get(expIndex).getAST();
            exps.remove(expIndex);
            exps.add(expIndex, nodeFactory.createName(patch.getName(), ast));
        }
    }
}
