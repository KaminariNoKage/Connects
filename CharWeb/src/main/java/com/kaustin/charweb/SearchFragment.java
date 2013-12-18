package com.kaustin.charweb;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaustin on 12/8/13.
 */
public class SearchFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.search_fragment, null);

        //SEARCH button setup
        Button search = (Button) v.findViewById(R.id.searchButton);
        final Context context = this.getActivity();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Gets the words entered by the user
                final TextView enteredSearch = (TextView) getActivity().findViewById(R.id.searchField);
                String searchWords = enteredSearch.getText().toString();

                //Getting Characters from the DB
                List<String> allSearchData = new ArrayList<String>();
                Cursor foundChar = MainActivity.myDBHelper.getCharacter(searchWords);
                foundChar.moveToFirst();
                for(int i=0; i < foundChar.getCount(); i++){
                    allSearchData.add(foundChar.getString(1));
                    foundChar.moveToNext();
                }

                // Set up the ArrayAdapter for the results of the search
                CharacterListAdapter searchListAdapter = new CharacterListAdapter(context, allSearchData);
                ListView searchList = (ListView) v.findViewById(R.id.searchResults);
                searchList.setAdapter(searchListAdapter);

                searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

            }

        });

        search.setFocusable(false);

        return v;
    }
}
