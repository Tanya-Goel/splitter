package com.qandle.splitwise.splitwise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddName extends AppCompatActivity {
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_name);
        db = new DatabaseHandler(this);
    }

    public void addToDB(View v)
    {
        String text = ((TextView)findViewById(R.id.name)).getText().toString();
        if(text.length()>0) {
            db.addContact(new People(text));
            setResult(102);
            finish();
        }
        else
        {
            Toast.makeText(this, "Empty name", Toast.LENGTH_SHORT).show();
        }
    }
}
