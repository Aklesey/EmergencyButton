package com.qoobico.emergencybutton.DataBase;


import com.qoobico.emergencybutton.adapter.Contact;

import java.util.List;

public interface IDatabaseHandler {
    public void addContact(Contact contact);
    public Contact getContact(int id);
    public List<Contact> getAllContacts();
    public int getContactsCount();
    public void deleteAll();
}
