package org.codetab.uknit.core.node;

import static java.util.Objects.nonNull;

import java.util.Hashtable;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.codetab.uknit.core.config.Configs;
import org.eclipse.jdt.core.JavaCore;

/**
 * Creates Compiler Options.
 *
 * @author m
 *
 */
@Singleton
public class CompilerOptions {

    private Hashtable<String, String> options;

    @Inject
    public CompilerOptions(final Configs configs) {
        /*
         * Create options and set the default compiler options inside the
         * sensible options according to the given compliance level. In
         * additions to other options, it sets COMPILER_COMPLIANCE and
         * COMPILER_SOURCE.
         */
        options = JavaCore.getOptions();
        String complianceLevel =
                configs.getConfig("uknit.compiler.compliance", "1.8");
        JavaCore.setComplianceOptions(complianceLevel, options);

        /*
         * The above code has already set the COMPILER_SOURCE level option.
         * However, user defined config can reduce the source compatibility
         * level. If user has reduced the source level then set it.
         */
        String compilerSourceLevel = configs.getConfig("uknit.compiler.source");
        if (nonNull(compilerSourceLevel)) {
            options.put(JavaCore.COMPILER_SOURCE, compilerSourceLevel);
        }
    }

    public Hashtable<String, String> getOptions() {
        return new Hashtable<>(options);
    }

    public String getSourceLevel() {
        return options.get("org.eclipse.jdt.core.compiler.source");
    }

    public String getComplianceLevel() {
        return options.get("org.eclipse.jdt.core.compiler.compliance");
    }
}
