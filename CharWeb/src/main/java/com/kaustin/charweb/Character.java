package com.kaustin.charweb;

import android.widget.ArrayAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kaustin on 12/5/13.
 */
public class Character {
    //Holds characters when parsing from de Screen

    String name;
    HashMap<String, String[]> relationships;

    public Character(){
        this.name = name;
        this.relationships = new HashMap<String, String[]>();
    }
}
