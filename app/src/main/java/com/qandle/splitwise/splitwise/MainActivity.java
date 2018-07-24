package com.qandle.splitwise.splitwise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.qandle.splitwise.splitwise.Distribute.minCashFlow;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_name);
// graph[i][j] indicates the amount
        // that person i needs to pay person j
        int graph[][] = {
                {0, 1000, 1000,1000},
                {1000, 0, 1000,1000},
                {0, 0, 0,0},
                {0,0,500,0}};

        // Print the solution
//        minCashFlow(graph,4);
    }
}
