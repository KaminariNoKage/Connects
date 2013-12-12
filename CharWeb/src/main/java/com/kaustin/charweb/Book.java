package com.kaustin.charweb;

import android.database.Cursor;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kaustin on 12/5/13.
 */
public class Book {

    String name;
    HashMap<String, Character> allCharaters;

    public Book(String name){
        this.name = name;
        this.allCharaters = new HashMap<String, Character>();
    }

    public void getBookFromDB() throws JSONException{
        //Takes in an array of JSON data from the Database and converts it to hashmap of characters
        Cursor bookDB = MainActivity.myDBHelper.getBook(this.name);
         bookDB.moveToFirst();
        for(int i=0; i < bookDB.getCount(); i++){
            Character nuChar = new Character(bookDB.getString(1));
            nuChar.relationships = MainActivity.myDBHelper.stringToJSON(bookDB.getString(3));
            this.allCharaters.put(nuChar.name, nuChar);
        }
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
