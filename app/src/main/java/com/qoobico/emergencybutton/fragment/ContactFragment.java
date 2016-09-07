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

import com.qoobico.emergencybutton.MainActivity;
import com.qoobico.emergencybutton.R;
import com.qoobico.emergencybutton.adapter.AlarmContactAdapter;

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
        rv.setAdapter(new AlarmContactAdapter(this.context, MainActivity.getAllContacts()));
        return view;
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
