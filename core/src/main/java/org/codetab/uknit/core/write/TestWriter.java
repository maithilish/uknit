package org.codetab.uknit.core.write;

import static java.util.Objects.isNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.exception.CriticalException;
import org.codetab.uknit.core.make.Controller;
import org.codetab.uknit.core.node.Classes;
import org.codetab.uknit.core.util.IOUtils;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class TestWriter {

    @Inject
    private Configs configs;
    @Inject
    private IOUtils ioUtils;
    @Inject
    private Writer writer;
    @Inject
    private Classes classes;

    public void write(final Controller ctl) {

        CompilationUnit cu = ctl.getTestCompilationUnit();

        boolean insertLineBreaks =
                configs.getConfig("uknit.pretty.insertLineBreaks", true);
        String destDir = configs.getConfig("uknit.dest");

        if (isNull(destDir)) {
            String code = writer.format(cu);
            if (insertLineBreaks) {
                code = writer.insertLineBreaks(code);
            }
            writer.display(code);
        } else {
            String workspace = configs.getConfig("uknit.workspace");

            @SuppressWarnings("unchecked")
            List<AbstractTypeDeclaration> definedTypes =
                    new ArrayList<>(cu.types()); // safe copy to restore

            @SuppressWarnings("unchecked")
            List<AbstractTypeDeclaration> types = cu.types();

            // remove all class from cu, add one by one and write file for each
            types.clear();
            try {
                for (AbstractTypeDeclaration type : definedTypes) {
                    types.add(type);
                    String testClzName = classes.getClzName(type);
                    String testFileName = ioUtils.getClassFilePath(workspace,
                            destDir, testClzName);
                    try {
                        String code = writer.format(cu);
                        if (insertLineBreaks) {
                            code = writer.insertLineBreaks(code);
                        }
                        writer.write(testFileName, code);
                    } catch (IOException e) {
                        throw new CriticalException(e);
                    }
                    types.clear();
                }
            } finally {
                // reset types
                types.clear();
                types.addAll(definedTypes);
            }
        }
    }

}
