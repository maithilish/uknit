package org.codetab.uknit.core.make.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.CompilationUnit;

import com.google.inject.assistedinject.Assisted;

/**
 * Source Compilation Unit
 *
 * @author Maithilish
 *
 */
public class Cu {

    private String sourcePath;
    private String pkg;
    private CompilationUnit cu;
    private List<String> clzNames;

    @Inject
    public Cu(@Assisted("pkg") final String pkg,
            @Assisted("clzName") final String clzName,
            @Assisted("srcPath") final String srcPath) {
        this.pkg = pkg;
        this.sourcePath = srcPath;
        clzNames = new ArrayList<>();
        clzNames.add(clzName);
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public String getPkg() {
        return pkg;
    }

    public List<String> getClzNames() {
        return clzNames;
    }

    public CompilationUnit getCu() {
        return cu;
    }

    public void setCu(final CompilationUnit cu) {
        this.cu = cu;
    }

    public boolean isKnown(final String clzPkg, final String clzName) {
        return clzPkg.equals(this.pkg) && clzNames.contains(clzName);
    }

    @Override
    public String toString() {
        return "Cu [clzNames=" + clzNames + ", pkg=" + pkg + ", sourcePath="
                + sourcePath + "]";
    }
}
