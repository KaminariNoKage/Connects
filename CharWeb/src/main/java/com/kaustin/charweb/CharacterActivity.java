package com.kaustin.charweb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by kaustin on 12/6/13.
 */
public class CharacterActivity extends Activity {
    //The Character's main page

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_activity);

        Intent intent = getIntent();

        final TextView charName = (TextView) findViewById(R.id.charName);
        /*final TextView tweet = (TextView) findViewById(R.id.feedText);
        final TextView date = (TextView) findViewById(R.id.feedDate);

        username.setText(intent.getStringExtra("username"));
        tweet.setText(intent.getStringExtra("tweet"));*/
        charName.setText(intent.getStringExtra("charName"));

    }
}
