package org.codetab.uknit.itest.nested.statics;

import java.io.File;
import java.util.Objects;

class Members {

    // static member
    static File file;

    // static member
    static {
        file = new File("foo");
    }

    // static nested class member
    static class Attributes {
        private boolean hidden;
        private boolean readonly;

        public boolean isHidden() {
            return hidden;
        }

        public void setHidden(final boolean hidden) {
            this.hidden = hidden;
        }

        public boolean isReadonly() {
            return readonly;
        }

        public void setReadonly(final boolean readonly) {
            this.readonly = readonly;
        }

        @Override
        public int hashCode() {
            return Objects.hash(hidden, readonly);
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Attributes other = (Attributes) obj;
            return hidden == other.hidden && readonly == other.readonly;
        }

    }

    public static File getFileStatic() {
        return file;
    }

    public File getFile() {
        return file;
    }

    public Attributes getAttributes() {
        Attributes attributes = new Attributes();
        attributes.setHidden(true);
        attributes.setReadonly(true);
        return attributes;
    }

}
