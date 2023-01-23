package org.codetab.uknit.core.make.method.var.infer;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.exception.TypeException;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Pack.Nature;
import org.codetab.uknit.core.make.model.ReturnType;
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.NodeGroups;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Type;

public class InferCreator {

    @Inject
    private Nodes nodes;
    @Inject
    private Packs packs;
    @Inject
    private Types types;
    @Inject
    private Expressions expressions;
    @Inject
    private InferFactory inferFactory;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private NodeGroups nodeGroups;

    /**
     * Create and set infer var for all packs in the list. The packs in the list
     * should have MI exp.
     *
     * @param inferPacks
     * @param heap
     */
    public void createInfer(final Pack pack, final Heap heap) {
        if (nonNull(pack.getVar())) {
            /*
             * For var of Kind.RETURN createInferForReturn() will create infer,
             * for Kind.LOCAL the var is already created by var declaration
             * visits.
             *
             * FIXME Pack - kinds FIELD, PARAMETER and infer for LOCAL reassign
             */
            IVar var = pack.getVar();
            if (var.isReturnVar() || var.isLocalVar() || var.isField()) {
                return;
            } else {
                throw new IllegalStateException(nodes.exMessage(
                        "unable to create infer var, var exists for",
                        pack.getExp()));
            }
        }

        Optional<ReturnType> returnTypeO = ((Invoke) pack).getReturnType();
        if (returnTypeO.isPresent()) {

            Type type = returnTypeO.get().getType();
            ITypeBinding typeBinding = returnTypeO.get().getTypeBinding();

            if (types.capableToReturnValue(type)) {
                IVar inferVar =
                        inferFactory.createInfer(type, typeBinding, heap);
                pack.setVar(inferVar);
            }
        } else {
            throw new TypeException(
                    "unable to get exp return type for: " + pack.getExp());
        }
    }

    /**
     * Create and set infer var for MI in return pack. Replaces return pack's
     * exp with infer var's name expression.
     *
     * @param returnPack
     * @param heap
     */
    public void createInferForReturn(final Pack returnPack,
            final boolean inCtlPath, final Heap heap) {

        Expression exp = returnPack.getExp();
        /*
         * Don't create infer for SimpleName, SMI, ThisExp and LambdaExp. The
         * SMI is replaced by IMC packs, so infer is not created.
         */
        if (nodes.is(exp, nodeGroups.uninferableNodes())) {
            return;
        }

        Type type = returnPack.getVar().getType();
        IVar inferVar = null;

        /*
         * Inferable: MI, Instance creation new Foo(), Array creation, Literals
         * "foo", 5 etc.,
         */
        if (expressions.isInferable(exp)) {
            inferVar = inferFactory.createInfer(type, exp.resolveTypeBinding(),
                    heap);
        }

        if (nonNull(inferVar)) {
            Optional<Pack> packO =
                    packs.findLastByLeftExp(exp, heap.getPacks());
            Pack inferPack = null;
            // FIXME Pack - write example for this
            if (packO.isPresent()) {
                if (isNull(packO.get().getVar())) {
                    packO.get().setVar(inferVar);
                } else {
                    throw new IllegalStateException(
                            nodes.exMessage("var exists for", exp));
                }
            } else {
                Name varName = nodeFactory.createName(inferVar.getName());
                if (returnPack instanceof Invoke) {
                    /*
                     * Ex: return foo.bar(); then returnPack is instance of
                     * Invoke. Create a new return pack with returnPack var and
                     * exp as inferVar name. Set inferVar to returnPack so that
                     * all details of invoke are available to inferVar. This
                     * effectively flips returnPack as inferPack and new pack as
                     * returnPack.
                     */

                    // var: returnVar exp: apple, morphed as returnPack
                    inferPack = modelFactory.createPack(returnPack.getVar(),
                            varName, inCtlPath);
                    // var: apple exp: foo.bar(), morphed as inferPack
                    returnPack.setVar(inferVar);
                } else {
                    /*
                     * normal returnPack, create new pack for infer var and set
                     * returnPack exp to inferVar name.
                     */
                    // var: apple, exp: new Foo()
                    inferPack =
                            modelFactory.createPack(inferVar, exp, inCtlPath);
                    // var: return, exp: apple
                    returnPack.setExp(varName);
                }
                if (expressions.isAnonOrLambda(exp)) {
                    inferPack.addNature(Nature.ANONYMOUS);
                }
                heap.addPack(inferPack);
            }
        } else {
            throw new CodeException(nodes.noImplmentationMessage(exp));
        }
    }
}
