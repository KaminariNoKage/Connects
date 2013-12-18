package com.kaustin.charweb;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaustin on 12/8/13.
 */
public class AllCharFragment extends Fragment {

    public List<String> allChar;
    public Book curBook;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            curBook = MainActivity.myBook;
            allChar = curBook.bookToChar();
        }catch (Exception E){ System.out.println("CANNOT CONVERT BOOK TO LIST"); }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.allchar_fragment, null);

        //Setting the book title
        TextView bookName = (TextView) v.findViewById(R.id.currentBook);
        bookName.setText(MainActivity.myBook.name);

        // Set up the ArrayAdapter for the feedList
        CharacterListAdapter charListAdapter = new CharacterListAdapter(this.getActivity(), allChar);
        ListView charList = (ListView) v.findViewById(R.id.characterResults);
        charList.setAdapter(charListAdapter);

        charList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Getting title (id) of what is clicked
                TextView textView = (TextView) view.findViewById(R.id.charName);
                String name = textView.getText().toString();

                //Creating intent to pass information
                Intent in = new Intent(getActivity(), CharacterActivity.class);
                in.putExtra("charName", name);

                //Going to new display of the note
                startActivity(in);
            }
        });

        return v;
    }
}
