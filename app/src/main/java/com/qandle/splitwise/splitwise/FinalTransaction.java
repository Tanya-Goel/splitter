package com.qandle.splitwise.splitwise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.qandle.splitwise.splitwise.Distribute.minCashFlow;

public class FinalTransaction extends AppCompatActivity {

    List<People> contacts;
    DatabaseHandler db;
    HashMap<Integer,String> contacts_name;
    TextView TV;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_transaction);
        db = new DatabaseHandler(this);
TV=findViewById(R.id.tv1);
        contacts = db.getAllContacts();
        initialiseHashMap();
        showData();



    }


    public void initialiseHashMap()
    {
        contacts_name = new HashMap<>();
        for (People p : contacts)
        {
            contacts_name.put(p._id,p._name);
        }
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
        String s="";


        for (AmountTransaction t : finalList)
        {

            s=s+""+contacts_name.get(t.payee) + " has to give Rs "+t.amount+"/- to "+contacts_name.get(t.receiver)+"\n";
            Log.i("Myapp",""+contacts_name.get(t.payee) + " has to give Rs "+t.amount+"/- to "+contacts_name.get(t.receiver));
        }

        TV.setText(s);


//        for (int i=0;i<n;i++)
//        {
//            for(int j=0;j<n;j++)
//            {
//                Log.i("MyApp","i->"+i+",j->"+j+"[i][j]->"+amount[i][j]);
//            }
//        }


    }

}
