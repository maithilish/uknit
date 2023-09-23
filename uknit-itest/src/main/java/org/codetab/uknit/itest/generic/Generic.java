package org.codetab.uknit.itest.generic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codetab.uknit.itest.generic.Model.Contacts;
import org.codetab.uknit.itest.generic.Model.ContactsData;

class Generic {

    private Contacts contacts;
    private ContactsData contactsData;

    public void inferInArg() {
        contactsData.setContactsMap(contacts.getContactsMap("foo"));
    }

    public void createInArg() {
        contactsData.setContactsMap(new HashMap<String, List<String>>());
    }

    public void createDiamondInArg() {
        contactsData.setContactsMap(new HashMap<>());
    }

    public void parameterInArg(final Map<String, List<String>> data) {
        contactsData.setContactsMap(data);
    }

    public void varInArg() {
        Map<String, List<String>> data = new HashMap<>();
        contactsData.setContactsMap(data);
    }
}
