package com.vetdevelopers.vetnetwork;


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
    public RecyclerView recyclerView, mRecyclerView ;
    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;
    //private RecyclerView.Adapter adapter;
    public List<ListItemForRecycleView1> listItems;
    public MyAdapterForRecycleViewAdmin myAdapterForRecycleViewAdmin;

    public String captureServerVolleyResponse;



    //ArrayList<String> arrayListName1 = new ArrayList<String>();
    //ArrayList<String> arrayListPhone1 = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_for_admin);

        recyclerView = (RecyclerView) findViewById(R.id.browse_recyclerView);

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
                            //callback.onSuccess(response);
                            //Bundle bundle1 = jsonStringToBundle(response);
                            createExampleList(response);
                            buildRecyclerView();
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



    }

    public void getStringResponseFromVolley()
    {
        getString(new VolleyCallback()
        {
            @Override
            public void onSuccess(String result)
            {
                captureServerVolleyResponse = result;
                System.out.println("Volley captured : "+captureServerVolleyResponse);
            }
        });

    }
    /*
    public String getStringResponseFromVolley1()
    {
        final String empty = "";
        getString(new VolleyCallback()
        {
            @Override
            public void onSuccess(String result)
            {
                final String k = result;
                System.out.println("Volley captured : "+captureServerVolleyResponse);
                return k;
            }
        });
        return empty;
    }
*/
    public void getString(final VolleyCallback callback)
    {
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
                            callback.onSuccess(response);
                            //Bundle bundle1 = jsonStringToBundle(response);
                            //createExampleList(response);
                            //buildRecyclerView();
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
    }

    public interface VolleyCallback{
        void onSuccess(String result);
    }

    public void createExampleList(String response)
    {

        /*
        mExampleList = new ArrayList<>();
        mExampleList.add(new ExampleItem(R.drawable.ic_android, "Line 1", "Line 2"));
        mExampleList.add(new ExampleItem(R.drawable.ic_audio, "Line 3", "Line 4"));
        mExampleList.add(new ExampleItem(R.drawable.ic_sun, "Line 5", "Line 6"));
        */
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
            listItems = new ArrayList<>();

            for (int i = 0; i < arrayListName.size(); i++)
            {
                ListItemForRecycleView1 listItem = new ListItemForRecycleView1(
                        arrayListName.get(i), arrayListPhone.get(i));
                listItems.add(listItem);
            }



                                /*
                                myAdapterForRecycleViewAdmin.setOnItemClickListener(new MyAdapterForRecycleViewAdmin.OnItemClickListener()
                                {
                                    @Override
                                    public void onItemClick(int position)
                                    {
                                        System.out.println("ok");
                                    }
                                });
                                */
        }
        catch (Exception e)
        {
            //Toast.makeText(BrowseForAdminActivity.this,"No result found",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void createExampleList()
    {
        getString(new VolleyCallback()
        {
            @Override
            public void onSuccess(String result)
            {
                ArrayList<String> arrayListName = new ArrayList<String>();
                ArrayList<String> arrayListPhone = new ArrayList<String>();

                System.out.println("this is response : " + result);
                try
                {
                    System.out.println("this is response : " + result);
                    JSONArray jsonArray = new JSONArray(result);
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
                    listItems = new ArrayList<>();

                    for (int i = 0; i < arrayListName.size(); i++)
                    {
                        ListItemForRecycleView1 listItem = new ListItemForRecycleView1(
                                arrayListName.get(i), arrayListPhone.get(i));
                        listItems.add(listItem);
                    }



                                /*
                                myAdapterForRecycleViewAdmin.setOnItemClickListener(new MyAdapterForRecycleViewAdmin.OnItemClickListener()
                                {
                                    @Override
                                    public void onItemClick(int position)
                                    {
                                        System.out.println("ok");
                                    }
                                });
                                */
                }
                catch (Exception e)
                {
                    //Toast.makeText(BrowseForAdminActivity.this,"No result found",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        /*
        mExampleList = new ArrayList<>();
        mExampleList.add(new ExampleItem(R.drawable.ic_android, "Line 1", "Line 2"));
        mExampleList.add(new ExampleItem(R.drawable.ic_audio, "Line 3", "Line 4"));
        mExampleList.add(new ExampleItem(R.drawable.ic_sun, "Line 5", "Line 6"));
        */

    }

    public void buildRecyclerView()
    {

        recyclerView = (RecyclerView) findViewById(R.id.browse_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(BrowseForAdminActivity.this));
        //new code - 1
        mLayoutManager = new LinearLayoutManager(BrowseForAdminActivity.this);
        //new code - 1 end
        myAdapterForRecycleViewAdmin = new MyAdapterForRecycleViewAdmin(listItems, BrowseForAdminActivity.this);
        //new code - 2
        mAdapter = myAdapterForRecycleViewAdmin;
        //new code - 2 end
        recyclerView.setAdapter(myAdapterForRecycleViewAdmin);
    }


}

