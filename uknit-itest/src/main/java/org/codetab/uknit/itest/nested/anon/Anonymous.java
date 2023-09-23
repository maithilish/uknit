package org.codetab.uknit.itest.nested.anon;

import java.io.File;
import java.io.FileFilter;

class Anonymous {

    // STEPIN - test the return object
    public FileFilter assignAnon(final File file) {
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return pathname.isHidden();
            }
        };
        return fileFilter;
    }

    // STEPIN - test the return object
    public FileFilter returnAnon(final File file) {
        return new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return pathname.isHidden();
            }
        };
    }

    public File[] anonArg(final File file) {
        File[] hiddenFiles = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return pathname.isHidden();
            }
        });
        return hiddenFiles;
    }

    public File[] anonVarAsArg(final File file) {
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return pathname.isHidden();
            }
        };
        File[] hiddenFiles = file.listFiles(filter);
        return hiddenFiles;
    }

    public void verifyAnon(final File file) {
        file.listFiles(new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return pathname.isHidden();
            }
        });
    }

    public void verifyAnonVar(final File file) {
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return pathname.isHidden();
            }
        };
        file.listFiles(filter);
    }

    public File[] mockFunctionalInterface(final File file,
            final FileFilter filter) {
        File[] hiddenFiles = file.listFiles(filter);
        return hiddenFiles;
    }
}
