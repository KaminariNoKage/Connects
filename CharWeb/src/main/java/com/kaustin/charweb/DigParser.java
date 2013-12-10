package com.kaustin.charweb;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kaustin on 12/5/13.
 */
public class DigParser {
    //Handles the parsing rules for entered strings

    HashMap<String, ArrayList<String>> allChar;

    public DigParser(){}

    public void sortString(Book curbook, String string){
        String[] charNew = string.split(">>");
        // [charName, restOfData1, restOfData2, ...]
        // EG: A >> B becomes [A,B]

        //Getting the current character
        Character curchar = curbook.allCharaters.get(charNew[0]);

        //Check if the character is in the HM
        //If so, then parse rest of character stuff
        //If not, then add to hash and parse rest of character stuff

    }

    public void splitDouble(String relation){
        if (relation.contains("==")){
            String[] relationDouble = relation.split("==");
        }
    }

    public void splitSingle(String relation){
        if (relation.contains("=")){
            String[] relationSingle = relation.split("=");
        }
    }
}
