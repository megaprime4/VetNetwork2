package com.vetdevelopers.vetnetwork;

import android.app.ProgressDialog;
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

public class BVAActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button BVA_PresidentButton,BVA_srvpButton,BVA_vpButton,BVA_sgButton,BVA_jsButton;
    private Button BVA_treasurerButton,BVA_orgSecButton,BVA_officeSecButton,BVA_pubSecButton;
    private Button BVA_intSecButton,BVA_sportSecButton,BVA_socSecButton,BVA_memberButton;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bva);

        progressDialog = new ProgressDialog(this);

        BVA_PresidentButton = (Button)findViewById(R.id.BVA_PresidentButton) ;
        BVA_srvpButton = (Button)findViewById(R.id.BVA_srvpButton) ;
        BVA_vpButton = (Button)findViewById(R.id.BVA_vpButton) ;
        BVA_sgButton = (Button)findViewById(R.id.BVA_sgButton) ;
        BVA_jsButton = (Button)findViewById(R.id.BVA_jsButton) ;
        BVA_treasurerButton = (Button)findViewById(R.id.BVA_treasurerButton) ;
        BVA_orgSecButton = (Button)findViewById(R.id.BVA_orgSecButton) ;
        BVA_officeSecButton = (Button)findViewById(R.id.BVA_officeSecButton) ;
        BVA_pubSecButton = (Button)findViewById(R.id.BVA_pubSecButton) ;
        BVA_intSecButton = (Button)findViewById(R.id.BVA_intSecButton) ;
        BVA_sportSecButton = (Button)findViewById(R.id.BVA_sportSecButton) ;
        BVA_socSecButton = (Button)findViewById(R.id.BVA_socSecButton) ;
        BVA_memberButton = (Button)findViewById(R.id.BVA_memberButton) ;


        //BVA_PresidentButton
        BVA_PresidentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

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
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
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
                            progressDialog.dismiss();
                            Toast.makeText(BVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            progressDialog.dismiss();
                            Toast.makeText(BVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            progressDialog.dismiss();
                            Toast.makeText(BVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            progressDialog.dismiss();
                            Toast.makeText(BVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            progressDialog.dismiss();
                            Toast.makeText(BVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            progressDialog.dismiss();
                            Toast.makeText(BVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
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

                MySingleton.getInstance(BVAActivity.this).addToRequestQueue(stringRequest);
            }
        });

        //BVA_srvpButton
        BVA_srvpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
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
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(BVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(BVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(BVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(BVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(BVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(BVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
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

                MySingleton.getInstance(BVAActivity.this).addToRequestQueue(stringRequest);
            }
        });

        //BVA_vpButton
        BVA_vpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
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
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(BVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(BVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(BVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(BVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(BVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(BVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
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

                MySingleton.getInstance(BVAActivity.this).addToRequestQueue(stringRequest);
            }
        });

        //BVA_sgButton
        BVA_sgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
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
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(BVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(BVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(BVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(BVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(BVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(BVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
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

                MySingleton.getInstance(BVAActivity.this).addToRequestQueue(stringRequest);
            }
        });

        //BVA_jsButton
        BVA_jsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
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
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(BVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(BVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(BVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(BVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(BVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(BVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
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

                MySingleton.getInstance(BVAActivity.this).addToRequestQueue(stringRequest);
            }
        });

        //BVA_treasurerButton
        BVA_treasurerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
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
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(BVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(BVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(BVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(BVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(BVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(BVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
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

                MySingleton.getInstance(BVAActivity.this).addToRequestQueue(stringRequest);
            }
        });


        //BVA_orgSecButton
        BVA_orgSecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
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
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(BVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(BVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(BVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(BVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(BVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(BVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
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

                MySingleton.getInstance(BVAActivity.this).addToRequestQueue(stringRequest);
            }
        });

        //BVA_officeSecButton
        BVA_officeSecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
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
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(BVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(BVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(BVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(BVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(BVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(BVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
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

                MySingleton.getInstance(BVAActivity.this).addToRequestQueue(stringRequest);
            }
        });

        //BVA_pubSecButton
        BVA_pubSecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
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
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(BVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(BVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(BVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(BVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(BVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(BVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
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

                MySingleton.getInstance(BVAActivity.this).addToRequestQueue(stringRequest);
            }
        });

        //BVA_intSecButton
        BVA_intSecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
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
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(BVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(BVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(BVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(BVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(BVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(BVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
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

                MySingleton.getInstance(BVAActivity.this).addToRequestQueue(stringRequest);
            }
        });

        BVA_sportSecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(BVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(BVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(BVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(BVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(BVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(BVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
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

                MySingleton.getInstance(BVAActivity.this).addToRequestQueue(stringRequest);
            }
        });

        //BVA_socSecButton
        BVA_socSecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
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
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(BVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(BVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(BVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(BVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(BVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(BVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
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

                MySingleton.getInstance(BVAActivity.this).addToRequestQueue(stringRequest);
            }
        });

        //BVA_memberButton
        BVA_memberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
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
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    //popupTextView.setText(response);
                                    //mDialog.show();
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No member found!"))
                                {
                                    Toast.makeText(BVAActivity.this, response, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(BVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(BVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(BVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            Toast.makeText(BVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(BVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(BVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
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

                MySingleton.getInstance(BVAActivity.this).addToRequestQueue(stringRequest);
            }
        });

        NavigationView navigationView = findViewById(R.id.bva_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void createExampleList(String response)
    {
        ArrayList<String> arrayListName = new ArrayList<String>();
        ArrayList<String> arrayListPhone = new ArrayList<String>();

        System.out.println("this is response : " + response);
        try
        {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String outputName = jsonObject.getString("name");
                String outputPhone = jsonObject.getString("phone");

                arrayListName.add(outputName);
                arrayListPhone.add(outputPhone);
            }

            progressDialog.dismiss();
            Intent intent = new Intent(BVAActivity.this, DisplayBVAmemberRecyclerViewActivity.class);
            intent.putStringArrayListExtra("name", arrayListName);
            intent.putStringArrayListExtra("phone", arrayListPhone);
            startActivity(intent);

        }
        catch (Exception e)
        {
            //Toast.makeText(BrowseForAdminActivity.this,"No result found",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
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

        if (id == R.id.nav_reportActivity) {

            Intent reportForBrowsersIntent = new Intent(BVAActivity.this, ReportForBrowsersActivity.class);
            startActivity(reportForBrowsersIntent);

        } else if (id == R.id.nav_aboutUs) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
