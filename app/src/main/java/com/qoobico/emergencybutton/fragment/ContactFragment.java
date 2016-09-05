package com.qoobico.emergencybutton.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.qoobico.emergencybutton.R;
import com.qoobico.emergencybutton.adapter.AlarmContactAdapter;
import com.qoobico.emergencybutton.adapter.Contact;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class ContactFragment extends AbstractTabFragment {
    private final static int LAYOUT = R.layout.fragment_contact;

    public static ContactFragment getInstance(Context context) {
        Bundle args = new Bundle();
        ContactFragment fragment = new ContactFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_contact));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recycleView);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(new AlarmContactAdapter(this.context, dataContact()));
        return view;
    }

    private static List<Contact> dataContact() {
        List<Contact> temp = AddContactActivity.getData();
        try {
            File file = new File("temp.out");
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(temp);
            oos.flush();
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cardViewContact) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
