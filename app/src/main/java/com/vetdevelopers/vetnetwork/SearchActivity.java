package com.vetdevelopers.vetnetwork;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchActivity extends AppCompatActivity
{
    private Button search_searchButton;
    private EditText search_name, search_phone;
    private Spinner search_postingArea_spinner, search_district_spinner, search_division_spinner;
    private String name = "", phone = "", postingArea = "", district = "", division = "",
            POSTING_AREA = "posting_area";
    public List<String> list = new ArrayList<String>(Arrays.asList("")); //variable vanish problem , but why?
    //public Set<String> setEmpty = new HashSet<String>(Arrays.asList(""));

    final String PREF_NAME = "prefs";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_name = (EditText) findViewById(R.id.search_name);
        search_phone = (EditText) findViewById(R.id.search_phone);
        search_postingArea_spinner = (Spinner) findViewById(R.id.search_postingArea_spinner);
        search_district_spinner = (Spinner) findViewById(R.id.search_district_spinner);
        search_division_spinner = (Spinner) findViewById(R.id.search_division_spinner);
        search_searchButton = (Button) findViewById(R.id.search_searchButton);


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
                                    set1.add("Select");
                                    try
                                    {
                                        JSONArray jsonArray = new JSONArray(response);
                                        for (int i = 0; i < jsonArray.length(); i++)
                                        {
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            String output = jsonObject.getString("posting_area");

                                            //debug : for each row - print the two output
                                            //System.out.println("debug : " + (i + 1) + " " + output1);

                                            //unique
                                            if (!set1.contains(output) && !output.equals("N/A"))
                                            {
                                                set1.add(output);
                                                list.add(output);
                                            }
                                        }
                                        list.add(0, "Select");
                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }

                                    System.out.println("Initial set : " + set1);
                                    //list.addAll(set);

                                    sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                                    editor = sharedPreferences.edit();
                                    editor.putStringSet("postingArea", set1);
                                    editor.commit();

                                    System.out.println("Initial pref : " + sharedPreferences.getStringSet("postingArea", null));
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

        //getJSONFromVolley_1_2(ServerConstants.DOCTOR_POSTING_AREA);
        Set<String> setEmpty = new HashSet<String>();
        setEmpty.add("");
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet("postingArea", setEmpty);

        System.out.println("Here is set : " + set);
        System.out.println("List here : " + list);

        ArrayAdapter<String> arrayAdapterDivision = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, list);
        arrayAdapterDivision.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        search_postingArea_spinner.setAdapter(arrayAdapterDivision);
        //search_postingArea_spinner.setSelection(0);

        /*
        //SET SELECTION AFTER YOU SET THE ADAPTER NOT BEFORE IT
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        search_postingArea_spinner.setSelection(sharedPreferences.getInt("spinnerSelection1", 0));
        search_postingArea_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id)
            {
                int item = search_postingArea_spinner.getSelectedItemPosition();

                String selected = arg0.getItemAtPosition(position).toString();
                //Toast.makeText(this, "Selected item: " + selected, Toast.LENGTH_SHORT).show();
                postingArea = selected;
                sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putInt("spinnerSelection1", item);
                editor.commit();

            }

            public void onNothingSelected(AdapterView<?> arg0)
            {


            }
        });
*/
        search_searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                name = search_name.getText().toString().trim();
                phone = search_phone.getText().toString().trim();
                postingArea = search_postingArea_spinner.getSelectedItem().toString();
                district = search_district_spinner.getSelectedItem().toString();
                division = search_division_spinner.getSelectedItem().toString();

                System.out.println("debug now : " + name + " " + phone + " " + postingArea + " " +
                        district + " " + division + " ");

                StringRequest stringRequest1 = new StringRequest
                        (Request.Method.POST, ServerConstants.SEARCH_URL,
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
                                            //Bundle bundle1 = jsonStringToBundle(response);

                                            ArrayList<String> arrayListName = new ArrayList<String>();
                                            ArrayList<String> arrayListPhone = new ArrayList<String>();


                                            try
                                            {
                                                System.out.println("this is response : "+response);
                                                JSONArray jsonArray = new JSONArray(response);
                                                for (int i = 0; i < jsonArray.length(); i++)
                                                {
                                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                                    String outputName = jsonObject.getString("name");
                                                    String outputPhone = jsonObject.getString("phone");

                                                    //debug : for each row - print the two output
                                                    //System.out.println("debug : " + (i + 1) + " " + output1);

                                                    //unique
                                                    if (!arrayListName.contains(outputName) && !arrayListName.equals("N/A"))
                                                    {
                                                        arrayListName.add(outputName);
                                                    }
                                                    if (!arrayListPhone.contains(outputName) && !arrayListPhone.equals("N/A"))
                                                    {
                                                        arrayListPhone.add(outputPhone);
                                                    }
                                                }

                                                Intent intent = new Intent(SearchActivity.this, BrowseActivity.class);
                                                intent.putStringArrayListExtra("name",arrayListName);
                                                intent.putStringArrayListExtra("phone",arrayListPhone);
                                                startActivity(intent);

                                            }
                                            catch (Exception e)
                                            {
                                                Toast.makeText(SearchActivity.this,"No result found",Toast.LENGTH_LONG).show();
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
                        params.put("name", name);
                        params.put("phone", phone);
                        params.put("postingArea", postingArea);
                        params.put("district", district);
                        params.put("division", division);
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

                MySingleton.getInstance(SearchActivity.this).addToRequestQueue(stringRequest1);
























            }
        });
    }
}
