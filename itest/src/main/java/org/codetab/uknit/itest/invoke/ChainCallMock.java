package org.codetab.uknit.itest.invoke;

import java.io.File;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.IntStream;

import org.codetab.uknit.itest.invoke.Model.Pet;
import org.codetab.uknit.itest.invoke.Model.Pets;

public class ChainCallMock {

    public List<Pet> assignInfer(final Pets pets) {
        List<Pet> dogs = pets.getPets().get("dog");
        return dogs;
    }

    public List<Pet> returnInfer(final Pets pets) {
        return pets.getPets().get("dog");
    }

    public File mockMockMock(final File file) {
        return file.getAbsoluteFile().getParentFile();
    }

    public String mockMockReal(final File file) {
        return file.getAbsoluteFile().getAbsolutePath();
    }

    public String mockRealReal(final File file) {
        return file.getName().toLowerCase();
    }

    public String mockRealRealReal(final File file) {
        return file.getName().toLowerCase().toUpperCase();
    }

    public IntStream mockRealRealish(final File file) {
        return file.getName().codePoints();
    }

    public IntSummaryStatistics mockRealRealishRealish(final File file) {
        return file.getName().codePoints().summaryStatistics();
    }

    public StringBuilder mockRealRealAsArg(final StringBuilder s1,
            final StringBuilder s2, final File file) {
        return s1.append(s2.append(file.getName().toLowerCase()));
    }

    public StringBuilder mockMockMockAsArg(final StringBuilder s1,
            final StringBuilder s2, final File file) {
        return s1.append(s2.append(file.getAbsoluteFile().getParentFile()));
    }
}
