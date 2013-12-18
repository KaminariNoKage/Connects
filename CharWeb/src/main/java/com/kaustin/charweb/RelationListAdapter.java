package com.kaustin.charweb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kaustin on 12/15/13.
 */
public class RelationListAdapter extends ArrayAdapter<Relation> {
    private final Context context;
    private final List<Relation> relations;

    public RelationListAdapter(Context context, List<Relation> relations){
        super(context, R.layout.relation_item, relations);
        this.context = context;
        this.relations = relations;
    }

    private class ItemHolder {
        TextView relName;
        TextView oneWay;
        //TextView twoWay;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ItemHolder holder;
        View connectionRow = convertView;

        if(connectionRow == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            connectionRow = inflater.inflate(R.layout.relation_item, parent, false);
            holder = new ItemHolder();
            holder.relName = (TextView) connectionRow.findViewById(R.id.relationName);
            holder.oneWay = (TextView) connectionRow.findViewById(R.id.oneWayRel);
            //holder.twoWay = (TextView) connectionRow.findViewById(R.id.twoWayRel);

            connectionRow.setTag(holder);
        } else {
            holder = (ItemHolder) connectionRow.getTag();
        }

        Relation relation = relations.get(position);

        holder.relName.setText(relation.charName);
        holder.oneWay.setText(relation.relOneWay);
        //holder.twoWay.setText(twoWayRel);

        return connectionRow;
    }
}
