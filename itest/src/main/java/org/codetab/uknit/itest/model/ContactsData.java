package org.codetab.uknit.itest.model;

import java.util.List;
import java.util.Map;

public class ContactsData {

    private Map<String, List<String>> contactsMap;

    public Map<String, List<String>> getContactsMap() {
        return contactsMap;
    }

    public void setContactsMap(final Map<String, List<String>> contactsMap) {
        this.contactsMap = contactsMap;
    }
}
