package com.vetdevelopers.vetnetwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DisplayBVAmemberRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    //private RecyclerView.Adapter adapter;
    private List<DisplayBVAmemberModel> resultList;
    DisplayBVAmemberModelAdapter displayBVAmemberModelAdapter;

    ArrayList<String> arrayListName = new ArrayList<String>();
    ArrayList<String> arrayListPhone = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_bvamember_recycler_view);

        Intent intent = getIntent();
        arrayListName = intent.getStringArrayListExtra("name");
        arrayListPhone = intent.getStringArrayListExtra("phone");

        recyclerView = findViewById(R.id.rvResult);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        resultList = new ArrayList<>();

        for (int i = 0; i < arrayListName.size(); i++)
        {
            DisplayBVAmemberModel listItem = new DisplayBVAmemberModel(arrayListName.get(i), arrayListPhone.get(i));
            resultList.add(listItem);
        }
        displayBVAmemberModelAdapter = new DisplayBVAmemberModelAdapter(resultList, this);
        recyclerView.setAdapter(displayBVAmemberModelAdapter);

        displayBVAmemberModelAdapter.setOnItemClickListener(new DisplayBVAmemberModelAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(int position)
            {
                System.out.println("ok");
            }
        });

    }

}
