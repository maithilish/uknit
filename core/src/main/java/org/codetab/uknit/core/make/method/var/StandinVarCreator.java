package org.codetab.uknit.core.make.method.var;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.VarNotFoundException;
import org.codetab.uknit.core.make.Clz;
import org.codetab.uknit.core.make.Controller;
import org.codetab.uknit.core.make.clz.FieldMakers;
import org.codetab.uknit.core.make.method.Packer;
import org.codetab.uknit.core.make.method.Vars;
import org.codetab.uknit.core.make.model.Field;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.Pack;

public class StandinVarCreator {

    @Inject
    private VarEnablers varEnablers;
    @Inject
    private Vars vars;
    @Inject
    private Packer packer;
    @Inject
    private Controller ctl;
    @Inject
    private FieldMakers fieldMakers;

    /**
     * Add stand-in local var for the fields that are used but not added to the
     * test class body.
     *
     * @param varNames
     * @param heap
     * @return
     */
    public List<Pack> addStandinVarsForUsedFields(final Set<String> varNames,
            final Heap heap) {

        Clz testClz =
                ctl.getClzMaker().getClzMap().getClz(heap.getTestClzName());

        List<Pack> standinPacks = new ArrayList<>();

        for (String name : varNames) {
            Field field;
            try {
                field = vars.findFieldByName(name, heap);
            } catch (VarNotFoundException e) {
                field = null;
            }
            /*
             * if field doesn't exist in the test body or local var is not
             * defined, create stand in var and pack.
             */
            if (nonNull(field)
                    && fieldMakers.fieldNotExists(name,
                            testClz.getTestTypeDecl())
                    && !vars.isLocalVarDefined(field, heap)) {
                IVar standinVar = varEnablers.createStandinVar(field);
                Pack standinPack =
                        packer.packStandinVar(standinVar, true, heap);
                standinPacks.add(standinPack);
            }
        }
        return standinPacks;
    }

}
