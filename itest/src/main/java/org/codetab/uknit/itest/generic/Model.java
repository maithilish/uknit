package org.codetab.uknit.itest.generic;

import java.util.List;
import java.util.Map;

class Model {

    class Contacts {

        public Map<String, List<String>> getContactsMap(
                final String groupName) {
            return null;
        }

    }

    class ContactsData {

        private Map<String, List<String>> contactsMap;

        public Map<String, List<String>> getContactsMap() {
            return contactsMap;
        }

        public void setContactsMap(
                final Map<String, List<String>> contactsMap) {
            this.contactsMap = contactsMap;
        }
    }
}
