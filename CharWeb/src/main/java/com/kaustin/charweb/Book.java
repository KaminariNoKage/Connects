package com.kaustin.charweb;

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

}
