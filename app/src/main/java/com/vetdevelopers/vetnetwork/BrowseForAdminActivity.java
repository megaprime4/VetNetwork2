package com.vetdevelopers.vetnetwork;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class BrowseForAdminActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    //private RecyclerView.Adapter adapter;
    private List<ListItemForRecycleView1> listItems;
    MyAdapterForRecycleViewAdmin myAdapterForRecycleViewAdmin;

    //ArrayList<String> arrayListName1 = new ArrayList<String>();
    //ArrayList<String> arrayListPhone1 = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_for_admin);
        
        //lets test here

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.PENDING_USER_REQUEST_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        if (response.contains("Connection failed!"))
                        {
                            //popupTextView.setText(response);
                            //mDialog.show();
                            Toast.makeText(BrowseForAdminActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.contains("Invalid platform!"))
                        {
                            //popupTextView.setText(response);
                            //mDialog.show();
                            Toast.makeText(BrowseForAdminActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.contains("Improper request method!"))
                        {
                            //popupTextView.setText(response);
                            //mDialog.show();
                            Toast.makeText(BrowseForAdminActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.contains("SQL (select) query error!"))
                        {
                            //popupTextView.setText(response);
                            //mDialog.show();
                            Toast.makeText(BrowseForAdminActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.contains("No user request found!"))
                        {
                            Toast.makeText(BrowseForAdminActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            //Bundle bundle1 = jsonStringToBundle(response);

                            ArrayList<String> arrayListName = new ArrayList<String>();
                            ArrayList<String> arrayListPhone = new ArrayList<String>();

                            System.out.println("this is response : " + response);
                            try
                            {
                                System.out.println("this is response : " + response);
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++)
                                {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String outputName = jsonObject.getString("name");
                                    String outputPhone = jsonObject.getString("phone");

                                    arrayListName.add(outputName);
                                    arrayListPhone.add(outputPhone);
                                }
                                //arrayListName1=arrayListName;
                                //arrayListPhone1=arrayListPhone;
                                recyclerView = (RecyclerView) findViewById(R.id.browse_recyclerView);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(BrowseForAdminActivity.this));

                                listItems = new ArrayList<>();

                                for (int i = 0; i < arrayListName.size(); i++)
                                {
                                    ListItemForRecycleView1 listItem = new ListItemForRecycleView1(
                                            arrayListName.get(i), arrayListPhone.get(i));
                                    listItems.add(listItem);
                                }
                                myAdapterForRecycleViewAdmin = new MyAdapterForRecycleViewAdmin(listItems, BrowseForAdminActivity.this);
                                recyclerView.setAdapter(myAdapterForRecycleViewAdmin);

                                myAdapterForRecycleViewAdmin.setOnItemClickListener(new MyAdapterForRecycleViewAdmin.OnItemClickListener()
                                {
                                    @Override
                                    public void onItemClick(int position)
                                    {
                                        System.out.println("ok");
                                    }
                                });
                            }
                            catch (Exception e)
                            {
                                //Toast.makeText(BrowseForAdminActivity.this,"No result found",Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                if (error instanceof TimeoutError)
                {
                    Toast.makeText(BrowseForAdminActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NoConnectionError)
                {
                    Toast.makeText(BrowseForAdminActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof AuthFailureError)
                {
                    Toast.makeText(BrowseForAdminActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NetworkError)
                {
                    Toast.makeText(BrowseForAdminActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ServerError)
                {
                    Toast.makeText(BrowseForAdminActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ParseError)
                {
                    Toast.makeText(BrowseForAdminActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("User-Agent", "VetNetwork");  ////security purpose
                return headers;
            }
        };

        MySingleton.getInstance(BrowseForAdminActivity.this).addToRequestQueue(stringRequest);







        //test ends here 

        /*
        Intent intent = getIntent();
        arrayListName = intent.getStringArrayListExtra("name");
        arrayListPhone = intent.getStringArrayListExtra("phone");
        */

/*
        System.out.println("name : "+arrayListName1);
        System.out.println("phone : "+arrayListPhone1);
        */
        





    }
}

