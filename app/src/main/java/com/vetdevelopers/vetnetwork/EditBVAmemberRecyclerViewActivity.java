package com.vetdevelopers.vetnetwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EditBVAmemberRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    //private RecyclerView.Adapter adapter;
    private List<EditBVAmemberModel> resultList;
    EditBVAmemberModelAdapter editBVAmemberModelAdapter;

    ArrayList<String> arrayListId = new ArrayList<String>();
    ArrayList<String> arrayListName = new ArrayList<String>();
    ArrayList<String> arrayListPhone = new ArrayList<String>();
    ArrayList<String> arrayListEmail = new ArrayList<String>();
    ArrayList<String> arrayListDesignation = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bvamember_recycler_view);

        Intent intent = getIntent();
        arrayListId = intent.getStringArrayListExtra("id");
        arrayListName = intent.getStringArrayListExtra("name");
        arrayListPhone = intent.getStringArrayListExtra("phone");
        arrayListEmail = intent.getStringArrayListExtra("email");
        arrayListDesignation = intent.getStringArrayListExtra("designation");

        recyclerView = findViewById(R.id.rvResult);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        resultList = new ArrayList<>();

        for (int i = 0; i < arrayListName.size(); i++)
        {
            EditBVAmemberModel listItem = new EditBVAmemberModel(
                    arrayListId.get(i),
                    arrayListName.get(i),
                    arrayListPhone.get(i),
                    arrayListEmail.get(i),
                    arrayListDesignation.get(i)
            );
            resultList.add(listItem);
        }
        editBVAmemberModelAdapter = new EditBVAmemberModelAdapter(resultList, this);
        recyclerView.setAdapter(editBVAmemberModelAdapter);

        editBVAmemberModelAdapter.setOnItemClickListener(new EditBVAmemberModelAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(int position)
            {
                System.out.println("ok");
            }
        });
    }

}
