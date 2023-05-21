package org.codetab.uknit.core.make.method.var.infer;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CodeException;
import org.codetab.uknit.core.exception.TypeException;
import org.codetab.uknit.core.make.exp.Arrays;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Kind;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Pack.Nature;
import org.codetab.uknit.core.make.model.ReturnType;
import org.codetab.uknit.core.node.Expressions;
import org.codetab.uknit.core.node.Methods;
import org.codetab.uknit.core.node.NodeFactory;
import org.codetab.uknit.core.node.NodeGroups;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayAccess;
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
    @Inject
    private Methods methods;
    @Inject
    private Wrappers wrappers;
    @Inject
    private Arrays arrays;

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
            if (var.isReturnVar() || var.isLocalVar() || var.isField()
                    || var.isParameter()) {
                return;
            } else if (var.isParameter() && pack.isIm()) {
                return;
            } else {
                throw new IllegalStateException(nodes.exMessage(
                        "unable to create infer var, var exists for",
                        pack.getExp()));
            }
        }

        // REVIEW - remove later
        // Expression ex = pack.getExp();
        // if (nodes.is(ex, MethodInvocation.class)) {
        // Expression value = expManager
        // .getValue((Expression) ((MethodInvocation) ex).arguments()
        // .get(0), heap);
        // }

        if (expressions.isInvokable(pack.getExp())) {
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
        } else if (nodes.is(pack.getExp(), ArrayAccess.class)) {
            /*
             * ArrayAccess is not invokable but returns value, hence create
             * infer var. Ex: foo.append(array[0]), Pack [var=-, exp=array[0])
             * then create infer var for the pack.
             */
            ArrayAccess exp = (ArrayAccess) pack.getExp();
            ASTNode parent = wrappers.stripAndGetParent(exp);

            boolean isParameter = false;
            try {
                Optional<Pack> arrayPackO = packs.findByVarName(
                        nodes.getName(exp.getArray()), heap.getPacks());
                if (arrayPackO.isPresent()
                        && arrayPackO.get().getVar().is(Kind.PARAMETER)) {
                    isParameter = true;
                }
            } catch (CodeException e) {
            }

            boolean createInfer = true;
            if (isParameter) {
                boolean isInternal =
                        methods.isInternalCall((Expression) parent, heap);
                if (isInternal) {
                    /*
                     * if array access is an arg of IM call and array is param,
                     * then don't create infer as ArgParms links the arg to
                     * param.
                     */
                    createInfer = false;
                } else if (!pack.isIm()) {
                    /*
                     * Pack is in calling method, not an IM call and array
                     * access refers to a param then don't create infer.
                     */
                    createInfer = false;
                }
            }

            if (createInfer) {

                /*
                 * try get type of the value returned of array access else get
                 * type of array access. Ex: Object a[] = {"foo"}; a[0]; the
                 * type of a[0] is object and type of value return by it String.
                 */
                Type type = null;
                ITypeBinding typeBinding =
                        arrays.getTypeBinding(exp, pack, heap);
                Optional<Type> typeO = arrays.getType(exp, pack, heap);
                if (typeO.isPresent() && nonNull(typeBinding)) {
                    // get type from value
                    type = typeO.get();
                } else {
                    // get type from ArrayAccess
                    typeBinding = exp.resolveTypeBinding();
                    typeO = types.getType(exp);
                    if (typeO.isPresent() && nonNull(typeBinding)) {
                        type = typeO.get();
                    }
                }

                if (isNull(type)) {
                    throw new TypeException(
                            "unable to get exp return type for: "
                                    + pack.getExp());
                } else if (types.capableToReturnValue(type)) {
                    IVar inferVar =
                            inferFactory.createInfer(type, typeBinding, heap);
                    pack.setVar(inferVar);
                }
            }
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
         * Don't create infer for SimpleName, SMI, ThisExp, LambdaExp and void.
         * The SMI is replaced by IMC packs, so infer is not created. If exp is
         * null then return is void.
         */
        if (isNull(exp) || nodes.is(exp, nodeGroups.uninferableNodes())) {
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
                    inferPack = modelFactory.createPack(packs.getId(),
                            returnPack.getVar(), varName, inCtlPath,
                            heap.isIm());
                    // var: apple exp: foo.bar(), morphed as inferPack
                    returnPack.setVar(inferVar);
                } else {
                    /*
                     * normal returnPack, create new pack for infer var and set
                     * returnPack exp to inferVar name.
                     */
                    // var: apple, exp: new Foo()
                    inferPack = modelFactory.createPack(packs.getId(), inferVar,
                            exp, inCtlPath, heap.isIm());
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
