package com.kaustin.charweb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by kaustin on 12/15/13.
 */
public class MiscDialog extends AlertDialog {
    //The extra dialog in the top right with settings options

    Context context;
    String username;
    User curUser;

    public MiscDialog(Context context){
        super(context);
        setContentView(R.layout.misc_dialog);
        this.context = context;
    }

    public void onCreate(Bundle savedInstanceState){
        //Setting the buttons
        Button howto = (Button) findViewById(R.id.howto);
        Button editBook = (Button)findViewById(R.id.editBook);
        //Button setCred = (Button)findViewById(R.id.setCredentials);
        //Button sync = (Button)findViewById(R.id.sync);
        Button cancel = (Button)findViewById(R.id.cancel);

        //Opens up New Book dialog
        editBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go to Edit Book activity
                Intent in = new Intent(context, BookActivity.class);
                in.putExtra("bookName", "Current: " + MainActivity.myBook.name);
                context.startActivity(in);
            }
        });

        //How to dialog
        howto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHowToDialog();
            }});

        //Syncs with the online server given the username and password
        /*setCred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }});*/

        //Opens up New Book dialog
        /*sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }});*/

        //Do nothing, just close the Dialog
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }});
    }

    //Creating HowTo Dialog
    public void showHowToDialog(){
        HowToDialog howto = new HowToDialog(context);
        howto.show();
    }

    //Creating NEW BOOK Dialog
    /*public void showMiscDialog(){
        NewBookDialog newBook = new NewBookDialog(context);
        newBook.show();
    }*/
}
