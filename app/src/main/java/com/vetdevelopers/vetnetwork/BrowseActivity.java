package com.vetdevelopers.vetnetwork;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrowseActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    //private RecyclerView.Adapter adapter;
    private List<ListItemForRecycleView1> listItems;
    MyAdapterForRecycleView2 myAdapterForRecycleView2;

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
            ListItemForRecycleView1 listItem = new ListItemForRecycleView1(
                    arrayListName.get(i), arrayListPhone.get(i));
            listItems.add(listItem);
        }
        myAdapterForRecycleView2 = new MyAdapterForRecycleView2(listItems, this);
        recyclerView.setAdapter(myAdapterForRecycleView2);

        myAdapterForRecycleView2.setOnItemClickListener(new MyAdapterForRecycleView2.OnItemClickListener()
        {
            @Override
            public void onItemClick(int position)
            {
                System.out.println("ok");
            }
        });


    }
}

