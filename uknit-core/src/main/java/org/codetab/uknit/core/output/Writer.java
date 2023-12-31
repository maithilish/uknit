package org.codetab.uknit.core.output;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetab.uknit.core.exception.CriticalException;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;

import com.google.common.io.CharSink;
import com.google.common.io.Files;

public class Writer {

    private static final Logger LOG = LogManager.getLogger();

    @Inject
    private Console console;

    public String format(final CompilationUnit cu) {
        String javaCode = cu.toString();

        Properties prefs = new Properties();
        prefs.setProperty(JavaCore.COMPILER_SOURCE,
                CompilerOptions.VERSION_1_8);
        prefs.setProperty(JavaCore.COMPILER_COMPLIANCE,
                CompilerOptions.VERSION_1_8);
        prefs.setProperty(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM,
                CompilerOptions.VERSION_1_8);
        prefs.setProperty(DefaultCodeFormatterConstants.FORMATTER_TAB_CHAR,
                "space");
        prefs.setProperty(
                DefaultCodeFormatterConstants.FORMATTER_USE_TABS_ONLY_FOR_LEADING_INDENTATIONS,
                "yes");

        CodeFormatter codeFormatter = ToolFactory.createCodeFormatter(prefs);
        IDocument doc = new Document(javaCode);
        try {
            TextEdit edit = codeFormatter.format(
                    CodeFormatter.K_COMPILATION_UNIT
                            | CodeFormatter.F_INCLUDE_COMMENTS,
                    javaCode, 0, javaCode.length(), 0, null);
            if (nonNull(edit)) {
                edit.apply(doc);
            }
        } catch (BadLocationException e) {
            throw new CriticalException(e);
        }
        return doc.get();
    }

    /**
     * Insert line breaks by brute force. Adding comments is very difficult with
     * ASTRewrite, so just a workaround till better solution is found.
     *
     * @param code
     * @return
     */
    public String insertLineBreaks(final String code) {

        boolean importBlock = false;
        boolean mockBlock = false;
        boolean beforeMethod = false;
        boolean testMethod = false;
        boolean whenBlock = false;
        boolean callBlock = false;
        boolean assertBlock = false;
        boolean argCaptureBlock = false;
        boolean verifyBlock = false;

        String[] lines = code.split("\\R");
        StringBuilder sb = new StringBuilder();
        String br = System.lineSeparator();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (Pattern.matches("^import.*", line) && !importBlock) {
                sb.append(br);
                importBlock = true;
            } else if (Pattern.matches(".*\\sclass .*\\s\\{", line)) {
                sb.append(br);
                mockBlock = false;
                beforeMethod = false;
            } else if (Pattern.matches(".*@Mock.*", line) && !mockBlock) {
                sb.append(br);
                mockBlock = true;
            } else if (Pattern.matches(".*@Before.*", line) && !beforeMethod) {
                sb.append(br);
                beforeMethod = true;
            } else if (Pattern.matches(".*@Test.*", line) && !testMethod) {
                sb.append(br);
                whenBlock = false;
                callBlock = false;
                assertBlock = false;
                argCaptureBlock = false;
                verifyBlock = false;
            } else if (Pattern.matches(".*when\\(.*", line) && !whenBlock) {
                sb.append(br);
                whenBlock = true;
            } else if (Pattern.matches(".*actual\\s=\\s.*", line)
                    && !callBlock) {
                sb.append(br);
                callBlock = true;
            } else if (Pattern.matches(".*ArgumentCaptor<.*>.*", line)
                    && !argCaptureBlock) {
                sb.append(br);
                argCaptureBlock = true;
            } else if (Pattern.matches(".*verify\\(.*", line) && !verifyBlock) {
                sb.append(br);
                verifyBlock = true;
            } else if (Pattern.matches(".*assert.*\\(.*", line)
                    && !assertBlock) {
                sb.append(br);
                assertBlock = true;
            } else if (Pattern.matches(".*fail\\(.*", line) && !assertBlock) {
                sb.append(br);
                assertBlock = true;
            }
            sb.append(line);
            sb.append(System.lineSeparator());
            sb.trimToSize();
        }
        return sb.toString();
    }

    public void display(final String code) {
        console.print(code, true);
    }

    public void write(final Path outputFilePath, final String code,
            final boolean overwrite) throws IOException {
        String msg = "";
        if (isNull(outputFilePath)) {
            msg = "unable to write test class, dest file name is null";
            LOG.error(msg);
        } else {
            File file = outputFilePath.toFile();
            if (!overwrite && file.exists()) {
                msg = String.format(
                        "output file not written, test class %s exists and no overwrite",
                        outputFilePath);
                LOG.info(msg);
            } else {
                Files.createParentDirs(file);
                CharSink cs = Files.asCharSink(file, Charset.defaultCharset());
                cs.write(code);
                msg = String.format("output test class to %s", outputFilePath);
                LOG.info(msg);
            }
        }
        console.print(msg);
    }
}
