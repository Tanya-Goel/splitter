package com.qandle.splitwise.splitwise;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class TravelExpense extends AppCompatActivity {

    DatabaseHandler db;
    ListView lv;
    EditText des;
    Button but1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_expense);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DatabaseHandler(this);

        lv = (ListView) findViewById(R.id.list_view);
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });
        des = (EditText) findViewById(R.id.e1);
        loadData();

        but1=findViewById(R.id.button1);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent but1=new Intent(TravelExpense.this,AllNames.class);
                startActivity(but1);

            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent but2=new Intent(TravelExpense.this,FinalTransaction.class);
                startActivity(but2);

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
