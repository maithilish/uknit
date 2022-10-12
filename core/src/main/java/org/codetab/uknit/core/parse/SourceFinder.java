package org.codetab.uknit.core.parse;

import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CriticalException;
import org.codetab.uknit.core.util.IOUtils;

/**
 * Find java source file that defines class.
 *
 * @author m
 *
 */
public class SourceFinder {

    @Inject
    private IOUtils ioUtils;

    /**
     * Find java file that defines class by searching the file contents for
     * matching class name. Class may be public, private or nested.
     *
     * @param searchDir
     * @param clzName
     * @return
     */
    public String find(final String searchDir, final String clzName) {

        // remove any type parameter from class name
        String clzNameNoTypeParam = clzName.replaceFirst("(<.*>)", "");

        // class name(opt type param)(opt space+extends/implements/super) {
        String searchRegex =
                String.format("class %s(<.*>)?( .*)? \\{", clzNameNoTypeParam);

        List<File> srcFiles =
                ioUtils.searchSource(searchDir, "java", searchRegex);
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
