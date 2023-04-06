package org.codetab.uknit.core.parse;

import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.inject.Inject;

import org.codetab.uknit.core.exception.CriticalException;
import org.codetab.uknit.core.util.IOUtils;

/**
 * Find java source file that defines class.
 *
 * @author Maithilish
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

        // find file by name (top level public classes)
        try {
            String filePath = ioUtils.findFile(searchDir, clzName);
            return filePath;
        } catch (FileNotFoundException e) {
        }

        // find file by scanning file content (non public classes in a file)
        StringBuilder classRegex = new StringBuilder();
        classRegex.append(
                "((|public|final|abstract|private|static|protected)(\\\\s+))?");
        classRegex.append("(class)(\\s+)");
        classRegex.append(clzName);
        classRegex.append("(<.*>)?");
        classRegex.append("(\\s+extends\\s+\\w+)?(<.*>)?");
        classRegex.append("(\\s+implements\\s+)?(.*)?(<.*>)?");
        classRegex.append("(\\s*)\\{");

        List<File> srcFiles =
                ioUtils.searchSource(searchDir, "java", classRegex.toString());
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
