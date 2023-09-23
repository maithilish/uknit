package org.codetab.uknit.core.make.method.body;

import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Pack.Nature;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.Type;

public class AssertStmt {

    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private Asserts asserts;
    @Inject
    private Packs packs;
    @Inject
    private Vars vars;
    @Inject
    private Nodes nodes;
    @Inject
    private AssertExcludes excludes;

    public Optional<Statement> createStmt(final Heap heap) {
        Optional<Statement> stmt = Optional.empty();
        Optional<Pack> returnPackO =
                packs.findByVarName("return", heap.getPacks());
        Optional<IVar> expectedVarO = vars.getExpectedVar(returnPackO, heap);

        if (excludes.exclude(returnPackO, expectedVarO)) {
            return Optional.empty();
        }

        if (expectedVarO.isPresent()) {
            IVar expectedVar = expectedVarO.get();
            Type retType = heap.getCall().getReturnType();

            boolean created = expectedVar.isEffectivelyReal();
            String key = asserts.getAssertKey(retType, expectedVar.isMock(),
                    created);

            /*
             * If expected var is from a static call or if it is not visible
             * then use assertEquals. Ref itest: invoke.CallStatic.java
             */
            Optional<Pack> expectedPackO =
                    packs.findByVarName(expectedVar.getName(), heap.getPacks());
            if (expectedPackO.isPresent()) {
                Pack expectedPack = expectedPackO.get();
                if (nodes.is(expectedPack.getExp(), MethodInvocation.class)
                        && expectedPack.is(Nature.STATIC_CALL)) {
                    key = asserts.getAssertKey(retType, expectedVar.isMock(),
                            true);
                }
                if (expectedPack.getVar().is(
                        org.codetab.uknit.core.make.model.IVar.Nature.OFFLIMIT)) {
                    key = asserts.getAssertKey(retType, expectedVar.isMock(),
                            true);
                }
            }

            String fmt = asserts.getAssertFormat(key, expectedVar.getName());
            stmt = Optional.of(nodeFactory.createAssertStatement(fmt));
        }
        /*
         * If return exp is ThisExpression then expectedVarO is not present and
         * expected in assertSame() is set to the CUT field of the test class.
         * Ex: If CUT is Foo then the field Foo foo (annotated
         * with @InjectMocks) in FooTest test class is the this var.
         */
        if (returnPackO.isPresent()
                && nodes.is(returnPackO.get().getExp(), ThisExpression.class)) {
            String fmt = asserts.getAssertFormat("same", heap.getCutName());
            stmt = Optional.of(nodeFactory.createAssertStatement(fmt));
        }

        stmt.ifPresent(s -> heap.setAsserted(true));
        return stmt;
    }

    /**
     * Create fail assertion when there is no assert or verify statement.
     *
     * @param heap
     * @return
     */
    public Optional<Statement> createFailStmt(final Heap heap) {
        Optional<Statement> stmt = Optional.empty();
        if (stmt.isEmpty()) {
            String key = "fail";
            String fmt = asserts.getAssertFormat(key, "");
            stmt = Optional.of(nodeFactory.createAssertStatement(fmt));
        }
        return stmt;
    }

    @SuppressWarnings("unchecked")
    public void addStmt(final MethodDeclaration methodDecl,
            final Optional<Statement> stmt) {
        if (stmt.isPresent()) {
            methodDecl.getBody().statements().add(stmt.get());
        }
    }
}

class AssertExcludes {

    @Inject
    private Types types;

    public boolean exclude(final Optional<Pack> returnPackO,
            final Optional<IVar> expectedVarO) {

        // exclude if returns anonymous
        if (returnPackO.isPresent()) {
            IVar var = returnPackO.get().getVar();
            if (nonNull(var) && types.isBoolean(var.getType())) {
                return false;
            }
            if (returnPackO.get().is(Nature.ANONYMOUS)) {
                return true;
            }
        }

        // exclude if expected var is disabled and not field
        if (expectedVarO.isPresent() && !expectedVarO.get().isEnable()) {
            if (!expectedVarO.get().isField()) {
                return true;
            }
        }

        return false;
    }
}
