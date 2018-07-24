package com.qandle.splitwise.splitwise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AddTravel extends AppCompatActivity
{

    TextView date;
    TextView amount;
    LinearLayout checkboxes;
    DatabaseHandler db;
    List<CheckBox> checkBoxList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_travel);
        db = new DatabaseHandler(this);
        date= (TextView) findViewById(R.id.date);
        amount= (TextView) findViewById(R.id.amount);
        checkboxes = (LinearLayout) findViewById(R.id.checkboxes);

        List<People> contacts = db.getAllContacts();
        checkBoxList = new ArrayList<>();

        for(People c : contacts)
        {
            CheckBox cb = new CheckBox(this);
            cb.setText(c.getName());
            checkboxes.addView(cb);
            checkBoxList.add(cb);
        }

        long curr_date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        String dateString = sdf.format(curr_date);
        date.setText(dateString);

    }

    public void addToDB(View v)
    {
        String names ="";
        for(CheckBox c : checkBoxList)
        {
            names=names+(c.isChecked()?c.getText().toString()+",":"");
        }

        db.addTravels(new Bills(date.getText().toString(),names,amount.getText().toString()));
        setResult(101);
        finish();
    }


}
