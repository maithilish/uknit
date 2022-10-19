package org.codetab.uknit.core.parse;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.make.Controller;
import org.codetab.uknit.core.make.model.Cu;
import org.codetab.uknit.core.make.model.ModelFactory;
import org.codetab.uknit.core.node.CuFactory;
import org.codetab.uknit.core.node.Resolver;
import org.codetab.uknit.core.util.IOUtils;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;

/**
 * Parse Super class.
 *
 * @author m
 *
 */
public class SuperParser {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private Configs configs;
    @Inject
    private CuFactory cuFactory;
    @Inject
    private SourceFinder sourceFinder;
    @Inject
    private ModelFactory modelFactory;
    @Inject
    private IOUtils ioUtils;
    @Inject
    private Controller ctl;
    @Inject
    private Resolver resolver;

    /**
     * Get list of super class names.
     * @return list of pkgName, className entries
     */
    public Map<AbstractTypeDeclaration, List<Entry<String, String>>> getSuperClassNames() {

        Map<AbstractTypeDeclaration, List<Entry<String, String>>> map =
                new HashMap<>();

        CompilationUnit srcCu = ctl.getSrcCompilationUnit();
        ITypeBinding objTypeBind =
                srcCu.getAST().resolveWellKnownType("java.lang.Object");

        // srcCu may have multiple classes
        @SuppressWarnings("unchecked")
        List<AbstractTypeDeclaration> typeDecls = srcCu.types();

        for (AbstractTypeDeclaration typeDecl : typeDecls) {
            List<Entry<String, String>> superClassNames = new ArrayList<>();
            ITypeBinding typeBind =
                    resolver.resolveBinding(typeDecl).getSuperclass();
            while (nonNull(typeBind) && !typeBind.equals(objTypeBind)) {
                // strip typeParms from super class name else super call fails
                String clzName = typeBind.getName().replaceAll("<.*>", "");
                String clzPkg = typeBind.getPackage().getName();
                Entry<String, String> entry =
                        new SimpleEntry<>(clzPkg, clzName);
                superClassNames.add(entry);
                LOG.info("super class pkg: {} clz: {}", entry.getKey(),
                        entry.getValue());
                typeBind = typeBind.getSuperclass();
            }
            map.put(typeDecl, superClassNames);
        }
        return map;
    }

    /*
     * Scan package folder java files and if it contains super class create or
     * update Cu.
     */
    public void findSources(final List<Entry<String, String>> classNames) {
        String srcBase = configs.getConfig("uknit.source.base");
        String srcDir = configs.getConfig("uknit.source.dir");
        List<Cu> cuCache = ctl.getCuCache();
        for (Entry<String, String> entry : classNames) {
            String clzPkg = entry.getKey();
            String clzName = entry.getValue();
            boolean known =
                    cuCache.stream().anyMatch(h -> h.isKnown(clzPkg, clzName));
            if (!known) {
                Path searchPath =
                        ioUtils.asSrcFolderPath(srcBase, srcDir, clzPkg);
                String searchDir = searchPath.toString();
                // source may not be available for external libraries
                if (ioUtils.dirExists(searchDir)) {

                    String srcPath = sourceFinder.find(searchDir, clzName);
                    LOG.info("super clz: {}, src path: {}", clzName, srcPath);

                    Optional<Cu> cu = cuCache.stream()
                            .filter(h -> h.getSourcePath().equals(srcPath))
                            .findFirst();
                    if (cu.isPresent()) {
                        cu.get().getClzNames().add(clzName);
                    } else {
                        cuCache.add(modelFactory.createCu(clzPkg, clzName,
                                srcPath));
                    }
                }
            }
        }
    }

    /**
     * Fetch super class cu from cache, if not found then parse new cu and cache
     * it. Source file may not be available for super classes in external
     * packages and they are ignored.
     * @param cuCache
     */
    public void parse(final List<Cu> cuCache) {
        for (Cu cu : cuCache) {
            if (isNull(cu.getCu())) {
                String srcDir = configs.getConfig("uknit.source.dir");
                String srcPkg = cu.getPkg();
                String srcFile = cu.getSourcePath();
                String unitName = String.join("/", srcDir,
                        srcPkg.replace(".", "/"), cu.getClzNames().get(0));
                try {
                    char[] src;
                    try {
                        src = ioUtils.toCharArray(srcFile,
                                Charset.defaultCharset());
                        CompilationUnit srcCu =
                                cuFactory.createCompilationUnit(src, unitName);
                        cu.setCu(srcCu);
                    } catch (IOException e) {
                        // may be external
                        LOG.debug("unable to find src file {} {}", srcFile, e);
                    }
                } catch (Exception e) {
                    LOG.debug("unable to parse src file {} {}", srcFile, e);
                }
            }
        }
    }
}
