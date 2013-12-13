package com.kaustin.charweb;

import android.widget.ArrayAdapter;

import org.json.JSONException;
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


    /***
     * UTILITY FUNCTIONS for working with relationships
     * NOTE: Any edits to a relationship permanently replaces them.
     * ***/

    public void addRelation(Relation nuRel) throws JSONException{
        if (!this.inRelation(nuRel.charName)){
            //If not in the JSONOnject, then add it
            this.relationships.put(nuRel.charName, nuRel);
        }
    }
    public void editRelation(Relation nuRel) throws JSONException{
        //Replace existing relationship
        this.relationships.put(nuRel.charName, nuRel);
    }

    public void deleteRelation(String delRelName) throws JSONException{
        if (this.inRelation(delRelName)){
            this.relationships.remove(delRelName);
        }
    }

    public boolean inRelation(String relationName)throws JSONException {
        //Basic function to check if relation in Relationship JSON
        Object relationType = this.relationships.get(relationName);
        if (relationType != null){
            return true;
        }
        return false;
    }
}
