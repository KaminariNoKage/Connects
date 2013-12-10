package com.kaustin.charweb;


import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {

    public static Book myBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Dummy Data
        myBook = new Book("myBook");
        myBook.allCharaters.put("Kai", new Character("Kai"));
        myBook.allCharaters.put("Domian", new Character("Domian"));
        myBook.allCharaters.put("Austin", new Character("Austin"));

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // MISCELLANEOUS FUNCTIONS
    public static Book getMyBook(){
        return myBook;
    }
    public static ArrayList<Character> getMyBookChar(){
        return bookToChar(myBook.allCharaters);
    }

    public static ArrayList<Character> bookToChar(HashMap<String, Character> bookCharacters){
        //For rendering purposes, converting HashMap to ArrayList
        ArrayList<Character> nuCharList = new ArrayList<Character>();
        //Getting all the characters of the book and putting them into list
        for (String key : bookCharacters.keySet()) {
            nuCharList.add(bookCharacters.get(key));
        }
        return nuCharList;
    }
}