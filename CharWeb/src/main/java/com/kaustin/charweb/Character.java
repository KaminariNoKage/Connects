package com.kaustin.charweb;

import android.widget.ArrayAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kaustin on 12/5/13.
 */
public class Character {

    String name;
    JSONObject relationships;

    public Character(String name){
        this.name = name;
        this.relationships = new JSONObject();
    }

    /*
    public void addRelation(String rName, String rType){
        if (!this.inRelation(rName)){
            //If not in the HashMap, then add it
            this.relationships.put(rName, new String[2]);
        }
        //Then add the new relation

        //this.relationships.get(rName) = rType;
    }

    public void editRelation(String rName, String[] rType){
        this.relationships.put(rName, rType);
    }

    public void deleteRelation(String rName){
        if (this.inRelation(rName)){
            //If not in the HashMap, then add it
            this.relationships.remove(rName);
        }
    }

    public boolean inRelation(String rName){
        //Basic function to check if relation in Relationship HashMap
        String[] relationType = this.relationships.get(rName);
        if (relationType != null){
            return true;
        }
        return false;
    }*/
}
