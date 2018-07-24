package com.qandle.splitwise.splitwise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.qandle.splitwise.splitwise.Distribute.minCashFlow;

public class AllNames extends AppCompatActivity {
    DatabaseHandler db;
    ListView lv;
    private ArrayAdapter listAdapter;
    private List<People> contacts;
    private AlertDialog.Builder builder;
    LinearLayout checkboxes;
    List<CheckBox> checkBoxList;
    EditText e2,e1;
    Button b1,b2;
    private AlertDialog alertDialog;
    People paidBy;
    HashMap<Integer,String> contacts_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHandler(this);
        b1=findViewById(R.id.paid_by);
        b2=findViewById(R.id.split);
        lv = new ListView(this);
        contacts = db.getAllContacts();
        initialiseHashMap();
        listAdapter = new ArrayAdapter<People>(this, R.layout.names_row, contacts);
        lv.setAdapter(listAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                paidBy = contacts.get(i);
               b1.setText(contacts.get(i).getName());
               if(alertDialog!=null)
                alertDialog.dismiss();

            }
        });
        e2=findViewById(R.id.e2);
        e1=findViewById(R.id.e1);
        findViewById(R.id.paid_by).setOnClickListener(new View.OnClickListener() {

//            private AlertDialog alertDialog;


            @Override
            public void onClick(View view) {
                updateAll();
                if(lv.getParent()!=null)
                {
                    ((ViewGroup)lv.getParent()).removeAllViews();
                }
                builder = new AlertDialog.Builder(AllNames.this);
                builder.setView(lv);
                alertDialog = builder.create();

                alertDialog.show();

            }
        });
        checkboxes = (LinearLayout) findViewById(R.id.checkboxes);
        updateAll();

        findViewById(R.id.split).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showData();
            }
        });


    }

    private void updateAll()
    {
        contacts = db.getAllContacts();
        initialiseHashMap();
        listAdapter = new ArrayAdapter<People>(this, R.layout.names_row, contacts);
        lv.setAdapter(listAdapter);
//        List<People> contacts = db.getAllContacts();
        checkBoxList = new ArrayList<>();
        checkboxes.removeAllViews();
        for(People c : contacts)
        {
            CheckBox cb = new CheckBox(this);
            cb.setText(c.getName());
            checkboxes.addView(cb);
            checkBoxList.add(cb);
        }
    }

    public void addNewName(View v)
    {
        startActivityForResult(new Intent(this,AddName.class),102);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 102)
        {
            updateAll();
        }
    }
    public void addToDB()
    {
        if(paidBy == null)
        {
            Toast.makeText(this,"Please select who paid this bill",Toast.LENGTH_SHORT).show();
            return;
        }
//        String names ="";
//        for (int i = 0; i <checkBoxList.size() ; i++) {
//            CheckBox c = checkBoxList.get(i);
//            if (i==0){
//                names=names+(c.isChecked()?c.getText().toString():"");
//
//            }else {
//                names=names+(c.isChecked()?","+c.getText().toString():"");
//            }
//        }
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        Calendar c = Calendar.getInstance();
//        String date = sdf.format(c.getTime());
        Calendar cal = Calendar.getInstance();

        int amount = Integer.parseInt(e2.getText().toString());

        long id = db.addTravels(new Bills(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault()).format(cal.getTime()),e1.getText().toString(),e2.getText().toString()));

        ArrayList<People> selected = new ArrayList<>();
        for (int i = 0; i <checkBoxList.size() ; i++) {
            CheckBox c = checkBoxList.get(i);
            if(c.isChecked())
            {
                selected.add(contacts.get(i));
            }
        }
        db.paid_by_data(paidBy.getID(), amount, id);


        //create a method to store paid by data if you my_id

//db.paid_by_data()
        int per_person=0;
        try {
             per_person = amount / selected.size();

        }catch (Exception e){

        }

        for (int j =0; j< selected.size();j++)
        {
            //Add this
            int selected_id = selected.get(j).getID();
            db.addper_person(selected_id, per_person, id);
            //Create a method to store per person amount in sharedBy tables
        }
        startActivityForResult(new Intent(AllNames.this,TravelExpense.class),101);

        //setResult(101);
       // finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_main2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        addToDB();

        return super.onOptionsItemSelected(item);
    }



    private void showData() {
        HashMap<String,AmountTransaction> data = db.query();

//        Iterator it = data.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry pair = (Map.Entry)it.next();
////            System.out.println(pair.getKey() + " = " + pair.getValue());
//            AmountTransaction transaction = (AmountTransaction) pair.getValue();
//            Log.i("Myapp","value "+transaction.amount);
//            it.remove(); // avoids a ConcurrentModificationException
//        }


        List<AmountTransaction> values = new ArrayList<AmountTransaction>(data.values());

        int n = contacts.size();
        double amount[][] = new double[n][n];

        for (AmountTransaction t : values)
        {
            amount[t.payee-1][t.receiver-1] = t.getAmount();
        }
        ArrayList<AmountTransaction> finalList = new ArrayList<>();
        minCashFlow(amount,n,finalList);

        for (AmountTransaction t : finalList)
        {

            Log.i("Myapp",""+contacts_name.get(t.payee) + " has to give Rs "+t.amount+"/- to "+contacts_name.get(t.receiver));
        }



//        for (int i=0;i<n;i++)
//        {
//            for(int j=0;j<n;j++)
//            {
//                Log.i("MyApp","i->"+i+",j->"+j+"[i][j]->"+amount[i][j]);
//            }
//        }


    }

    public void initialiseHashMap()
    {
        contacts_name = new HashMap<>();
        for (People p : contacts)
        {
            contacts_name.put(p._id,p._name);
        }
    }

}
