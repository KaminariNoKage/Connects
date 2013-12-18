package com.kaustin.charweb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

/**
 * Created by kaustin on 12/6/13.
 */
public class AddDialog extends AlertDialog {
    Context context;
    String prompt;
    String username;
    User curUser;

    public AddDialog(Context context, String prompt){
        super(context);
        setContentView(R.layout.add_dialog);
        this.context = context;
        this.prompt = prompt;
    }

    public void onCreate(Bundle savedInstanceState){
        if (this.prompt != null){
            EditText textBox = (EditText)findViewById(R.id.textField);
            textBox.setText(this.prompt + " >> ");
        }

        Button submit = (Button)findViewById(R.id.submitRel);
        Button cancel = (Button)findViewById(R.id.cancelRel);

        //Adding the relationship to the app
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText inText = (EditText)findViewById(R.id.textField);
                String nuText = inText.getText().toString();
                parseInput(nuText);
                dismiss();
            }});

        //Do nothing, just close the Dialog
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }});
    }

    public void parseInput(String input){
        String charName, relBlock, relName, relType;
        String[] tokens, tolkens;
        Character addedChar = new Character();

        addedChar.name = input;
        charName = input;
        relName = "Unknown";
        relType = "Unknown";

        //Step #1, get the name of the character the user has entered
        // User enters in A >> B, so parse it to be [A,B]
        if (input.contains(">>")){
            tokens = input.split(">>");

            charName = tokens[0].trim();
            relBlock = tokens[1].trim();
            addedChar.name = charName;

            //Step #2, get the name of the relation and the relationship type
            // User enters in B = C, so parse it to be [B, C]
            if (relBlock.contains("=")){
                tolkens = relBlock.split("=");

                relName = tolkens[0].trim();
                relType = tolkens[1].trim();
            }
        }

        try{
            //Step#3, update the character in the Book
            MainActivity.myBook.editChar(charName, relName, relType);

            //Step #4, update the character in the database
            MainActivity.myDBHelper.updateCharacter(MainActivity.myBook, charName);

        }catch (Exception E) {System.out.println("ERROR PARSING STING, CHECK JSON"); }
    }
}