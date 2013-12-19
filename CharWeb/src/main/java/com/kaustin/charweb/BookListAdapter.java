package com.kaustin.charweb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaustin on 12/18/13.
 */
public class BookListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> books;

    public BookListAdapter(Context context, List<String> books){
        super(context, R.layout.book_item, books);
        this.context = context;
        this.books = books;
    }

    private class ConnectionItemHolder{
        TextView bookName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ConnectionItemHolder holder;
        View connectionRow = convertView;

        if(connectionRow == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            connectionRow = inflater.inflate(R.layout.book_item, parent, false);
            holder = new ConnectionItemHolder();
            holder.bookName = (TextView) connectionRow.findViewById(R.id.booktitle);

            connectionRow.setTag(holder);
        } else {
            holder = (ConnectionItemHolder) connectionRow.getTag();
        }

        String item = books.get(position);

        holder.bookName.setText(item);

        return connectionRow;
    }
}
