package com.kaustin.charweb;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by kaustin on 12/18/13.
 */
public class HowToDialog extends AlertDialog {

    Context context;
    String username;
    User curUser;

    public HowToDialog(Context context){
        super(context);
        setContentView(R.layout.howto_dialog);
        this.context = context;
    }

    public void onCreate(Bundle savedInstanceState){

        //Dismiss the How To
        Button dismiss = (Button)findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }});
    }
}
