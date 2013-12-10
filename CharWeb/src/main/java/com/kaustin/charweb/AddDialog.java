package com.kaustin.charweb;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by kaustin on 12/6/13.
 */
public class AddDialog extends AlertDialog {
    Context context;
    String username;
    User curUser;

    public AddDialog(Context context){
        super(context);
        setContentView(R.layout.add_dialog);
        this.context = context;
    }

    public void onCreate(Bundle savedInstanceState){
        Button submit = (Button)findViewById(R.id.submitRel);
        Button cancel = (Button)findViewById(R.id.cancelRel);

        //Adding the relationship to the app
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }});

        //Do nothing, just close the Dialog
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }});
    }
}