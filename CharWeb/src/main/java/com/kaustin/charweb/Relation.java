package com.kaustin.charweb;

/**
 * Created by kaustin on 12/12/13.
 */
public class Relation {

    String charName, relOneWay, relTwoWay;

    public Relation(String charName, String relOneWay){
        this.charName = charName;   //Name of the relation with whom the character is connected (same as key)
        this.relOneWay = relOneWay; //Relationship of the character to the relation, but not exactly in return
        //this.relTwoWay = relTwoWay; //Relationship between the relation and character have with one another
    }

}
