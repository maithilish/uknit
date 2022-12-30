package org.codetab.uknit.itest.create;

import java.util.Objects;

class Model {

    class Factory {
        public Driver createDriver(final String name) {
            return new Driver(name);
        }

        class Driver {
            Driver(final String name) {
            }
        }
    }

    static class Event {

        private final String name;

        public Event(final String name) {
            this.name = name;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + Objects.hash(name);
            return result;
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
            Event other = (Event) obj;
            return Objects.equals(name, other.name);
        }
    }
}
