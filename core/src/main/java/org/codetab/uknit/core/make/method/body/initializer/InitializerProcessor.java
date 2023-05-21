package org.codetab.uknit.core.make.method.body.initializer;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.ResolveException;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.method.patch.Patcher;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Initializer;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.node.NodeGroups;
import org.codetab.uknit.core.node.Nodes;
import org.codetab.uknit.core.node.Types;
import org.codetab.uknit.core.node.Wrappers;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Type;

public class InitializerProcessor {

    @Inject
    private MockInitializer mockInitializer;
    @Inject
    private RealInitializer realInitializer;
    @Inject
    private StepinInitializer stepinInitializer;
    @Inject
    private SensibleInitializer sensibleInitializer;
    @Inject
    private Packs packs;
    @Inject
    private Nodes nodes;
    @Inject
    private Wrappers wrappers;
    @Inject
    private NodeGroups nodeGroups;
    @Inject
    private Patcher patcher;
    @Inject
    private Factory factory;
    @Inject
    private InitialzerFinder initialzerFinder;
    @Inject
    private TypeChange typeChange;

    /**
     * Assign initializer for vars that are true for isMock(). Even when a var
     * is mock MockInitialzer.getInitializer() returns empty optional for
     * excluded items such as effectively real etc.,
     *
     * @param heap
     */
    public void processMocks(final Heap heap) {

        List<Pack> mockPacks = packs.filterNoInitializers(heap.getPacks(),
                p -> p.getVar().isMock());

        for (Pack pack : mockPacks) {
            Optional<Pack> iniPackO =
                    initialzerFinder.findInitializerPack(pack, heap);
            IVar var = pack.getVar();
            Optional<Initializer> initializer =
                    mockInitializer.getInitializer(var, iniPackO, heap);
            if (initializer.isPresent()) {
                var.setInitializer(initializer);
            }
        }
    }

    public void processReals(final Heap heap) {

        List<Pack> realPacks = packs.filterNoInitializers(heap.getPacks(),
                p -> !p.getVar().isMock());

        for (Pack pack : realPacks) {
            Optional<Pack> iniPackO =
                    initialzerFinder.findInitializerPack(pack, heap);
            IVar var = pack.getVar();
            Optional<Initializer> initializer =
                    realInitializer.getInitializer(var, iniPackO, heap);
            if (initializer.isPresent()) {
                var.setInitializer(initializer);
            }
        }
    }

    public void processExps(final Heap heap) {

        List<Pack> packList = packs.filterNoInitializers(heap.getPacks(),
                p -> nonNull(p.getExp()));

        for (Pack pack : packList) {
            Optional<Initializer> initializer = Optional.empty();
            Expression patchedExp =
                    wrappers.unpack(patcher.copyAndPatch(pack, heap));
            if (nodes.isName(patchedExp)) {
                /*
                 * the iniPack for name is pack itself, process straightway
                 * without searching for linked iniPack.
                 */
                IInitializer srv = factory.createNameInitializer();
                initializer = srv.getInitializer(pack, pack, heap);
            } else if (nodes.is(patchedExp, nodeGroups.literalNodes())) {
                /*
                 * Ex: call(foo, (int) 10L); the iniPack for arg literal is pack
                 * itself, process straightway.
                 */
                IInitializer srv = factory.createExpInitializer();
                initializer = srv.getInitializer(pack, pack, heap);
            } else {
                /*
                 * search for linked iniPack and if present, process using
                 * appropriate IInitializer (either ExpInitializer or
                 * InvokeInitializer.
                 */
                Optional<Pack> iniPackO =
                        initialzerFinder.findInitializerPack(pack, heap);
                if (iniPackO.isPresent()) {
                    IInitializer srv = factory.createInitializer(
                            wrappers.unpack(iniPackO.get().getExp()));
                    initializer =
                            srv.getInitializer(pack, iniPackO.get(), heap);
                }
            }

            IVar var = pack.getVar();
            if (initializer.isPresent()) {
                var.setInitializer(initializer);

                // if var type is changed, set new type
                Object value = initializer.get().getInitializer();
                if (nonNull(value) && value instanceof Expression) {
                    Optional<Type> newType = typeChange
                            .getValueTypeIfChanged((Expression) value, var);
                    if (newType.isPresent()) {
                        var.setType(newType.get());
                    }
                }
            }
        }
    }

    public void processSensibles(final Heap heap) {

        List<Pack> packList =
                packs.filterNoInitializers(heap.getPacks(), p -> true);

        for (Pack pack : packList) {
            Optional<Initializer> initializer =
                    sensibleInitializer.getInitializer(pack, heap);
            if (initializer.isPresent()) {
                pack.getVar().setInitializer(initializer);
            }
        }
    }

    public void processStepins(final Heap heap) {

        List<Pack> packList =
                packs.filterNoInitializers(heap.getPacks(), p -> true);

        for (Pack pack : packList) {
            Optional<Initializer> initializer =
                    stepinInitializer.getInitializer(pack);
            if (initializer.isPresent()) {
                pack.getVar().setInitializer(initializer);
            }
        }
    }
}

/**
 * In rare cases var type may change, obtain new type.
 *
 * @author Maithilish
 *
 */
class TypeChange {

    @Inject
    private Types types;

    /**
     * Get new type if new type is not compatible with old type. Ex: old type
     * ArrayList and new value is HashMap, type is changed. Old type is List
     * (old value is ArrayList) and new value is LinkedList, type is not
     * changed.
     *
     * @param value
     * @param var
     * @return
     */
    public Optional<Type> getValueTypeIfChanged(final Expression value,
            final IVar var) {
        Optional<Type> newType = Optional.empty();
        try {
            /*
             * If var value type is changed then set new type. Ex: Object[] a =
             * {new ArrayList<String>()}; a[0] = new Object[]{new
             * HashMap<String, File>()}; The a[0] type is ArrayList<...> and on
             * reassign a2[0] is HashMap<...>.
             *
             * Ref itest: exp.value.ArrayAccess.accessClassInstanceCreation().
             */

            Optional<Type> valueType = types.getType(value);
            ITypeBinding valueBind = value.resolveTypeBinding();
            ITypeBinding varBind = var.getTypeBinding();
            boolean compatible = true;
            if (nonNull(valueBind) && nonNull(varBind)) {
                compatible = valueBind.isAssignmentCompatible(varBind);
            }

            if (!compatible) {
                newType = valueType;
            }

        } catch (ResolveException e) {
        }
        return newType;
    }
}
