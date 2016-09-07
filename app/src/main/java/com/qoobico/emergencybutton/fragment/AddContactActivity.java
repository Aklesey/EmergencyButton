package com.qoobico.emergencybutton.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.qoobico.emergencybutton.MainActivity;
import com.qoobico.emergencybutton.R;
import com.qoobico.emergencybutton.adapter.Contact;

import java.util.List;


public class AddContactActivity extends AppCompatActivity {
    private final static int LAYOUT_2 = R.layout.add_contact_activity;
    private static List<Contact> data = MainActivity.getAllContacts();
    EditText name, phone, email;

    public static List<Contact> getData() {
        return data;
    }

    public static String[] getDataPhoneList() {
        data = MainActivity.getAllContacts();
        String[] temp = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            temp[i] = data.get(i).getTel();
        }
        return temp;
    }

    public static void setData(List<Contact> data) {
        AddContactActivity.data = data;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT_2);
    }

    public void saveContact(View view) {

        Toast.makeText(getApplicationContext(), "Contact has added.", Toast.LENGTH_SHORT).show();
        fillListAdapter();
        Intent SecAct = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(SecAct);
    }

    private void fillListAdapter() {
        name = (EditText) findViewById(R.id.editName);
        phone = (EditText) findViewById(R.id.editNumber);
        email = (EditText) findViewById(R.id.editEmail);
        Contact temp = new Contact(name.getText().toString(), phone.getText().toString(), email.getText().toString());
        MainActivity.addOneContacts(temp);
    }
}

