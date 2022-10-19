package org.codetab.uknit.core.zap.make.method;

import static java.util.Objects.isNull;
import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.node.CompilerOptions;
import org.codetab.uknit.core.util.StringUtils;
import org.codetab.uknit.core.zap.make.model.Heap;
import org.codetab.uknit.core.zap.make.model.IVar;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.JavaConventions;

import com.google.common.base.CaseFormat;

@Singleton
public class VarNames {

    @Inject
    private Configs configs;
    @Inject
    private StringUtils stringUtils;
    @Inject
    private Validator validator;

    private int msIndex;
    private String[] metaSyntantics;
    /**
     * inferred vars - var for arg method call, method call in return statement
     * etc., examples: LocalDate.parse(foo.birthday()), return foo.getName();
     */
    private int ivIndex;
    private String[] inferredVars;

    private int capIndex;
    private String[] captureVars;
    private static final int RANDOM_STR_LEN = 4;

    public void setup() {
        msIndex = 0;
        String value = configs.getNames("uknit.names.string.value",
                "Foo,Bar,Baz,Qux,Quux,Corge,Grault,Garply,Waldo,Fred,Plugh,Xyzzy,Thud");
        metaSyntantics = value.split(",");

        ivIndex = 0;
        value = configs.getNames("uknit.names.inferVar",
                "apple,grape,orange,kiwi,mango,banana,cherry,apricot,peach,fig,plum,lychee");
        inferredVars = value.split(",");

        capIndex = 0;
        value = configs.getNames("uknit.names.anonymous.class.capture",
                "captorA,captorB,captorC,captorD,captorE,captorF,captorG,captorH,captorI,captorK,captorL");
        captureVars = value.split(",");
    }

    public void resetIndexes() {
        ivIndex = 0;
        msIndex = 0;
        capIndex = 0;
    }

    public String getInferVarName(final Optional<String> typeName,
            final Heap heap) {

        String name = null;

        // names from type name - date, date2, date3 etc.,
        if (typeName.isPresent()) {
            String typeCamelName = CaseFormat.UPPER_CAMEL
                    .to(CaseFormat.LOWER_CAMEL, typeName.get());
            String configKey = String.join(".", "uknit.inferVar.name.useType",
                    typeName.get());
            boolean useTypeName = configs.getConfig(configKey, true);
            if (useTypeName) {
                // if alias is defined for type use it, else use typeCamelName
                configKey = String.join(".", "uknit.inferVar.name.alias",
                        typeName.get());
                typeCamelName = configs.getConfig(configKey, typeCamelName);
                name = heap.getIndexedVar(typeCamelName);

                // if name is not valid identifier then discard it
                if (!validator.isValidIdentifier(name)) {
                    name = null;
                }
            }
        }

        // names from uknit.names.inferVar - apple, grape etc.,
        if (isNull(name)) {
            if (ivIndex < inferredVars.length) {
                name = inferredVars[ivIndex++];
            } else {
                name = stringUtils.generateString(RANDOM_STR_LEN);
            }
        }
        return name;
    }

    public String getCaptureVarName() {
        if (capIndex < captureVars.length) {
            return captureVars[capIndex++];
        } else {
            return stringUtils.generateString(RANDOM_STR_LEN);
        }
    }

    public String getMetaSyntantics() {
        if (msIndex < metaSyntantics.length) {
            return metaSyntantics[msIndex++];
        } else {
            return stringUtils.generateString(RANDOM_STR_LEN);
        }
    }

    public void checkVarConsistency(final List<IVar> vars) {
        for (IVar var : vars) {
            String name = var.getName();
            List<IVar> matchedNameVars =
                    vars.stream().filter(v -> v.getName().equals(name))
                            .collect(Collectors.toList());
            long rvCount =
                    matchedNameVars.stream().filter(IVar::isReturnVar).count();
            long lvCount =
                    matchedNameVars.stream().filter(IVar::isLocalVar).count();
            long ivCount =
                    matchedNameVars.stream().filter(IVar::isInferVar).count();
            long pCount =
                    matchedNameVars.stream().filter(IVar::isParameter).count();
            long fCount =
                    matchedNameVars.stream().filter(IVar::isField).count();

            if (rvCount > 1 || lvCount > 1 || ivCount > 1 || pCount > 1
                    || fCount > 1) {
                String message =
                        spaceit("multiple var declaration found for", name);
                throw new IllegalStateException(message);
            }
        }
    }

    /**
     * Vars may have to be renamed when IM are called multiple times. There is
     * high probability that similar names may be indexed foo, foo1, foo2 etc.,
     * To avoid conflict better to use infer var name as suffix so that vars are
     * renamed as fooApple, fooGrape etc., instead of index.
     * @param name
     * @return
     */
    public String renameVar(final String name) {
        String suffix;
        if (ivIndex < inferredVars.length) {
            suffix = inferredVars[ivIndex++];
        } else {
            suffix = stringUtils.generateString(RANDOM_STR_LEN);
        }
        return name + StringUtils.capitalize(suffix);
    }
}

class Validator {

    @Inject
    private CompilerOptions compilerOptions;

    public boolean isValidIdentifier(final String name) {
        String sourceLevel = compilerOptions.getSourceLevel();
        String complianceLevel = compilerOptions.getComplianceLevel();
        IStatus status = JavaConventions.validateIdentifier(name, sourceLevel,
                complianceLevel);
        return status.isOK();
    }
}
