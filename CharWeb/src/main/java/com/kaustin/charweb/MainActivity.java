package com.kaustin.charweb;


import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {

    public static Book myBook;
    public static CharDBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Database
        myDBHelper = new CharDBHelper(this);


        //Dummy Data
        myBook = new Book("myBook");
        myDBHelper.addCharacter("Kai", myBook);
        myDBHelper.addCharacter("Domian", myBook);
        myDBHelper.addCharacter("Austin", myBook);

        //Dummy Data
        try{
            myBook.getBookFromDB();
        }catch (Exception E) { System.out.println("MainActivity -> Unhandled JSON Error"); }

        // Define view fragments
        AllCharFragment charFragment = new AllCharFragment();
        SearchFragment searchFragment = new SearchFragment();

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        ActionBar.Tab charTab = actionBar.newTab().setText(R.string.tab1);
        charTab.setTabListener(new NavTabListener(charFragment));

        ActionBar.Tab searchTab = actionBar.newTab().setText(R.string.tab2);
        searchTab.setTabListener(new NavTabListener(searchFragment));

        actionBar.addTab(charTab);
        actionBar.addTab(searchTab);

        actionBar.setStackedBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.android_dark_blue)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    //Creating ADD Dialog
    public void showAddDialog(){
        AddDialog addRel = new AddDialog(MainActivity.this);
        addRel.show();
    }
    //Handling Clicking MENU items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                showAddDialog();
                return true;
            case R.id.action_sync:
                //LOG IN TO DATABASE
                //SYNC DATA
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // MISCELLANEOUS FUNCTIONS
    public static Book getMyBook(){
        return myBook;
    }

}