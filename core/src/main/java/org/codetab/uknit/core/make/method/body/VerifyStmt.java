package org.codetab.uknit.core.make.method.body;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.make.method.Packs;
import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.Verify;
import org.codetab.uknit.core.node.NodeFactory;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;

public class VerifyStmt {

    @Inject
    private Configs configs;
    @Inject
    private NodeFactory nodeFactory;
    @Inject
    private Packs packs;
    @Inject
    private Times times;

    public List<Statement> createStmts(final Heap heap) {
        List<Statement> stmts = new ArrayList<>();

        // uknit.format.verify = verify(%s).%s(%s);
        String verifyFormat = configs.getFormat("uknit.format.verify");
        // uknit.format.verifyNever = verify(%s,never()).%s(%s);
        String verifyNeverFormat =
                configs.getFormat("uknit.format.verifyNever");
        // uknit.format.verifyTimes = verify(%s,times(%d)).%s(%s);
        String verifyTimesFormat =
                configs.getFormat("uknit.format.verifyTimes");

        List<Verify> verifies = packs.filterInvokes(heap.getPacks()).stream()
                .filter(i -> i.getVerify().isPresent())
                .map(i -> i.getVerify().get()).collect(Collectors.toList());

        // deduplicate the similar verify
        times.updatesTimes(verifies);
        times.removeDuplicates(verifies);

        for (Verify verify : verifies) {
            String format = verifyNeverFormat;
            if (verify.isInCtlFlowPath()) {
                format = verifyFormat;
            }
            if (verify.isInCtlFlowPath() && verify.getTimes() > 1) {
                format = verifyTimesFormat;
            }
            Statement stmt = nodeFactory.createVerifyStatement(verify.getMi(),
                    verify.getTimes(), format);
            stmts.add(stmt);

            // as verify stmt is created, assertFail is not required
            heap.setAsserted(true);

        }
        return stmts;
    }

    @SuppressWarnings("unchecked")
    public void addStmts(final MethodDeclaration methodDecl,
            final List<Statement> stmts) {
        methodDecl.getBody().statements().addAll(stmts);
    }
}

class Times {

    /**
     * By default Verify.times is -1. For each verify set times to 1 if it still
     * -1 and increment it if similar verify is found in list which is still
     * unprocessed. Set the similar verify's times to zero.
     *
     * @param verifies
     */
    public void updatesTimes(final List<Verify> verifies) {

        for (Verify verify : verifies) {
            if (verify.getTimes() == -1) { // only the unprocessed
                verify.setTimes(1);
                List<Verify> unprocessed =
                        verifies.stream().filter(v -> v.getTimes() == -1)
                                .collect(Collectors.toList());
                for (Verify other : unprocessed) {
                    /*
                     * if both mi string are same and both are in inCtlPath then
                     * both the verifies are same, increment the verify and
                     * update other to 0 from -1 to mark it as processed.
                     */
                    if (verify.getMi().toString()
                            .equals(other.getMi().toString())
                            && verify.isInCtlFlowPath()
                            && other.isInCtlFlowPath()) {
                        verify.setTimes(verify.getTimes() + 1);
                        other.setTimes(0);
                    }
                }
            }
        }
    }

    /**
     * Remove the verifies whose times is 0 as it they are merged with similar
     * verifies.
     *
     * @param verifies
     */
    public void removeDuplicates(final List<Verify> verifies) {
        verifies.removeIf(v -> v.getTimes() == 0);
    }

}
