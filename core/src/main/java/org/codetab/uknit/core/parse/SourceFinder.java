package org.codetab.uknit.core.parse;

import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CriticalException;
import org.codetab.uknit.core.util.IOUtils;

public class SourceFinder {

    @Inject
    private IOUtils ioUtils;

    public String find(final String searchDir, final String clzName) {
        String regex = "class " + clzName + " ";
        List<File> srcFiles = ioUtils.searchSource(searchDir, "java", regex);
        if (srcFiles.size() == 0) {
            String message = spaceit("unable to find source file for", clzName,
                    "in", searchDir);
            throw new CriticalException(message);
        }
        if (srcFiles.size() > 1) {
            String message = spaceit("found multiple source file for", clzName,
                    "in", searchDir);
            throw new CriticalException(message);
        }
        return srcFiles.get(0).toString();
    }

}
