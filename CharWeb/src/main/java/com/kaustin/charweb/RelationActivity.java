package com.kaustin.charweb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by kaustin on 12/18/13.
 */
public class RelationActivity extends Activity {

    String charName, relName, relType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relation_activity);

        Intent intent = getIntent();

        //Retrieving intents
        charName = intent.getStringExtra("charName");
        relName = intent.getStringExtra("relName");
        relType = intent.getStringExtra("relType");

        //Setting the textviews
        final TextView characterName = (TextView) findViewById(R.id.character);
        final TextView relation = (TextView) findViewById(R.id.relationNameView);
        final TextView relationType = (TextView) findViewById(R.id.relationTypeView);

        characterName.setText(charName);
        relation.setText(relName);
        relationType.setText(relType);

        //Delete button and DB handler
        final Button delButton = (Button) findViewById(R.id.del_button);
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Delete Relation
                delRel();
                //Return to Character Activity
                Intent in = new Intent(getApplicationContext(), CharacterActivity.class);
                in.putExtra("charName", charName);

                //Going to new display of the note
                startActivity(in);
            }
        });

    }

    public void delRel(){
        //Delete a relationship from the database
        try{
            //Step #1, delete existing data from the book
            MainActivity.myBook.delCharRelation(charName,relName);

            //Step #2, update the character in the database
            MainActivity.myDBHelper.updateCharacter(MainActivity.myBook, charName);

        }catch (Exception E) {System.out.println("ERROR DELETING RELATION"); }
    }
}
