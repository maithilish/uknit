package org.codetab.uknit.core.parse;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.isNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.exception.CriticalException;
import org.codetab.uknit.core.make.Controller;
import org.codetab.uknit.core.node.CuFactory;
import org.codetab.uknit.core.util.IOUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class SourceParser {

    private static final Logger LOG = LogManager.getLogger();

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
            String srcBase = configs.getConfig("uknit.source.base");
            String srcDir = configs.getConfig("uknit.source.dir");
            String srcPkg = configs.getConfig("uknit.source.package");
            String srcClz = configs.getConfig("uknit.source.clz");
            String srcFile = String.join(".", srcClz, "java");

            String srcPath = String.join("/", srcBase, srcDir,
                    srcPkg.replace(".", "/"), srcFile);

            String unitName =
                    String.join("/", srcDir, srcPkg.replace(".", "/"), srcClz);

            char[] src;
            try {
                src = ioUtils.toCharArray(srcPath, Charset.defaultCharset());
            } catch (IOException e) {
                throw new CriticalException(e);
            }

            CompilationUnit srcCu =
                    cuFactory.createCompilationUnit(src, unitName);
            ctl.setSrcCompilationUnit(srcCu);
            String key = String.join(".", srcPkg, srcClz);
            ctl.getCuCache().put(key, srcCu);

            sourceVisitor.preProcess();
            srcCu.accept(sourceVisitor);
            sourceVisitor.postProcess();

        } catch (Exception e) {
            sourceVisitor.closeDumpers();
            throw e;
        }
        return true;
    }

    /**
     * Fetch cu from cache, if not found then parse new cu and cache it. Source
     * file may not be available for super classes of external packages, then
     * optional empty is returned. Used to parse super class.
     * @param clzPkg
     * @param clzName
     * @return cu optional
     */
    public Optional<CompilationUnit> fetchCu(final String clzPkg,
            final String clzName) {

        checkNotNull(clzPkg);
        checkNotNull(clzName);

        String key = String.join(".", clzPkg, clzName);
        CompilationUnit srcCu = ctl.getCuCache().get(key);
        if (isNull(srcCu)) {
            try {
                String srcBase = configs.getConfig("uknit.source.base");
                String srcDir = configs.getConfig("uknit.source.dir");
                String srcPkg = clzPkg;
                String srcName = clzName + ".java";
                String srcFile = String.join("/", srcBase, srcDir,
                        srcPkg.replace(".", "/"), srcName);

                String unitName = String.join("/", srcDir,
                        srcPkg.replace(".", "/"), clzName);

                char[] src;
                try {
                    src = ioUtils.toCharArray(srcFile,
                            Charset.defaultCharset());
                    srcCu = cuFactory.createCompilationUnit(src, unitName);
                    ctl.getCuCache().put(key, srcCu);
                    return Optional.of(srcCu);
                } catch (IOException e) {
                    LOG.debug("unable to find src file for {} {} {}", clzPkg,
                            clzName, e);
                }
            } catch (Exception e) {
                LOG.debug("unable to parse src for {} {} {}", clzPkg, clzName,
                        e);
            }
        }
        return Optional.ofNullable(srcCu);
    }
}
