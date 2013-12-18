package com.kaustin.charweb;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kaustin on 12/6/13.
 */
public class CharacterActivity extends Activity {
    //The Character's main page

    String thischarname;
    List<Relation> relationList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_activity);

        Intent intent = getIntent();

        thischarname = intent.getStringExtra("charName");
        final TextView characterName = (TextView) findViewById(R.id.character);
        characterName.setText(thischarname);

        //Get the character from the database
        try {
            JSONObject relations = getCharFromDB(thischarname);

            //Converts JSON into list of Relationships
            relationList = new ArrayList<Relation>();
            Iterator<String> iter = relations.keys();
            String key, rone;

            while (iter.hasNext()) {
                key = iter.next();
                //Get the value
                rone = relations.getString(key);
                Relation nuRel = new Relation(key, rone);
                relationList.add(nuRel);
            }

            // Set up the ArrayAdapter for the relationship list
            RelationListAdapter charListAdapter = new RelationListAdapter(this, relationList);
            ListView charList = (ListView) findViewById(R.id.characterResults);
            charList.setAdapter(charListAdapter);

            charList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    //Go to the relationship activity
                    //Getting title (id) of what is clicked
                    TextView relName = (TextView) view.findViewById(R.id.relationName);
                    TextView relType = (TextView) view.findViewById(R.id.oneWayRel);

                    String rname = relName.getText().toString();
                    String rtype = relType.getText().toString();

                    //Creating intent to pass information
                    Intent in = new Intent(getApplicationContext(), RelationActivity.class);
                    in.putExtra("charName", thischarname);
                    in.putExtra("relName", rname);
                    in.putExtra("relType", rtype);

                    //Going to new display of the note
                    startActivity(in);
                }
            });
        } catch (Exception E){ System.out.println("CANNOT GET RELATIONS OF CHARACTER"); }


        //DELETE CHARACTER
        final Button delButton = (Button) findViewById(R.id.del_button);
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Delete Relation
                deleteChar();
                //Return to Main Activity
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
            }
        });
    }

    //Database retrieving functions
    public JSONObject getCharFromDB(String name) throws JSONException{
        //Takes in an array of JSON data from the Database and converts it to hashmap of characters
        Cursor bookDB = MainActivity.myDBHelper.getCharacter(name);
        bookDB.moveToFirst();
        String jsonData = bookDB.getString(3);

        //Putting character in book
        JSONObject relations = MainActivity.myDBHelper.stringToJSON(jsonData);
        MainActivity.myBook.allCharaters.put(name, relations);

        return relations;
    }

    //DELETE THE CHARACTER
    public void deleteChar(){
        //Deletes the current character and all its data
        try{
            //Step #1, delete from the book
            MainActivity.myBook.delCharacter(thischarname);

            //Step #2, delete from the database
            MainActivity.myDBHelper.deleteCharacter(MainActivity.myBook, thischarname);
        } catch (Exception E){ System.out.println("UNABLE TO DELETE CHARACTER"); }
     }


    /*public List<Relation> charToRel(JSONObject tChar)throws JSONException {

        //return nuCharList;
    }*/

    /***
     * FOR THE SETTINGS MENU AT THE TOP OF THE APP
     * @param menu
     * @return
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    //Creating ADD Dialog
    public void showAddDialog(){
        AddDialog addRel = new AddDialog(CharacterActivity.this, thischarname);
        addRel.show();
        addRel.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
                startActivity(getIntent());
            }
        });
    }
    //Creating HowTo Dialog
    public void showHowToDialog(){
        HowToDialog howto = new HowToDialog(CharacterActivity.this);
        howto.show();
    }
    //Handling Clicking MENU items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
                showAddDialog();
                //Set the character as part of the first portion of the text
                //EG: "KAI >> "
                return true;
            case R.id.action_howto:
                showHowToDialog();
                //Set the character as part of the first portion of the text
                //EG: "KAI >> "
                return true;
            /*case R.id.action_settings:
                //Things
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
