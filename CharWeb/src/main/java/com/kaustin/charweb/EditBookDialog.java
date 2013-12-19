package com.kaustin.charweb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kaustin on 12/18/13.
 */
public class EditBookDialog extends AlertDialog {
    Context context;
    String bookName;

    public EditBookDialog(Context context, String bookName){
        super(context);
        setContentView(R.layout.editbook_dialog);
        this.context = context;
        this.bookName = bookName;
    }

    public void onCreate(Bundle savedInstanceState){
        TextView editText = (TextView) findViewById(R.id.booktitle);
        editText.setText(bookName);

        Button dismiss = (Button) findViewById(R.id.dismiss);
        Button activate = (Button) findViewById(R.id.activateBook);
        Button changeName = (Button)findViewById(R.id.changeBookName);
        Button delete = (Button)findViewById(R.id.del_button);


        //Do nothing, just close the Dialog
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }});

        activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Set the current book in the DB
                MainActivity.myDBHelper.setCurrentBook(bookName);
                dismiss();
        }});

        changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Set the current book in the DB
                showNewBookDialog(bookName, true);
        }});

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Set the current book in the DB
                MainActivity.myDBHelper.deleteBook(bookName);
                dismiss();
        }});
    }

    //Creating BOOK Dialog
    public void showNewBookDialog(String curBookName, boolean edit){
        AddBookDialog editBook = new AddBookDialog(context, curBookName, edit);
        editBook.show();
        //Restart the page
        editBook.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                Intent in = new Intent(context.getApplicationContext(), MainActivity.class);
                context.startActivity(in);
            }
        });
    }
}
