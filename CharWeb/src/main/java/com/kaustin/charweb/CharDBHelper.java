package com.kaustin.charweb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by kaustin on 12/12/13.
 */
public class CharDBHelper extends SQLiteOpenHelper {

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_CHARACTERS = "characters";
        public static final String _ID = "_id";
        public static final String COLUMN_NAME = "name";    //Stores the character's name
        public static final String COLUMN_BOOK = "bookName";    //Name of the book the character is in
        public static final String COLUMN_DATA = "date";    //Stores the character's relationship data as JSON

        public static final String TABLE_USER = "userinfo";
        public static final String COLUMN_USER = "username";    //The user's username
        public static final String COLUMN_PASSWORD = "password";    //The user's password
        public static final String COLUMN_CURBOOK = "myBook";   //The current book of the user
        public static final String COLUMN_MYBOOKS = "myBooks";  //List of the books
    }

    //Methods to maintain database and tables
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA = ",";
    private static final String CREATE_TABLE_CHARACTERS=
            "CREATE TABLE " + FeedEntry.TABLE_CHARACTERS + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME + TEXT_TYPE + COMMA +
                    FeedEntry.COLUMN_BOOK + TEXT_TYPE + COMMA +
                    FeedEntry.COLUMN_DATA + TEXT_TYPE + " )";

    private static final String CREATE_TABLE_USER =
            "CREATE TABLE " + FeedEntry.TABLE_USER + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_USER + TEXT_TYPE + COMMA +
                    FeedEntry.COLUMN_PASSWORD + TEXT_TYPE + COMMA +
                    FeedEntry.COLUMN_CURBOOK + TEXT_TYPE + COMMA +
                    FeedEntry.COLUMN_MYBOOKS + TEXT_TYPE + " )";

    private static final String DELETE_BOOKS_DB =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_CHARACTERS;
    private static final String DELETE_USER_DB =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_USER;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "charBooks.db";

    public CharDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CHARACTERS);
        db.execSQL(CREATE_TABLE_USER);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(DELETE_BOOKS_DB);
        db.execSQL(DELETE_USER_DB);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    // HELPER FUNCTIONS FOR JSON DATA STORAGE
    public String jsonToString(JSONObject json){
        String toString = json.toString();
        return toString;
    }
    public JSONObject stringToJSON(String string) throws JSONException{
        JSONObject toJSON = new JSONObject(string);
        return toJSON;
    }
    public String arrlistToString(List<String> arr) throws JSONException {
        //Convert to JSON, then to string
        JSONObject allbooks = new JSONObject();
        allbooks.put("allbooks", new JSONArray(arr));
        String arrString = allbooks.toString();
        return arrString;
    }
    public ArrayList<String> stringToArrlist(String string) throws JSONException{
        JSONObject toArr = new JSONObject(string);
        ArrayList<String> bookNames = new ArrayList<String>();
        JSONArray jArr = toArr.optJSONArray("allbooks");
        for (int i=0; i < jArr.length(); i++){
            bookNames.add(jArr.get(i).toString());
        }
        return bookNames;
    }

    //UTILITY FUNCTIONS FOR THE BOOK
    public void editBookOfCharacters(String oldName, String nuName){
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            String corTo = FeedEntry.COLUMN_BOOK + "= '" + oldName + "'";

            ContentValues values = new ContentValues();
            values.put(FeedEntry.COLUMN_BOOK, nuName);       //New Book name

            db.update(FeedEntry.TABLE_CHARACTERS, values, corTo, null);

            db.close();
        }catch (Exception E){ System.out.println("ERROR IN CharDBHelper.editBookOfCharacters"); }
    }

    public void deleteBook(String bookName){
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            //Step#1, Delete from the main list of books
            Cursor c = db.rawQuery("SELECT * FROM " + FeedEntry.TABLE_USER, null);

            c.moveToFirst();

            String curBook = c.getString(3);
            String curBookList = c.getString(4);

            List<String> allBooks = stringToArrlist(curBookList);
            for (int i=0; i < allBooks.size(); i++){
                if (allBooks.get(i).toString().equals(bookName)){
                    allBooks.remove(i);
                    break;
                }
            }

            //Step#2, If the book is the current book, pick the first book from the fixed array
            //And set the new book to equal that
            if (curBook.equals(bookName)){
                curBook = allBooks.get(0).toString();
            }

            String corTo = FeedEntry._ID + "= '" + c.getInt(0) + "'";

            //Step#3, Update the DB
            //Making the new data
            ContentValues values = new ContentValues();
            values.put(FeedEntry.COLUMN_USER, "");       //Name of the User
            values.put(FeedEntry.COLUMN_PASSWORD, "");    //Password (for future syncing)
            values.put(FeedEntry.COLUMN_CURBOOK, curBook);  //Name of the current book
            values.put(FeedEntry.COLUMN_MYBOOKS, arrlistToString(allBooks));     //Name of the booklist

            db.update(FeedEntry.TABLE_USER, values, corTo, null);
            c.close();

            //Step #4, Delete all the book's characters from the DB
            String delTo = FeedEntry.COLUMN_BOOK + "= '" + bookName + "'";
            db.delete(FeedEntry.TABLE_CHARACTERS, delTo, null);

            db.close();

        }catch (Exception E){ System.out.println("ERROR IN CharDBHelper.deleteBook"); }
    }

    public void setCurrentBook(String nuBook){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + FeedEntry.TABLE_USER, null);

        c.moveToFirst();
        int id = c.getInt(0); //Get the ID
        String allBooks = c.getString(4);

        String corTo = FeedEntry._ID + "= " + id + "";

        //Making the new data
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_USER, "");       //Name of the User
        values.put(FeedEntry.COLUMN_PASSWORD, "");    //Password (for future syncing)
        values.put(FeedEntry.COLUMN_CURBOOK, nuBook);  //Name of the current book
        values.put(FeedEntry.COLUMN_MYBOOKS, allBooks);     //Name of the booklist

        db.update(FeedEntry.TABLE_USER, values, corTo, null);
        c.close();
        db.close();
        MainActivity.myBook.name = nuBook;
    }

    public String getCurrentBook(){
        //Gets the current book from TABLE_USER
        SQLiteDatabase db = this.getWritableDatabase();
        //Getting the cursor
        Cursor c = db.rawQuery("SELECT * FROM " + FeedEntry.TABLE_USER, null);

        if(c.getCount() == 0){
            //If there is nothing in the database, add a new user row
            addNewUser();
            //Get the query again
            c = db.rawQuery("SELECT * FROM " + FeedEntry.TABLE_USER, null);
        }

        c.moveToFirst();
        String curBook = c.getString(3);
        c.close();
        db.close();
        return curBook;
    }

    public ArrayList<String> getAllBooks(){
        ArrayList<String> allBooks = new ArrayList<String>();
        try{
        //Gets the current book from TABLE_USER
        SQLiteDatabase db = this.getWritableDatabase();
        //Getting the cursor
        Cursor c = db.rawQuery("SELECT * FROM " + FeedEntry.TABLE_USER, null);

        c.moveToFirst();

        String curBook = c.getString(4);
        allBooks = stringToArrlist(curBook);

        c.close();
        db.close();
        }catch (Exception E) { System.out.println("ERROR: CharDBHelper.getAllBooks"); }

        return allBooks;
    }

    public void editBookList(String curBookName, List<String> allBooks){
        //Edits the book list in the database
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String bookList = arrlistToString(allBooks);
            String corTo = FeedEntry.COLUMN_CURBOOK + "= '" + curBookName + "'";

            ContentValues values = new ContentValues();
            values.put(FeedEntry.COLUMN_USER, "");       //Name of the User
            values.put(FeedEntry.COLUMN_PASSWORD, "");    //Password (for future syncing)
            values.put(FeedEntry.COLUMN_CURBOOK, curBookName);  //Name of the current book
            values.put(FeedEntry.COLUMN_MYBOOKS, bookList);     //Name of the booklist

            db.update(FeedEntry.TABLE_USER, values, corTo, null);

        } catch (Exception E){ System.out.println("ERROR IN CharDBHelper.editBookList"); }
    }

    public void addNewUser(){
        try {
        //Adds a new User to the Database, NOTE: This is a default function
        //The actual username is not set, only the book name
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> prelim = new ArrayList<String>();
        prelim.add("myBook");

        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_USER, "");       //Name of the User
        values.put(FeedEntry.COLUMN_PASSWORD, "");    //Password (for future syncing)
        values.put(FeedEntry.COLUMN_CURBOOK, "myBook");  //By default, add the user
        values.put(FeedEntry.COLUMN_MYBOOKS, arrlistToString(prelim));

        // insert row
        long newRowId = db.insert(FeedEntry.TABLE_USER, null, values);

        }catch (Exception E){ System.out.println( "UNABLE TO ADD NEW USER" ); }
    }

    //UTILITY FUNCTIONS FOR THE CHARACTERS
    public void deleteCharacter(Book book, String charName){
        //Deletes specified character from the database
        SQLiteDatabase db = this.getWritableDatabase();
        String bookName = book.name;
        String corTo = FeedEntry.COLUMN_NAME + "= '" + charName + "' AND "
                + FeedEntry.COLUMN_BOOK + "= '" + bookName + "'";

        db.delete(FeedEntry.TABLE_CHARACTERS, corTo, null);
    }


    public void updateCharacter(Book book, String charName) throws JSONException{
        //Updates the character (i.e. if there is a new relationship) in the database
        SQLiteDatabase db = this.getWritableDatabase();
        String data = jsonToString(book.allCharaters.getJSONObject(charName));
        String bookName = book.name;
        String corTo = FeedEntry.COLUMN_NAME + "= '" + charName + "' AND "
                + FeedEntry.COLUMN_BOOK + "= '" + bookName + "'";

        //Making the new data
        ContentValues nuData = new ContentValues();
        nuData.put(FeedEntry.COLUMN_NAME, charName);
        nuData.put(FeedEntry.COLUMN_BOOK, bookName);
        nuData.put(FeedEntry.COLUMN_DATA, data);

        db.update(FeedEntry.TABLE_CHARACTERS, nuData, corTo, null);

    }

    public void addCharacter(Book book, String charName) throws JSONException{
        //Adds a character to the Database
        SQLiteDatabase db = this.getWritableDatabase();

        String data = jsonToString(book.allCharaters.getJSONObject(charName));

        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME, charName);       //Name of the Character
        values.put(FeedEntry.COLUMN_BOOK, book.name);    //Name of the "Book"
        values.put(FeedEntry.COLUMN_DATA, data);  //Initialize empty relationship list

        // insert row
        long newRowId = db.insert(FeedEntry.TABLE_CHARACTERS, null, values);
    }
    public Cursor getCharacter(String name){
        //Getting the query of the specified character
        String[] allCols = {FeedEntry._ID,
                FeedEntry.COLUMN_NAME,
                FeedEntry.COLUMN_BOOK,
                FeedEntry.COLUMN_DATA};
        //Turning DB into string array, getting specific title of what is clicked
        return this.getReadableDatabase().query(FeedEntry.TABLE_CHARACTERS,
                allCols, "name=" + "\"" + name + "\"", null, null, null, null);
    }
    public Cursor getBook(String name){
        //Getting the query of the specified character
        String[] allCols = {FeedEntry._ID,
                FeedEntry.COLUMN_NAME,
                FeedEntry.COLUMN_BOOK,
                FeedEntry.COLUMN_DATA};
        //Turning DB into string array, getting specific title of what is clicked
        return this.getReadableDatabase().query(FeedEntry.TABLE_CHARACTERS,
                allCols, "bookName=" + "\"" + name + "\"", null, null, null, null);
    }
    public boolean isInCharDB(String name, String bookName) {
        //Checks if a character is in the Character database
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + FeedEntry.TABLE_CHARACTERS +
                " WHERE " + "name" + "= '" + name + "'" + " AND " + "bookName" + "= '" + bookName + "'", null);

        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
}