package com.qandle.splitwise.splitwise;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class TravelExpense extends AppCompatActivity {

    DatabaseHandler db;
    ListView lv;
    EditText des;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_expense);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DatabaseHandler(this);

        lv = (ListView) findViewById(R.id.list_view);
        des = (EditText) findViewById(R.id.e1);

        loadData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivityForResult(new Intent(TravelExpense.this,AddTravel.class),101);
            }
        });

    }

    private void loadData()
    {

        List<Bills> contacts = db.getAllTravels();

        ArrayAdapter listAdapter = new ArrayAdapter<Bills>(this, R.layout.names_row, contacts);
        lv.setAdapter(listAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==101)
        {
            loadData();
        }
    }
}
