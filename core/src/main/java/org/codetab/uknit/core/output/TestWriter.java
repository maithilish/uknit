package org.codetab.uknit.core.output;

import java.io.IOException;
import java.nio.file.Path;
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

        String outputMode = configs.getConfig("uknit.output.mode", "file");
        boolean outputFileOverwrite =
                configs.getConfig("uknit.output.file.overwrite", false);
        String outputDir =
                configs.getConfig("uknit.output.dir", "src/test/java");

        if (outputMode.equalsIgnoreCase("file")) {
            String sourceBase = configs.getConfig("uknit.source.base");
            String sourcePkg = configs.getConfig("uknit.source.package");

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
                    Path outputFilePath = ioUtils.asSrcFilePath(sourceBase,
                            outputDir, sourcePkg, testClzName + ".java");
                    try {
                        String code = writer.format(cu);
                        if (insertLineBreaks) {
                            code = writer.insertLineBreaks(code);
                        }
                        writer.write(outputFilePath, code, outputFileOverwrite);
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
        } else {
            String code = writer.format(cu);
            if (insertLineBreaks) {
                code = writer.insertLineBreaks(code);
            }
            writer.display(code);
        }
    }

}
