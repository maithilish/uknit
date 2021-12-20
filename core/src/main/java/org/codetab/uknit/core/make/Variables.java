package org.codetab.uknit.core.make;

import static org.codetab.uknit.core.util.StringUtils.spaceit;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.codetab.uknit.core.config.Configs;
import org.codetab.uknit.core.make.model.IVar;
import org.codetab.uknit.core.util.StringUtils;

@Singleton
public class Variables {

    @Inject
    private Configs configs;
    @Inject
    private StringUtils stringUtils;

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
    private static final int RANDOM_STR_LEN = 8;

    public void setup() {
        msIndex = 0;
        String value = configs.getConfig("uknit.createInstance.string.value",
                "foo,bar,baz,qux,quux,corge,grault,garply,waldo,fred,plugh,xyzzy,thud");
        metaSyntantics = value.split(",");

        ivIndex = 0;
        value = configs.getConfig("uknit.inferVar.name",
                "apple,grape,orange,kiwi,mango,banana,cherry,apricot,peach,fig,plum,lychee,melon");
        inferredVars = value.split(",");

        capIndex = 0;
        value = configs.getConfig("uknit.anonymous.class.capture.name",
                "capA,capB,capC,capD,capE,capF,capG,capH,capI,capK,capL");
        captureVars = value.split(",");
    }

    public void resetIndexes() {
        ivIndex = 0;
        msIndex = 0;
        capIndex = 0;
    }

    public String getInferVarName() {
        if (ivIndex < inferredVars.length) {
            return inferredVars[ivIndex++];
        } else {
            return stringUtils.generateString(RANDOM_STR_LEN);
        }
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
}
