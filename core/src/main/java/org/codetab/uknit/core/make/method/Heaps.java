package org.codetab.uknit.core.make.method;

import static java.util.Objects.isNull;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.make.model.IVar.Nature;
import org.codetab.uknit.core.make.model.Invoke;
import org.codetab.uknit.core.make.model.Pack;
import org.codetab.uknit.core.make.model.Patch;
import org.eclipse.jdt.core.dom.Expression;

public class Heaps {

    private static final Logger LOG = LogManager.getLogger();

    public void debugPacks(final String header, final Heap heap) {

        if (!LOG.isDebugEnabled()) {
            return;
        }

        LOG.debug("{}", header);

        List<Pack> packs = heap.getPacks();
        for (Pack pack : packs) {
            StringBuilder s = new StringBuilder();
            String packKind = "Pack  ";
            if (pack instanceof Invoke) {
                packKind = "Invoke";
            }
            if (isNull(pack.getVar())) {
                s.append("Pack   Var [--");
            } else {
                IVar v = pack.getVar();
                s.append(String.format("%s Var [name=%s", packKind,
                        v.getName()));
                if (!v.getOldName().equals(v.getName())) {
                    s.append(String.format(", [%s]", v.getOldName()));
                }
                s.append(String.format(", type=%s, %s", v.getType(),
                        v.getKind().name().charAt(0)));
                if (v.isMock()) {
                    s.append(", Mock");
                } else {
                    s.append(", Real");
                }
                if (v.isCreated()) {
                    s.append(", Create");
                }
                if (!v.isEnable()) {
                    s.append(", Disable");
                }
                for (Nature n : v.getNatures()) {
                    s.append(", ");
                    s.append(n.name());
                }
            }

            s.append("] ");

            if (isNull(pack.getExp())) {
                s.append("Exp [exp=--");
            } else {
                s.append("Exp [exp=");
                s.append(pack.getExp().toString());
            }

            if (pack.getLeftExp().isPresent()) {
                s.append(", leftExp=");
                s.append(pack.getLeftExp().get().toString());
            }

            for (org.codetab.uknit.core.make.model.Pack.Nature n : pack
                    .getNatures()) {
                s.append(", ");
                s.append(n.name());
            }

            s.append("]");

            LOG.debug("{}", s.toString());

            if (pack.getPatches().size() > 0) {
                for (Patch patch : pack.getPatches()) {
                    LOG.debug("           Patch [kind={}, rename {} -> {}]",
                            patch.getKind(), patch.getDefinedName(),
                            patch.getVar().getName());
                }
            }
        }
        LOG.debug("");
    }

    public void debugPatches(final String header, final Heap heap) {
        Map<Expression, IVar> patches = heap.getPatcher().getPatches();
        LOG.debug("{}", header);
        for (Expression exp : patches.keySet()) {
            IVar var = patches.get(exp);
            LOG.debug("Patch [exp={}, var={}]", exp, var.getName());
        }
        LOG.debug("");
    }
}
