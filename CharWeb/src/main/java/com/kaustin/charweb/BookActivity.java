package com.kaustin.charweb;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kaustin on 12/18/13.
 */
public class BookActivity extends Activity {
    //The Book's main page

    String curBook;
    List<String> bookList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        Intent intent = getIntent();

        //Setting the name of the current book
        curBook = intent.getStringExtra("bookName");
        final TextView curBookName = (TextView) findViewById(R.id.currentBook);
        curBookName.setText(curBook);

        //New book button
        Button newBookButton = (Button) findViewById(R.id.add_button);
        newBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Show the add Book button
                showNewBookDialog("", false);
            }
        });

        //Setting th list adapter for the rest of the books
        //Step #1, Get the list of current books in the database
        List<String> allBooks = MainActivity.myDBHelper.getAllBooks();
        BookListAdapter bookListAdapter = new BookListAdapter(this, allBooks);

        ListView allBooksList = (ListView) findViewById(R.id.bookResults);
        allBooksList.setAdapter(bookListAdapter);

        allBooksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Go to the relationship activity
                //Getting title (id) of what is clicked
                TextView bookT = (TextView) view.findViewById(R.id.booktitle);
                String booktitle = bookT.getText().toString();
                showEditBookDialog(booktitle);
            }
        });
    }

    //Creating BOOK Dialog
    public void showNewBookDialog(String bookName, boolean edit){
        AddBookDialog newBook = new AddBookDialog(BookActivity.this, bookName, edit);
        newBook.show();
        //Restart the page
        newBook.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
                startActivity(getIntent());
            }
        });
    }

    public void showEditBookDialog(String bookName){
        final String curName = bookName;
        EditBookDialog editBook = new EditBookDialog(BookActivity.this, bookName);
        editBook.show();
        editBook.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
            }
        });
    }
}
