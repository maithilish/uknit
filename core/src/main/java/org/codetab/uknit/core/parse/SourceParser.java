package org.codetab.uknit.core.parse;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.isNull;
import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.exception.CriticalException;
import org.codetab.uknit.core.make.Controller;
import org.codetab.uknit.core.make.model.Cu;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.node.CuFactory;
import org.codetab.uknit.core.util.IOUtils;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * Parse source and its super classes.
 *
 * @author Maithilish
 *
 */
public class SourceParser {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private Configs configs;
    @Inject
    private CuFactory cuFactory;
    @Inject
    private SourceVisitor sourceVisitor;
    @Inject
    private SuperParser superParser;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private IOUtils ioUtils;
    @Inject
    private Controller ctl;

    /**
     * Parse source, create and cache compilation unit.
     * @return
     */
    public boolean parseClass() {
        try {
            String srcBase = configs.getConfig("uknit.source.base");
            String srcDir = configs.getConfig("uknit.source.dir");
            String srcPkg = configs.getConfig("uknit.source.package");
            String srcClz = configs.getConfig("uknit.source.clz");
            String srcFile = String.join(".", srcClz, "java");

            Path srcPath =
                    ioUtils.asSrcFilePath(srcBase, srcDir, srcPkg, srcFile);
            String srcPathStr = srcPath.toString();

            String unitName =
                    String.join("/", srcDir, srcPkg.replace(".", "/"), srcClz);

            LOG.info("parse class under test");
            LOG.info("source.base {}", srcBase);
            LOG.info("source.dir {}", srcDir);
            LOG.info("source.package {}", srcPkg);
            LOG.info("source.clz {}", srcClz);
            LOG.info("srcPath {}", srcPathStr);
            LOG.info("unitName {}", unitName);

            /*
             * to avoid scrolling in package explorer, place Alpha.java in test
             * dir itself.
             */
            String validSrcPath = null;
            if (Files.exists(Paths.get(srcPathStr))) {
                validSrcPath = srcPathStr;
            } else {
                if (srcFile.equals("Alpha.java")) {
                    String altFilePath = srcPathStr.replace("src/main/java",
                            "src/test/java");
                    if (Files.exists(Paths.get(altFilePath))) {
                        validSrcPath = altFilePath;
                    }
                }
            }

            if (isNull(validSrcPath)) {
                throw new CriticalException(
                        spaceit("src file not found: ", srcPathStr));
            }

            char[] src;
            try {
                src = ioUtils.toCharArray(validSrcPath,
                        Charset.defaultCharset());
            } catch (IOException e) {
                try {
                    if (srcFile.equals("Alpha.java")) {
                        String altPath = srcPathStr.replace("src/main/java",
                                "src/test/java");
                        src = ioUtils.toCharArray(altPath,
                                Charset.defaultCharset());
                    } else {
                        throw new CriticalException(e);
                    }
                } catch (IOException e1) {
                    throw new CriticalException(e);
                }
            }

            CompilationUnit srcCu =
                    cuFactory.createCompilationUnit(src, unitName);
            ctl.setSrcCompilationUnit(srcCu);

            List<Cu> cuCache = ctl.getCuCache();
            Optional<Cu> cu = cuCache.stream()
                    .filter(h -> h.getSourcePath().equals(srcPathStr))
                    .findFirst();
            if (cu.isPresent()) {
                LOG.debug("cu present in cache, update");
                cu.get().getClzNames().add(srcClz);
                cu.get().setCu(srcCu);
            } else {
                LOG.debug("cu not present in cache, add");
                Cu c = modelFactory.createCu(srcPkg, srcClz, srcPathStr);
                c.setCu(srcCu);
                cuCache.add(c);
            }
        } catch (Exception e) {
            sourceVisitor.closeDumpers();
            throw e;
        }
        return true;
    }

    public void parseSuperClasses() {
        LOG.info("parse super classes");
        Map<AbstractTypeDeclaration, List<Entry<String, String>>> map =
                superParser.getSuperClassNames();
        ctl.setSuperClassMap(map);
        for (AbstractTypeDeclaration typeDecl : map.keySet()) {
            superParser.findSources(map.get(typeDecl));
            superParser.parse(ctl.getCuCache());
        }
    }

    public boolean process() {
        LOG.info("process source cu");
        try {
            sourceVisitor.preProcess();

            CompilationUnit srcCu = ctl.getSrcCompilationUnit();
            srcCu.accept(sourceVisitor);

            sourceVisitor.postProcess();
            return true;
        } catch (Exception e) {
            sourceVisitor.closeDumpers();
            throw e;
        }
    }

    /**
     * Fetch {@link CompilationUnit} from cache or if not found, then return
     * empty.
     * @param clzPkg
     * @param clzName
     * @return {@link CompilationUnit} - optional
     */
    public Optional<CompilationUnit> fetchCu(final String clzPkg,
            final String clzName) {

        checkNotNull(clzPkg);
        checkNotNull(clzName);

        Optional<Cu> cu =
                ctl.getCuCache().stream().filter(c -> c.getPkg().equals(clzPkg)
                        && c.getClzNames().contains(clzName)).findAny();
        if (cu.isPresent()) {
            return Optional.of(cu.get().getCu());
        } else {
            return Optional.empty();
        }
    }
}
