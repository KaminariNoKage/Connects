package com.kaustin.charweb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


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

    //UTILITY FUNCTIONS FOR THE DBS
    public void addCharacter(String name, Book book){
        //Adds a book to the Database
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME, name);       //Name of the Character
        values.put(FeedEntry.COLUMN_BOOK, book.name);    //Name of the "Book"
        values.put(FeedEntry.COLUMN_DATA, jsonToString(new JSONObject()));  //Initialize empty relationship list

        // insert row
        long newRowId = db.insert(FeedEntry.TABLE_CHARACTERS, null, values);
        Log.v("ROW ID: ", "" + newRowId);
    }
    public Cursor getCharatcer(String name){
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