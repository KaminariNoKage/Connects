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
        this.allCharaters = new JSONObject();
    }

    public void printBook(){
        System.out.println("BOOK NAME: " + this.name);
        String data = MainActivity.myDBHelper.jsonToString(this.allCharaters);
        System.out.println("DATA: " + data);
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
        Character nuChar = new Character(name);
        this.allCharaters.put(name, nuChar);
    }

    //DELETE CHARACTERS IN THE BOOK
    public void delCharacter(String name) throws JSONException{
        this.allCharaters.remove(name);
    }

}
