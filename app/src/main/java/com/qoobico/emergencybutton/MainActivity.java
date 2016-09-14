package com.qoobico.emergencybutton;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.qoobico.emergencybutton.DataBase.DatabaseHandler;
import com.qoobico.emergencybutton.adapter.Contact;
import com.qoobico.emergencybutton.adapter.TabsFragmentAdapter;
import com.qoobico.emergencybutton.fragment.AddContactActivity;
import com.qoobico.emergencybutton.fragment.EditContactActivity;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private final static int LAYOUT = R.layout.activity_main;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private static DatabaseHandler db;
    public static boolean first = false;
    public static boolean second = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        initToolbar();
        initNavigationView();
        initTabs();
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
        db = new DatabaseHandler(this);

    }

    public static void addOneContacts(Contact contact) {
        db.addContact(contact);
    }

    public static List<Contact> getAllContacts() {
        return db.getAllContacts();
    }

    public static void addListContacts(List<Contact> contacts) {
        db.deleteAll();
        for (int i = 0; i < contacts.size(); i++) {
            db.addContact(contacts.get(i));
        }
    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.menu);
    }

    private void initTabs() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabsFragmentAdapter adapter = new TabsFragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void initNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.view_navigation_open, R.string.view_navigation_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.action_contact:
                        showContactTab();
                        break;
                    case R.id.action_settirng:
                        showSettingsTab();
                        break;
                }
                return true;
            }
        });
    }

    private void showSettingsTab() {
        viewPager.setCurrentItem(Constant.TAB_TWO);
    }

    private void showContactTab() {
        viewPager.setCurrentItem(Constant.TAB_ONE);
    }

    public void newScreen(View view) {
        Intent SecAct = new Intent(getApplicationContext(), AddContactActivity.class);
        startActivity(SecAct);
    }

    public void newScreen(MenuItem item) {
        Intent SecAct = new Intent(getApplicationContext(), AddContactActivity.class);
        startActivity(SecAct);
    }

    public void contactEdit(MenuItem item) {
        Intent SecAct = new Intent(getApplicationContext(), EditContactActivity.class);
        startActivity(SecAct);
    }

    public void contactDelete(MenuItem item) {
        new EditContactActivity().contactDeleted();
        initTabs();
        Toast.makeText(getApplicationContext(), "Contact has deleted.", Toast.LENGTH_SHORT).show();

    }



    public void startAlarmOnButton(View view) {
        Intent SecAct = new Intent(getApplicationContext(), MyServiceButton.class);
        startService(SecAct);
    }

}
