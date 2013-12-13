package com.kaustin.charweb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kaustin on 12/9/13.
 */
public class CharacterListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> characters;

    public CharacterListAdapter(Context context, List<String> characters){
        super(context, R.layout.search_item, characters);
        this.context = context;
        this.characters = characters;
    }

    private class ConnectionItemHolder{
        TextView charName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ConnectionItemHolder holder;
        View connectionRow = convertView;

        if(connectionRow == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            connectionRow = inflater.inflate(R.layout.search_item, parent, false);
            holder = new ConnectionItemHolder();
            holder.charName = (TextView) connectionRow.findViewById(R.id.charName);

            connectionRow.setTag(holder);
        } else {
            holder = (ConnectionItemHolder) connectionRow.getTag();
        }

        String item = characters.get(position);

        holder.charName.setText(item);

        return connectionRow;
    }
}
