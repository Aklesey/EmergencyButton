package com.qoobico.emergencybutton.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qoobico.emergencybutton.R;

public class InstructionsFragment extends AbstractTabFragment {
    private final static int LAYOUT = R.layout.fragment_example;

    public static InstructionsFragment getInstance(Context context) {

        Bundle args = new Bundle();
        InstructionsFragment fragment = new InstructionsFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_instructions));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
