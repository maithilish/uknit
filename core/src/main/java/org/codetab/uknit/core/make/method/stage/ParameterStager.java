package org.codetab.uknit.core.make.method.stage;

import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.make.model.Heap;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.make.model.Parameter;
import org.codetab.uknit.core.node.Mocks;
import org.codetab.uknit.core.node.Nodes;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;

public class ParameterStager {

    @Inject
    private Mocks mocks;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private Nodes nodes;

    /**
     * Adds method parameters.
     * @param parameters
     * @param method
     */
    public void stageParameters(
            final List<SingleVariableDeclaration> parameters, final Heap heap) {
        for (SingleVariableDeclaration svd : parameters) {
            Type type = svd.getType();
            boolean mock = mocks.isMockable(type);
            String name = nodes.getVariableName(svd);
            Parameter parameter =
                    modelFactory.createParameter(name, type, mock);
            heap.getVars().add(parameter);
        }
    }

}
