package com.vetdevelopers.vetnetwork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BrowseActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    //private RecyclerView.Adapter adapter;
    private List<ListItemForRecycleView1> listItems;
    MyAdapterForRecycleViewForAll myAdapterForRecycleViewForAll;

    ArrayList<String> arrayListName = new ArrayList<String>();
    ArrayList<String> arrayListPhone = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);


        Intent intent = getIntent();
        arrayListName = intent.getStringArrayListExtra("name");
        arrayListPhone = intent.getStringArrayListExtra("phone");

        recyclerView = (RecyclerView) findViewById(R.id.browse_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        for (int i = 0; i < arrayListName.size(); i++)
        {
            ListItemForRecycleView1 listItem = new ListItemForRecycleView1(arrayListName.get(i), arrayListPhone.get(i));
            listItems.add(listItem);
        }
        myAdapterForRecycleViewForAll = new MyAdapterForRecycleViewForAll(listItems, this);
        recyclerView.setAdapter(myAdapterForRecycleViewForAll);

        myAdapterForRecycleViewForAll.setOnItemClickListener(new MyAdapterForRecycleViewForAll.OnItemClickListener()
        {
            @Override
            public void onItemClick(int position)
            {
                System.out.println("ok");
            }
        });


    }
}

