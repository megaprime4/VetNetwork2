package com.vetdevelopers.vetnetwork;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.Map;

public class AdminPanelActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    private EditText getEmail, getUserID, showCurrentAdminEmail;
    private TextView currentAdminEmail;
    private Button emailChangeBtn, userSearchBtn, requestListBtn;
    private RadioGroup radioGroupSearchType;
    private RadioButton radioButtonSearchType;

    //custom popup
    private Dialog mDialogPass, mDialogMsg;
    private EditText popupGetPassword;
    private Button popupConfirmButton, msgPopupOKButton;
    private TextView msgPopupTextView;

    private ProgressDialog progressDialog;

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";

    private Boolean emailBtn = false, searchBtn = false, passMatch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        progressDialog = new ProgressDialog(this);

        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        showCurrentAdminEmail = (EditText) findViewById(R.id.admin_panel_showCurrentEmail);
        currentAdminEmail = (TextView) findViewById(R.id.admin_panel_currentEmail);
        getEmail = (EditText) findViewById(R.id.adminPanel_getEmail);
        emailChangeBtn = (Button) findViewById(R.id.adminPanel_emailChangeBtn);
        getUserID = (EditText) findViewById(R.id.adminPanel_getUserID);
        userSearchBtn = (Button) findViewById(R.id.adminPanel_userSearchBtn);
        requestListBtn = (Button) findViewById(R.id.adminPanel_requestListBtn);

        radioGroupSearchType = (RadioGroup) findViewById(R.id.adminPanel_radioGroup);



        //onClickListener

        requestListBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent browseIntent = new Intent(AdminPanelActivity.this, BrowseForAdminActivity.class);
                startActivity(browseIntent);
            }
        });

        showCurrentAdminEmail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                progressDialog.setTitle("Please wait");
                progressDialog.setMessage("Getting current admin email..");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                /*String AdminEmail = getAdminEmail();
                currentAdminEmail.setText(AdminEmail);
                progressDialog.dismiss();
                System.out.println(".........................................adminEmail (onCreate) = " + AdminEmail);

                ///destroy here...
                sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.remove("admin_email_verify");
                editor.apply();
                System.out.println("Admin email is cleared : " + sharedPreferences.getString("admin_email_verify", "key cleared"));*/

                getAdminEamil(new VolleyCallback()
                {
                    @Override
                    public void onSuccess(String response)
                    {
                        try
                        {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String AdminEmail = jsonObject.getString("admin_email");
                            currentAdminEmail.setText(AdminEmail);
                            progressDialog.dismiss();

                            //System.out.println("-----------------------------------------------admin email[0] = " + AdminEmail[0]);
                            //System.out.println("-----------------------------------------------admin email[1] = " + sharedPreferences.getString("admin_email_verify", ""));

                        }
                        catch (JSONException e)
                        {
                            progressDialog.dismiss();
                            e.printStackTrace();
                            System.out.println("-----------------------json response error occured ------------!!!");
                        }
                        //System.out.println("---------------------------------------------------Volley captured : " + AdminEmail + "--"+getAdminEmail);
                        //return k;
                    }
                });
            }
        });


        emailChangeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String Email = getEmail.getText().toString().trim();
                if (TextUtils.isEmpty(Email))
                {
                    Toast.makeText(AdminPanelActivity.this, "Enter the new Email!", Toast.LENGTH_SHORT).show();
                }
                else if (Patterns.EMAIL_ADDRESS.matcher(Email).matches() == false)
                {
                    Toast.makeText(AdminPanelActivity.this, "Invalid Email!", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    emailBtn = true;
                    searchBtn = false;

                    popupGetPassword.setText("");
                    mDialogPass.show();
                }
            }
        });

        userSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = getUserID.getText().toString().trim();
                if(TextUtils.isEmpty(userID))
                {
                    Toast.makeText(AdminPanelActivity.this, "Enter the selected type user ID!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    emailBtn = false;
                    searchBtn = true;

                    popupGetPassword.setText("");
                    mDialogPass.show();
                }
            }
        });


        //custom popup
        mDialogPass = new Dialog(this);
        mDialogPass.setContentView(R.layout.custompopup_get_password);
        mDialogPass.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupGetPassword = (EditText) mDialogPass.findViewById(R.id.getPassword);
        popupConfirmButton = (Button) mDialogPass.findViewById(R.id.getPassword_confirm_Button);
        popupConfirmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkPassword();
            }
        });

        mDialogMsg = new Dialog(this);
        mDialogMsg.setContentView(R.layout.custompopup_success);
        mDialogMsg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        msgPopupTextView = (TextView) mDialogMsg.findViewById(R.id.popup_textView);
        msgPopupOKButton = (Button) mDialogMsg.findViewById(R.id.popup_OK_Button);
        msgPopupOKButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //mDialogMsg.dismiss();

                if (passMatch == false)
                {
                    mDialogMsg.dismiss();
                    popupGetPassword.setText("");
                    mDialogPass.show();
                }
                else if (passMatch == true)
                {
                    mDialogMsg.dismiss();
                }
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /*private String getAdminEmail()
    {
        final String[] AdminEmail = {""};


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.GET_ADMIN_EMAIL_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        if (response.equals("Registration complete! Request sent to admin!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("Please check your e-mail!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("Connection failed!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("sql (select) query error!-outer"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("Improper request method!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("Invalid platform!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("sql (select) query error!-inner"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("No row selected! Please debug!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                        }

                        try
                        {

                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            AdminEmail[0] = jsonObject.getString("admin_email");
                            //set email
                            sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putString("admin_email_verify", AdminEmail[0]);
                            editor.apply();

                            System.out.println("-----------------------------------------------admin email[0] = " + AdminEmail[0]);
                            System.out.println("-----------------------------------------------admin email[1] = " + sharedPreferences.getString("admin_email_verify", ""));

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                            System.out.println("-----------------------json response error occured ------------!!!");
                        }
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                if (error instanceof TimeoutError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AdminPanelActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NoConnectionError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AdminPanelActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof AuthFailureError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AdminPanelActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NetworkError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AdminPanelActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ServerError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AdminPanelActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ParseError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AdminPanelActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
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
                headers.put("User-Agent", "VetNetwork"); ////security purpose
                return headers;
            }
        };

        MySingleton.getInstance(AdminPanelActivity.this).addToRequestQueue(stringRequest);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        System.out.println("-------------------------------------------------------adminEmail (return) (sharedPreferences)= " + sharedPreferences.getString("admin_email_verify", ""));
        return sharedPreferences.getString("admin_email_verify", "");
    }*/

    public void getAdminEamil(final AdminPanelActivity.VolleyCallback callback)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.GET_ADMIN_EMAIL_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        if (response.equals("Registration complete! Request sent to admin!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("Please check your e-mail!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("Connection failed!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("sql (select) query error!-outer"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("Improper request method!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("Invalid platform!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("sql (select) query error!-inner"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("No row selected! Please debug!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            callback.onSuccess(response);

                            /*try
                            {

                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                AdminEmail[0] = jsonObject.getString("admin_email");
                                //set email
                                sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                                editor = sharedPreferences.edit();
                                editor.putString("admin_email_verify", AdminEmail[0]);
                                editor.apply();

                                System.out.println("-----------------------------------------------admin email[0] = " + AdminEmail[0]);
                                System.out.println("-----------------------------------------------admin email[1] = " + sharedPreferences.getString("admin_email_verify", ""));

                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                                System.out.println("-----------------------json response error occured ------------!!!");
                            }*/
                        }
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                if (error instanceof TimeoutError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AdminPanelActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NoConnectionError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AdminPanelActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof AuthFailureError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AdminPanelActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NetworkError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AdminPanelActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ServerError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AdminPanelActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ParseError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AdminPanelActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
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
                headers.put("User-Agent", "VetNetwork"); ////security purpose
                return headers;
            }
        };

        MySingleton.getInstance(AdminPanelActivity.this).addToRequestQueue(stringRequest);
    }

    private void checkPassword()
    {
        String password = popupGetPassword.getText().toString().trim();
        String matchPassword = sharedPreferences.getString("Password", "");

        if (password.equals(matchPassword))
        {
            passMatch = true;

            mDialogPass.dismiss();
            if (emailBtn == true)
            {
                changeEmail();
                progressDialog.setTitle("Please wait");
                progressDialog.setMessage("Changing admin e-mail");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
            }
            else if (searchBtn == true)
            {
                checkUserID(); //will check if the user is registered or not
            }
        }
        else
        {
            passMatch = false;

            mDialogPass.dismiss();
            msgPopupTextView.setText("Wrong password!");
            mDialogMsg.show();
        }
    }

    private void checkUserID()
    {
        int selectedId = 0;
        selectedId = radioGroupSearchType.getCheckedRadioButtonId();
        radioButtonSearchType = (RadioButton) findViewById(selectedId);

        if(radioButtonSearchType != null)
        {
            final String selectedRadioButton = radioButtonSearchType.getText().toString();
            final String inputUserID = getUserID.getText().toString().trim();

            System.out.println("------------entering volley checkuser--------------------------");

            ///volley code
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.SEARCH_FOR_DELETE_URL,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            if (response.contains("Connection failed!"))
                            {
                                //popupTextView.setText(response);
                                //mDialog.show();
                                Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                            else if (response.contains("Invalid platform!"))
                            {
                                //popupTextView.setText(response);
                                //mDialog.show();
                                Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                            else if (response.contains("Improper request method!"))
                            {
                                //popupTextView.setText(response);
                                //mDialog.show();
                                Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                            else if (response.contains("Something wrong with the RadioButton! Please debug!"))
                            {
                                //popupTextView.setText(response);
                                //mDialog.show();
                                Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                            else if (response.contains("SQL query (BVC) error!"))
                            {
                                //popupTextView.setText(response);
                                //mDialog.show();
                                Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                            else if (response.contains("SQL query (Phone) error!"))
                            {
                                //popupTextView.setText(response);
                                //mDialog.show();
                                Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                            else if (response.contains("SQL query (Name) error!"))
                            {
                                //popupTextView.setText(response);
                                //mDialog.show();
                                Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                            else if (response.contains("No user found having this BVC registration number!"))
                            {
                                Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                            else if (response.contains("No user found having like this phone number!"))
                            {
                                Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                            else if (response.contains("No user found having like this name!"))
                            {
                                Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
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

                                    System.out.println("------------------------------------adminpanel inten---------------");

                                    Intent browseIntent = new Intent(AdminPanelActivity.this, BrowseForAdminDeleteActivity.class);
                                    browseIntent.putStringArrayListExtra("name", arrayListName);
                                    browseIntent.putStringArrayListExtra("phone", arrayListPhone);
                                    startActivity(browseIntent);

                                }
                                catch (Exception e)
                                {
                                    //Toast.makeText(AdminPanelActivity.this,"No result found",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(AdminPanelActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof NoConnectionError)
                    {
                        Toast.makeText(AdminPanelActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof AuthFailureError)
                    {
                        Toast.makeText(AdminPanelActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof NetworkError)
                    {
                        Toast.makeText(AdminPanelActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof ServerError)
                    {
                        Toast.makeText(AdminPanelActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof ParseError)
                    {
                        Toast.makeText(AdminPanelActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                    }
                }
            })
            {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put(ServerConstants.KEY_SELECTED_RADIO_BUTTON, selectedRadioButton);
                    params.put(ServerConstants.KEY_USER_ID, inputUserID);
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

            MySingleton.getInstance(AdminPanelActivity.this).addToRequestQueue(stringRequest);
        }
        else
        {
            Toast.makeText(AdminPanelActivity.this, "Please select a search category!", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeEmail()
    {
        final String newAdminEmail = getEmail.getText().toString().trim();
        final String adminName = sharedPreferences.getString("Name", "");
        final String adminPhone = sharedPreferences.getString("Phone", "");

        //volley code
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.UPDATE_CURRENT_ADMIN_EMAIL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        if (response.contains("Invalid platform!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.contains("Improper request method!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.contains("SQL (insert) query error!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.contains("Data insertion failed!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.contains("Mail sending failed!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdminPanelActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.contains("Please check your e-mail!"))
                        {
                            progressDialog.dismiss();
                            getEmail.setText("");
                            passMatch = true;
                            msgPopupTextView.setText(response);
                            mDialogMsg.show();
                        }
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                if (error instanceof TimeoutError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AdminPanelActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NoConnectionError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AdminPanelActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof AuthFailureError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AdminPanelActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NetworkError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AdminPanelActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ServerError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AdminPanelActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ParseError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AdminPanelActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(ServerConstants.KEY_ADMIN_NAME, adminName);
                params.put(ServerConstants.KEY_ADMIN_PHONE, adminPhone);
                params.put(ServerConstants.KEY_ADMIN_EMAIL, newAdminEmail);
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

        MySingleton.getInstance(AdminPanelActivity.this).addToRequestQueue(stringRequest);

        //end volley
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem)
    {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_adminProfile)
        {
            Intent ProfileIntent = new Intent(AdminPanelActivity.this, AdminProfileActivity.class);
            startActivity(ProfileIntent);
        }
        else if (id == R.id.nav_editProfile)
        {
            Intent editProfileIntent = new Intent(AdminPanelActivity.this, EditProfileUserActivity.class);
            startActivity(editProfileIntent);
        }
        else if (id == R.id.nav_changePassword)
        {
            Intent changePasswordIntent = new Intent(AdminPanelActivity.this, ChangePasswordActivity.class);
            startActivity(changePasswordIntent);
        }
        else if (id == R.id.nav_logout)
        {
            //firebaseAuth.signOut();
            //stop auto login
            getApplicationContext().getSharedPreferences("prefs", 0).edit().clear().commit();
            //exit intent - clear previous flags of intent
            logoutUser();
        }
        else if (id == R.id.nav_deleteAccount)
        {
            Intent deleteAccountAdminIntent = new Intent(AdminPanelActivity.this, DeleteAccountAdminActivity.class);
            startActivity(deleteAccountAdminIntent);
        }
        else if (id == R.id.nav_adminPanel)
        {
            //nothing will happen
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutUser()
    {
        Intent startPageIntent = new Intent(AdminPanelActivity.this, StartPageActivity.class);
        startPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startPageIntent);
        finish();
    }

    public interface VolleyCallback
    {
        void onSuccess(String result);
    }
}
