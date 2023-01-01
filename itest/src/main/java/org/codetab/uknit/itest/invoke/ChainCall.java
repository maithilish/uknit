package org.codetab.uknit.itest.invoke;

import java.io.File;
import java.util.IntSummaryStatistics;
import java.util.List;

import org.codetab.uknit.itest.invoke.Model.Pet;
import org.codetab.uknit.itest.invoke.Model.Pets;

public class ChainCall {

    public List<Pet> assignInfer(final Pets pets) {
        return pets.getPets().get("dog");
    }

    public List<Pet> returnInfer(final Pets pets) {
        List<Pet> dogs = pets.getPets().get("dog");
        return dogs;
    }

    public File mockMockMockChain(final File file) {
        return file.getAbsoluteFile().getParentFile();
    }

    public String mockMockRealChain(final File file) {
        return file.getAbsoluteFile().getAbsolutePath();
    }

    public String mockRealRealChain(final File file) {
        return file.getName().toLowerCase();
    }

    public String realRealRealChain(final String name) {
        return name.toLowerCase().toUpperCase();
    }

    public IntSummaryStatistics mockRealMockChain(final File file) {
        return file.getName().codePoints().summaryStatistics();
    }

    public StringBuilder mockRealRealChainAsArg(final StringBuilder s1,
            final StringBuilder s2, final File file) {
        return s1.append(s2.append(file.getName().toLowerCase()));
    }

    public StringBuilder mockMockMockChainAsArg(final StringBuilder s1,
            final StringBuilder s2, final File file) {
        return s1.append(s2.append(file.getAbsoluteFile().getParentFile()));
    }

}
