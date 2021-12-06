package org.codetab.uknit.core.parse;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.exception.CriticalException;
import org.codetab.uknit.core.make.Controller;
import org.codetab.uknit.core.node.CuFactory;
import org.codetab.uknit.core.util.IOUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class SourceParser {

    @Inject
    private Configs configs;
    @Inject
    private CuFactory cuFactory;
    @Inject
    private SourceVisitor sourceVisitor;
    @Inject
    private IOUtils ioUtils;
    @Inject
    private Controller ctl;

    public boolean parseAndProcess() {
        try {
            String srcFileName = configs.getConfig("uknit.file");
            String workspace = configs.getConfig("uknit.workspace");
            String srcDir = configs.getConfig("uknit.dir");
            String srcFile = String.join("/", workspace, srcDir, srcFileName);

            String unitName = String.join("/", srcDir, srcFileName);

            char[] src;
            try {
                src = ioUtils.toCharArray(srcFile, Charset.defaultCharset());
            } catch (IOException e) {
                throw new CriticalException(e);
            }
            CompilationUnit srcCu =
                    cuFactory.createCompilationUnit(src, unitName);
            ctl.setSrcCompilationUnit(srcCu);
            sourceVisitor.preProcess();
            srcCu.accept(sourceVisitor);
            sourceVisitor.postProcess();
        } catch (Exception e) {
            sourceVisitor.closeDumpers();
            throw e;
        }
        return true;
    }
}
