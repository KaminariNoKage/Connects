package com.kaustin.charweb;

import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kaustin on 12/5/13.
 */
public class Book {

    String name;
    JSONObject allCharaters;

    public Book(String name){
        this.name = name;
        this.allCharaters = new JSONObject();   //JSONObject of Character: relationshipsJSON
    }

    public void printBook(){
        System.out.println("BOOK NAME: " + this.name);
        System.out.println("DATA: " + this.allCharaters);
    }

    //Convert the characters in the book to an ArrayList for the main page
    public List<String> bookToChar()throws JSONException {
        //For rendering purposes, converting JSONObject to ArrayList
        List<String> nuCharList = new ArrayList<String>();
        //Getting all the characters of the book and putting them into list
        JSONObject tChar = this.allCharaters;
        Iterator<String> iter = tChar.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            nuCharList.add(key);
        }
        return nuCharList;
    }

    /***
     * Utility functions for the book to handle characters
     * @param name = Names of the character
     * @throws JSONException
     ***/

    //ADD CHARACTERS TO THE BOOK
    public void addCharacter(String name) throws JSONException{
        //Note, new characters will have no relations
        JSONObject nuChar = new JSONObject();
        this.allCharaters.put(name, nuChar);
    }

    //EDIT CHARACTER
    public void editChar(String charName, String relName, String relationData) throws JSONException{
        //Get character and current data and put in the new information
        //This function specifically updates the relationship of a character
        if (!(this.allCharaters.has(charName))){
            this.addCharacter(charName);
            MainActivity.myDBHelper.addCharacter(this, charName);
        }
        this.allCharaters.getJSONObject(charName).put(relName, relationData);
    }

    //DELETE CHARACTERS IN THE BOOK
    public void delCharacter(String name) throws JSONException{
        this.allCharaters.remove(name);
    }

    //DELETE RELATION OF A CHARACTER
    public void delCharRelation(String charName, String relName) throws JSONException{
        //Delete the relation of a character
        this.allCharaters.getJSONObject(charName).remove(relName);
    }

}
