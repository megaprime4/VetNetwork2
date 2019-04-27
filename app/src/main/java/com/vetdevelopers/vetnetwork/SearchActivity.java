package com.vetdevelopers.vetnetwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText name, phone;
    private Spinner postingAreaSpinner, districtSpinner, divisionSpinner;
    private Button searchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        name = (EditText) findViewById(R.id.search_name);
        phone = (EditText) findViewById(R.id.search_phone);

        postingAreaSpinner = (Spinner) findViewById(R.id.search_postingArea_spinner);
        districtSpinner = (Spinner) findViewById(R.id.search_district_spinner);
        divisionSpinner = (Spinner) findViewById(R.id.search_division_spinner);

        searchButton = (Button) findViewById(R.id.search_searchButton);

        setPostingAreaSpinner();

        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                final String Name = name.getText().toString().trim();
                final String Phone = phone.getText().toString().trim();
                final String PostingArea = postingAreaSpinner.getSelectedItem().toString();
                final String District = districtSpinner.getSelectedItem().toString();
                final String Division = divisionSpinner.getSelectedItem().toString();

                System.out.println("---------------------------------------------------" + Name);
                System.out.println("---------------------------------------------------" + Phone);
                System.out.println("---------------------------------------------------" + PostingArea);
                System.out.println("---------------------------------------------------" + District);
                System.out.println("---------------------------------------------------" + Division);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.SEARCH_URL,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                if (response.contains("Connection failed!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(SearchActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Please check your ID & Password!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(SearchActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(SearchActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(SearchActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("sql error"))
                                {
                                    Toast.makeText(SearchActivity.this, response, Toast.LENGTH_SHORT).show();
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

                                        Intent browseIntent = new Intent(SearchActivity.this, BrowseActivity.class);
                                        browseIntent.putStringArrayListExtra("name", arrayListName);
                                        browseIntent.putStringArrayListExtra("phone", arrayListPhone);
                                        startActivity(browseIntent);

                                    }
                                    catch (Exception e)
                                    {
                                        //Toast.makeText(SearchActivity.this,"No result found",Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(SearchActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(SearchActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(SearchActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(SearchActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(SearchActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(SearchActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("name", Name);
                        params.put("phone", Phone);
                        params.put("postingArea", PostingArea);
                        params.put("district", District);
                        params.put("division", Division);
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

                MySingleton.getInstance(SearchActivity.this).addToRequestQueue(stringRequest);

            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setPostingAreaSpinner()
    {
        //List<String> postingAreaList = new ArrayList<String>();


        StringRequest stringRequest = new StringRequest
                (Request.Method.POST, ServerConstants.DOCTOR_POSTING_AREA,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                if (response.contains("Connection failed!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(SearchActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Please check your ID & Password!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(SearchActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(SearchActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(SearchActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("sql error"))
                                {
                                    Toast.makeText(SearchActivity.this, response, Toast.LENGTH_SHORT).show();
                                }

                                //server data  retrieve code below...
                                else
                                {
                                    Set<String> set1 = new HashSet<String>();
                                    //set1.add("Select");
                                    try
                                    {
                                        JSONArray jsonArray = new JSONArray(response);
                                        for (int i = 0; i < jsonArray.length(); i++)
                                        {
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            String outputFromServer = jsonObject.getString("posting_area");

                                            set1.add(outputFromServer);
                                        }
                                        List<String> postingAreaList = new ArrayList<String>();
                                        postingAreaList.add(0, "Select");
                                        postingAreaList.addAll(set1);

                                        ArrayAdapter<String> postingAreaArrayAdapter = new ArrayAdapter<String>(SearchActivity.this,
                                                android.R.layout.simple_spinner_item, postingAreaList);
                                        postingAreaArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        postingAreaSpinner.setAdapter(postingAreaArrayAdapter);
                                    }
                                    catch (Exception e)
                                    {
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
                            Toast.makeText(SearchActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(SearchActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(SearchActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(SearchActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(SearchActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(SearchActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
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

        MySingleton.getInstance(SearchActivity.this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_reportActivity) {

            Intent reportForBrowsersIntent = new Intent(SearchActivity.this, ReportForBrowsersActivity.class);
            startActivity(reportForBrowsersIntent);

        } else if (id == R.id.nav_aboutUs) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
