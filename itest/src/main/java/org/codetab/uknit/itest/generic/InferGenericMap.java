package org.codetab.uknit.itest.generic;

public class InferGenericMap {

    private Contacts contacts;
    private ContactsData contactsData;

    public void build() {
        contactsData.setContactsMap(contacts.getContactsMap("foo"));
    }

}
