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

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.exception.CriticalException;
import org.codetab.uknit.core.make.Controller;
import org.codetab.uknit.core.node.CuFactory;
import org.codetab.uknit.core.util.IOUtils;
import org.codetab.uknit.core.zap.make.model.Cu;
import org.codetab.uknit.core.zap.make.model.ModelFactory;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class SourceParser {

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

            /*
             * to avoid scrolling in package explorer, we can place Alpha.java
             * in test dir itself.
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
                cu.get().getClzNames().add(srcClz);
                cu.get().setCu(srcCu);
            } else {
                Cu c = modelFactory.createCuMap(srcPkg, srcClz, srcPathStr);
                c.setCu(srcCu);
                cuCache.add(c);
            }
        } catch (

        Exception e) {
            sourceVisitor.closeDumpers();
            throw e;
        }
        return true;
    }

    public void parseSuperClasses() {
        Map<AbstractTypeDeclaration, List<Entry<String, String>>> map =
                superParser.getSuperClassNames();
        ctl.setSuperClassMap(map);
        for (AbstractTypeDeclaration typeDecl : map.keySet()) {
            superParser.findSources(map.get(typeDecl));
            superParser.parse(ctl.getCuCache());
        }
    }

    public boolean process() {
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
