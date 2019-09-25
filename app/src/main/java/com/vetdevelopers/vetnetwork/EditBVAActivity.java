package com.vetdevelopers.vetnetwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
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
import java.util.Map;

public class EditBVAActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button president, sr_vice_president, vice_president, secretary_general, joint_secretary,
            treasurer, org_secretary, office_secretary, press_secretary, intAffair_secretary,
            sports_secretary, social_secretary, members;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bva);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(this);

        president = findViewById(R.id.BVA_PresidentButton);
        sr_vice_president = findViewById(R.id.BVA_srvpButton);
        vice_president = findViewById(R.id.BVA_vpButton);
        secretary_general = findViewById(R.id.BVA_sgButton);
        joint_secretary = findViewById(R.id.BVA_jsButton);
        treasurer = findViewById(R.id.BVA_treasurerButton);
        org_secretary = findViewById(R.id.BVA_orgSecButton);
        office_secretary = findViewById(R.id.BVA_officeSecButton);
        press_secretary = findViewById(R.id.BVA_pressSecButton);
        intAffair_secretary = findViewById(R.id.BVA_intSecButton);
        sports_secretary = findViewById(R.id.BVA_sportSecButton);
        social_secretary = findViewById(R.id.BVA_socSecButton);
        members = findViewById(R.id.BVA_memberButton);

        //president
        president.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Please Wait");
                progressDialog.setMessage("Getting data from server");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.SEARCH_BVA_DESIGNATION,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                progressDialog.dismiss();
                                if (response.contains("Connection failed!"))
                                {
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    createExampleList(response);
                                }
                            }
                        }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressDialog.dismiss();
                        if (error instanceof TimeoutError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(EditBVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(EditBVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("designation","President");
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("User-Agent", "VetNetwork");  ////security purpose
                        return headers;
                    }
                };

                MySingleton.getInstance(EditBVAActivity.this).addToRequestQueue(stringRequest);
            }
        });


        //sr_vice_president
        sr_vice_president.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Please Wait");
                progressDialog.setMessage("Getting data from server");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.SEARCH_BVA_DESIGNATION,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                progressDialog.dismiss();
                                if (response.contains("Connection failed!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    //callback.onSuccess(response);
                                    //Bundle bundle1 = jsonStringToBundle(response);
                                    createExampleList(response);
                                    //buildRecyclerView();
                                }
                            }
                        }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressDialog.dismiss();
                        if (error instanceof TimeoutError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(EditBVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(EditBVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("designation","Sr. Vice President");
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("User-Agent", "VetNetwork");  ////security purpose
                        return headers;
                    }
                };

                MySingleton.getInstance(EditBVAActivity.this).addToRequestQueue(stringRequest);
            }
        });

        //vice_president
        vice_president.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Please Wait");
                progressDialog.setMessage("Getting data from server");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.SEARCH_BVA_DESIGNATION,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                progressDialog.dismiss();
                                if (response.contains("Connection failed!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    //callback.onSuccess(response);
                                    //Bundle bundle1 = jsonStringToBundle(response);
                                    createExampleList(response);
                                    //buildRecyclerView();
                                }
                            }
                        }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressDialog.dismiss();
                        if (error instanceof TimeoutError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(EditBVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(EditBVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("designation","Vice President");
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("User-Agent", "VetNetwork");  ////security purpose
                        return headers;
                    }
                };

                MySingleton.getInstance(EditBVAActivity.this).addToRequestQueue(stringRequest);
            }
        });

        //secretary_general
        secretary_general.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Please Wait");
                progressDialog.setMessage("Getting data from server");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.SEARCH_BVA_DESIGNATION,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                progressDialog.dismiss();
                                if (response.contains("Connection failed!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    //callback.onSuccess(response);
                                    //Bundle bundle1 = jsonStringToBundle(response);
                                    createExampleList(response);
                                    //buildRecyclerView();
                                }
                            }
                        }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressDialog.dismiss();
                        if (error instanceof TimeoutError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(EditBVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(EditBVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("designation","Secretary General");
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("User-Agent", "VetNetwork");  ////security purpose
                        return headers;
                    }
                };

                MySingleton.getInstance(EditBVAActivity.this).addToRequestQueue(stringRequest);
            }
        });

        //joint_secretary
        joint_secretary.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Please Wait");
                progressDialog.setMessage("Getting data from server");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.SEARCH_BVA_DESIGNATION,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                progressDialog.dismiss();
                                if (response.contains("Connection failed!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    //callback.onSuccess(response);
                                    //Bundle bundle1 = jsonStringToBundle(response);
                                    createExampleList(response);
                                    //buildRecyclerView();
                                }
                            }
                        }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressDialog.dismiss();
                        if (error instanceof TimeoutError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(EditBVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(EditBVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("designation","Joint Secretary");
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("User-Agent", "VetNetwork");  ////security purpose
                        return headers;
                    }
                };

                MySingleton.getInstance(EditBVAActivity.this).addToRequestQueue(stringRequest);
            }
        });

        //treasurer
        treasurer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Please Wait");
                progressDialog.setMessage("Getting data from server");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.SEARCH_BVA_DESIGNATION,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                progressDialog.dismiss();
                                if (response.contains("Connection failed!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    //callback.onSuccess(response);
                                    //Bundle bundle1 = jsonStringToBundle(response);
                                    createExampleList(response);
                                    //buildRecyclerView();
                                }
                            }
                        }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressDialog.dismiss();
                        if (error instanceof TimeoutError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(EditBVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(EditBVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("designation","Treasurer");
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("User-Agent", "VetNetwork");  ////security purpose
                        return headers;
                    }
                };

                MySingleton.getInstance(EditBVAActivity.this).addToRequestQueue(stringRequest);
            }
        });

        //org_secretary
        org_secretary.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Please Wait");
                progressDialog.setMessage("Getting data from server");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.SEARCH_BVA_DESIGNATION,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                progressDialog.dismiss();
                                if (response.contains("Connection failed!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    //callback.onSuccess(response);
                                    //Bundle bundle1 = jsonStringToBundle(response);
                                    createExampleList(response);
                                    //buildRecyclerView();
                                }
                            }
                        }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressDialog.dismiss();
                        if (error instanceof TimeoutError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(EditBVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(EditBVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("designation","Organizing Secretary");
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("User-Agent", "VetNetwork");  ////security purpose
                        return headers;
                    }
                };

                MySingleton.getInstance(EditBVAActivity.this).addToRequestQueue(stringRequest);
            }
        });


        //office_secretary
        office_secretary.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Please Wait");
                progressDialog.setMessage("Getting data from server");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.SEARCH_BVA_DESIGNATION,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                progressDialog.dismiss();
                                if (response.contains("Connection failed!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    //callback.onSuccess(response);
                                    //Bundle bundle1 = jsonStringToBundle(response);
                                    createExampleList(response);
                                    //buildRecyclerView();
                                }
                            }
                        }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressDialog.dismiss();
                        if (error instanceof TimeoutError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(EditBVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(EditBVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("designation","Office Secretary");
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("User-Agent", "VetNetwork");  ////security purpose
                        return headers;
                    }
                };

                MySingleton.getInstance(EditBVAActivity.this).addToRequestQueue(stringRequest);
            }
        });

        //press_secretary
        press_secretary.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Please Wait");
                progressDialog.setMessage("Getting data from server");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.SEARCH_BVA_DESIGNATION,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                progressDialog.dismiss();
                                if (response.contains("Connection failed!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    //callback.onSuccess(response);
                                    //Bundle bundle1 = jsonStringToBundle(response);
                                    createExampleList(response);
                                    //buildRecyclerView();
                                }
                            }
                        }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressDialog.dismiss();
                        if (error instanceof TimeoutError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(EditBVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(EditBVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("designation","Press and Publication Secretary");
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("User-Agent", "VetNetwork");  ////security purpose
                        return headers;
                    }
                };

                MySingleton.getInstance(EditBVAActivity.this).addToRequestQueue(stringRequest);
            }
        });

        //intAffair_secretary
        intAffair_secretary.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Please Wait");
                progressDialog.setMessage("Getting data from server");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.SEARCH_BVA_DESIGNATION,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                progressDialog.dismiss();
                                if (response.contains("Connection failed!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    //callback.onSuccess(response);
                                    //Bundle bundle1 = jsonStringToBundle(response);
                                    createExampleList(response);
                                    //buildRecyclerView();
                                }
                            }
                        }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressDialog.dismiss();
                        if (error instanceof TimeoutError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(EditBVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(EditBVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("designation","International Affairs Secretary");
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("User-Agent", "VetNetwork");  ////security purpose
                        return headers;
                    }
                };

                MySingleton.getInstance(EditBVAActivity.this).addToRequestQueue(stringRequest);
            }
        });

        //sports_secretary
        sports_secretary.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Please Wait");
                progressDialog.setMessage("Getting data from server");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.SEARCH_BVA_DESIGNATION,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                progressDialog.dismiss();
                                if (response.contains("Connection failed!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    //callback.onSuccess(response);
                                    //Bundle bundle1 = jsonStringToBundle(response);
                                    createExampleList(response);
                                    //buildRecyclerView();
                                }
                            }
                        }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressDialog.dismiss();
                        if (error instanceof TimeoutError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(EditBVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(EditBVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("designation","Sports and Cultural Secretary");
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("User-Agent", "VetNetwork");  ////security purpose
                        return headers;
                    }
                };

                MySingleton.getInstance(EditBVAActivity.this).addToRequestQueue(stringRequest);
            }
        });

        //social_secretary
        social_secretary.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Please Wait");
                progressDialog.setMessage("Getting data from server");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.SEARCH_BVA_DESIGNATION,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                progressDialog.dismiss();
                                if (response.contains("Connection failed!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    //callback.onSuccess(response);
                                    //Bundle bundle1 = jsonStringToBundle(response);
                                    createExampleList(response);
                                    //buildRecyclerView();
                                }
                            }
                        }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressDialog.dismiss();
                        if (error instanceof TimeoutError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(EditBVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(EditBVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("designation","Social Welfare Secretary");
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("User-Agent", "VetNetwork");  ////security purpose
                        return headers;
                    }
                };

                MySingleton.getInstance(EditBVAActivity.this).addToRequestQueue(stringRequest);
            }
        });


        //members
        members.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Please Wait");
                progressDialog.setMessage("Getting data from server");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.SEARCH_BVA_DESIGNATION,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                progressDialog.dismiss();
                                if (response.contains("Connection failed!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(EditBVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    //callback.onSuccess(response);
                                    //Bundle bundle1 = jsonStringToBundle(response);
                                    createExampleList(response);
                                    //buildRecyclerView();
                                }
                            }
                        }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressDialog.dismiss();
                        if (error instanceof TimeoutError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(EditBVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(EditBVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(EditBVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("designation","Member");
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("User-Agent", "VetNetwork");  ////security purpose
                        return headers;
                    }
                };

                MySingleton.getInstance(EditBVAActivity.this).addToRequestQueue(stringRequest);
            }
        });

        NavigationView navigationView = findViewById(R.id.edit_bva_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void createExampleList(String response)
    {
        ArrayList<String> arrayListId = new ArrayList<String>();
        ArrayList<String> arrayListName = new ArrayList<String>();
        ArrayList<String> arrayListPhone = new ArrayList<String>();
        ArrayList<String> arrayListEmail = new ArrayList<String>();
        ArrayList<String> arrayListDesignation = new ArrayList<String>();

        //System.out.println("this is response : " + response);
        try
        {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String outputId = jsonObject.getString("id");
                String outputName = jsonObject.getString("name");
                String outputPhone = jsonObject.getString("phone");
                String outputEmail = jsonObject.getString("email");
                String outputDesignation = jsonObject.getString("designation");

                arrayListId.add(outputId);
                arrayListName.add(outputName);
                arrayListPhone.add(outputPhone);
                arrayListEmail.add(outputEmail);
                arrayListDesignation.add(outputDesignation);
            }

            Intent intent = new Intent(EditBVAActivity.this, EditBVAmemberRecyclerViewActivity.class);
            intent.putStringArrayListExtra("id", arrayListId);
            intent.putStringArrayListExtra("name", arrayListName);
            intent.putStringArrayListExtra("phone", arrayListPhone);
            intent.putStringArrayListExtra("email", arrayListEmail);
            intent.putStringArrayListExtra("designation", arrayListDesignation);
            startActivity(intent);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

        if (id == R.id.nav_adminProfile)
        {
            Intent adminProfileIntent = new Intent(EditBVAActivity.this, AdminProfileActivity.class);
            startActivity(adminProfileIntent);
        }
        else if (id == R.id.nav_editProfile)
        {
            Intent editProfileIntent = new Intent(EditBVAActivity.this, EditProfileAdminActivity.class);
            startActivity(editProfileIntent);
        }
        else if (id == R.id.nav_changePassword)
        {
            Intent changePasswordIntent = new Intent(EditBVAActivity.this, ChangePasswordActivity.class);
            startActivity(changePasswordIntent);
        }
        else if (id == R.id.nav_logout)
        {
            //stop auto login
            getApplicationContext().getSharedPreferences(PREF_NAME, 0).edit().clear().commit();
            //exit intent - clear previous flags of intent
            logoutUser();
        }
        else if (id == R.id.nav_deleteAccount)
        {

            Intent deleteAccountAdminIntent = new Intent(EditBVAActivity.this, DeleteAccountAdminActivity.class);
            startActivity(deleteAccountAdminIntent);

        }
        else if (id == R.id.nav_adminPanel)
        {
            Intent adminControlPanel = new Intent(EditBVAActivity.this, AdminPanelActivity.class);
            startActivity(adminControlPanel);
        }
        else if (id == R.id.nav_reportActivity)
        {
            Intent reportForAdminIntent = new Intent(EditBVAActivity.this, ReportForAdminActivity.class);
            startActivity(reportForAdminIntent);
        }
        else if (id == R.id.nav_aboutUs)
        {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutUser()
    {
        Intent startPageIntent = new Intent(EditBVAActivity.this, StartPageActivity.class);
        startPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startPageIntent);
        finish();
    }
}
