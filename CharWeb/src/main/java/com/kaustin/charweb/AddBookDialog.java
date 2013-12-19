package com.kaustin.charweb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kaustin on 12/15/13.
 */
public class AddBookDialog extends AlertDialog  {
    Context context;
    String bookName;
    boolean editName;

    public AddBookDialog(Context context, String bookName, boolean editName){
        super(context);
        setContentView(R.layout.addbook_dialog);
        this.context = context;
        this.bookName = bookName;
        this.editName = editName;
    }

    public void onCreate(Bundle savedInstanceState){
        EditText editText = (EditText) findViewById(R.id.textField);

        if (editName){
            editText.setText(bookName);
        }

        Button submit = (Button)findViewById(R.id.submitRel);
        Button cancel = (Button)findViewById(R.id.cancelRel);

        //Adding the book
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText entered = (EditText) findViewById(R.id.textField);
                String nuName = entered.getText().toString();
                if (!editName){
                    //If not an edit, then add the new book to the DB
                    addNewBook(nuName);
                } else {
                    //Else it is an edit, so change the existing name everywhere
                    editCurBook(bookName, nuName);
                    MainActivity.myDBHelper.setCurrentBook(nuName);
                }

                dismiss();
            }});

        //Do nothing, just close the Dialog
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }});
    }

    public void addNewBook(String nuName){
        //Step#1, get existing book list from Database
        ArrayList<String> curList = MainActivity.myDBHelper.getAllBooks();

        //Step#2, add the book to the list
        curList.add(nuName);

        //Step#3, sort the list to be alphabetical
        Collections.sort(curList);

        //Step#4, add back to the Database
        MainActivity.myDBHelper.editBookList(MainActivity.myBook.name, curList);
    }

    public void editCurBook(String oldName, String curName){
        //Step #1, get existing book list from the database
        ArrayList<String> curList = MainActivity.myDBHelper.getAllBooks();

        //Step#2, find the old name and replace it with the new
        for (int i=0; i < curList.size(); i++){
            if (curList.get(i).toString().equals(oldName)){
                curList.set(i, curName);
            }
        }

        //Step#3, sort the list alphabetically
        Collections.sort(curList);

        //Step#4, add back to the database
        MainActivity.myDBHelper.editBookList(MainActivity.myBook.name, curList);

        //Step#5, replace the bookname of the characters to respect the new name
        MainActivity.myDBHelper.editBookOfCharacters(oldName, curName);
    }
}
