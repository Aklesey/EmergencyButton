package com.qoobico.emergencybutton.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.qoobico.emergencybutton.fragment.AbstractTabFragment;
import com.qoobico.emergencybutton.fragment.ContactFragment;
import com.qoobico.emergencybutton.fragment.HistoryFragment;
import com.qoobico.emergencybutton.fragment.SettingsFragment;

import java.util.HashMap;
import java.util.Map;

public class TabsFragmentAdapter extends FragmentPagerAdapter {

    private Map<Integer, AbstractTabFragment> tabs;
    private Context context;

    public TabsFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
        initTabMap(context);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {

        return  tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    private void initTabMap(Context context) {
        tabs = new HashMap<>();
        tabs.put(0, ContactFragment.getInstance(context));
        tabs.put(1, SettingsFragment.getInstance(context));
        tabs.put(2, HistoryFragment.getInstance(context));
    }
}
