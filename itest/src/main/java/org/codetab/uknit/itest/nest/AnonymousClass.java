package org.codetab.uknit.itest.nest;

import java.io.File;
import java.io.FileFilter;

public class AnonymousClass {

    // STEPIN - not possible to test
    public FileFilter anonymousReturn(final File file) {
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return pathname.isHidden();
            }
        };
        return fileFilter;
    }

    public File[] anonymousArg(final File file) {
        File[] hiddenFiles = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return pathname.isHidden();
            }
        });
        return hiddenFiles;
    }

    public File[] mockFilter(final File file, final FileFilter filter) {
        File[] hiddenFiles = file.listFiles(filter);
        return hiddenFiles;
    }

    public void anonymousInVerify(final File file) {
        file.listFiles(new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return pathname.isHidden();
            }
        });
    }
}
