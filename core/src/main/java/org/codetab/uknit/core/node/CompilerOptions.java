package org.codetab.uknit.core.node;

import static java.util.Objects.nonNull;

import java.util.Map;

import javax.inject.Inject;

import org.codetab.uknit.core.config.Configs;
import org.eclipse.jdt.core.JavaCore;

public class CompilerOptions {

    @Inject
    private Configs configs;

    public Map<String, String> getOptions() {

        Map<String, String> options = JavaCore.getOptions();

        String complianceLevel =
                configs.getConfig("uknit.compiler.compliance", "1.8");
        JavaCore.setComplianceOptions(complianceLevel, options);

        // user defined config can reduce the source compatibility level
        String compilerSourceLevel = configs.getConfig("uknit.compiler.source");
        if (nonNull(compilerSourceLevel)) {
            options.put(JavaCore.COMPILER_SOURCE, compilerSourceLevel);
        }

        return options;
    }
}
