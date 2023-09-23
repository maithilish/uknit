package org.codetab.uknit.itest.invoke;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.Path;

import org.codetab.uknit.itest.invoke.Model.Foo;
import org.codetab.uknit.itest.invoke.Model.Statics;

class CallStatic {

    public String assignStatic() {
        String name = Statics.getName("foo");
        return name;
    }

    public String returnStatic() {
        return Statics.getName("foo");
    }

    public Path assignInvokeOnStaticCallMock() {
        Path fileName = Statics.getPath("foo", "bar").getFileName();
        return fileName;
    }

    public Path returnInvokeOnStaticCallMock() {
        return Statics.getPath("foo", "bar").getFileName();
    }

    public String assignInvokeOnStaticCallReal() {
        String name = Statics.getName("hello").toLowerCase();
        return name;
    }

    public String returnInvokeOnStaticCallReal() {
        return Statics.getName("hello").toLowerCase();
    }

    public FileSystem assignStaticInInvokeExp(final String foo,
            final String bar) {
        Path path = Statics.getPath(foo).relativize(Path.of(bar));
        return path.getFileSystem();
    }

    public FileSystem returnStaticInInvokeExp(final String foo,
            final String bar) {
        return Statics.getPath(foo).relativize(Path.of(bar)).getFileSystem();
    }

    public int assignStaticInArg(final Path path) {
        int val = path.compareTo(
                Path.of(Statics.getName(Statics.getFile().getAbsolutePath())));
        return val;
    }

    public int returnStaticInArg(final Path path) {
        return path.compareTo(
                Path.of(Statics.getName(Statics.getFile().getAbsolutePath())));
    }

    public void staticCallArgReassign(final Foo foo) {
        boolean a = true;
        foo.append(String.valueOf(a));
        a = false;
        foo.append(String.valueOf(a));
    }

    public void staticCallArgLiteral(final Foo foo) {
        foo.append(String.valueOf(true));
        foo.append(String.valueOf(false));
    }

    public void staticCallArgInvoke(final Foo foo, final File file) {
        foo.append(String.valueOf(file.canExecute()));
    }
}
