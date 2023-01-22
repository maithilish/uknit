package org.codetab.uknit.itest.statics;

import java.util.Objects;

class Model {

    static class DriverWait {
        private long id;

        DriverWait(final Driver driver, final int parseInt) {
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
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
            DriverWait other = (DriverWait) obj;
            return id == other.id;
        }
    }

    interface Driver {
    }
}
