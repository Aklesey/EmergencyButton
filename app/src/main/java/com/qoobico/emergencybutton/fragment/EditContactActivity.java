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


public class EditContactActivity extends AppCompatActivity {
    private final static int LAYOUT_2 = R.layout.add_contact_activity;
    private static List<Contact> data;
    EditText name, phone, email;
    public static int pos;

    public EditContactActivity() {

    }

    public void setPos(int poss) {
        EditContactActivity.pos = poss;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT_2);
        setEditText();

    }

    private void setEditText() {
        data = MainActivity.getAllContacts();
        name = (EditText) findViewById(R.id.editName);
        phone = (EditText) findViewById(R.id.editNumber);
        email = (EditText) findViewById(R.id.editEmail);
        name.setText(data.get(pos).getTitle());
        phone.setText(data.get(pos).getTel());
        email.setText(data.get(pos).getEmail());
    }


    public void saveContact(View view) {
        data = MainActivity.getAllContacts();
        Toast.makeText(getApplicationContext(), R.string.contact_chenging, Toast.LENGTH_SHORT).show();
        fillListAdapter();
        Intent SecAct = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(SecAct);
    }

    private void fillListAdapter() {
        data = MainActivity.getAllContacts();
        data.remove(pos);
        data.add(pos, new Contact(name.getText().toString(),
                phone.getText().toString(), email.getText().toString()));
        MainActivity.addListContacts(data);
    }


    public void contactDeleted() {
        data = MainActivity.getAllContacts();
        data.remove(pos);
        MainActivity.addListContacts(data);
    }
}

