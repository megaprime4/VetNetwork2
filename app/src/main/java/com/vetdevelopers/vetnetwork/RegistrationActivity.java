package com.vetdevelopers.vetnetwork;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Patterns;
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
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText name, email, phone, password, retypePassword, bvc_number, designation, posting_area;
    private Spinner userType_spinner, university_spinner, district_spinner, division_spinner;
    private Button signUpButton;

    private Dialog mDialog;
    private TextView popupTextView;
    private Button popupOKButton;

    private static final String PREF_NAME = "prefs";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    ProgressDialog progressDialog;

    //Toolbar mToolbar;

    private boolean getAdminEmail = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        /*mToolbar = (Toolbar) findViewById(R.id.registration_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Sign Up");*/

        progressDialog = new ProgressDialog(this);

        mDialog = new Dialog(this);  //custom popup window
        mDialog.setContentView(R.layout.custompopup_success);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupTextView = (TextView) mDialog.findViewById(R.id.popup_textView);
        popupOKButton = (Button) mDialog.findViewById(R.id.popup_OK_Button);

        userType_spinner = (Spinner) findViewById(R.id.reg_userType_spinner);
        name = (EditText) findViewById(R.id.reg_name);
        email = (EditText) findViewById(R.id.reg_email);
        phone = (EditText) findViewById(R.id.reg_phone);
        bvc_number = (EditText) findViewById(R.id.reg_bvc);
        password = (EditText) findViewById(R.id.reg_pass1);
        retypePassword = (EditText) findViewById(R.id.reg_pass2);
        university_spinner = (Spinner) findViewById(R.id.reg_university_spinner);
        //designation = (EditText) findViewById(R.id.reg_designation);
        posting_area = (EditText) findViewById(R.id.reg_postingArea);
        district_spinner = (Spinner) findViewById(R.id.reg_district_spinner);
        division_spinner = (Spinner) findViewById(R.id.reg_division_spinner);

        signUpButton = (Button) findViewById(R.id.reg_signupButton);


        signUpButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                final String UserType = userType_spinner.getSelectedItem().toString().trim();
                final String Name = name.getText().toString().trim();
                final String Email = email.getText().toString().trim();
                final String Phone = phone.getText().toString().trim();
                final String BVC_number = bvc_number.getText().toString().trim();
                final String Password = password.getText().toString().trim();
                String RetypePassword = retypePassword.getText().toString().trim();
                final String University = university_spinner.getSelectedItem().toString().trim();
                final String Designation = "Dr.";
                final String Posting_area = posting_area.getText().toString().trim();
                final String District = district_spinner.getSelectedItem().toString().trim();
                final String Division = division_spinner.getSelectedItem().toString().trim();


                System.out.println(".........................................." + UserType);
                System.out.println(".........................................." + Name);
                System.out.println(".........................................." + Email);
                System.out.println(".........................................." + Phone);
                System.out.println(".........................................." + BVC_number);
                System.out.println(".........................................." + Password);
                System.out.println(".........................................." + RetypePassword);
                System.out.println(".........................................." + University);
                System.out.println(".........................................." + Designation);
                System.out.println(".........................................." + Posting_area);
                System.out.println(".........................................." + District);
                System.out.println(".........................................." + Division);
                //debug purpose


                String AdminEmail = getAdminEamil(); //get the latest admin email from the server
                System.out.println(".........................................admin email = " + AdminEmail);

                ///destroy here...
                sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.remove("admin_email_verify");
                editor.apply();
                System.out.println("this is cleared : " + sharedPreferences.getString("admin_email_verify", "key cleared"));

                if (getAdminEmail == true)
                {
                    registerAccount(UserType, Name, Email, AdminEmail, Phone, BVC_number, Password,
                            RetypePassword, University, Designation, Posting_area,
                            District, Division);
                }
                else
                {
                    Toast.makeText(RegistrationActivity.this, "Admin email getting error!", Toast.LENGTH_SHORT).show();
                }


            } //onClick

        }); //onCLick method

        popupOKButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mDialog.dismiss();
                Intent startPageActivity = new Intent(RegistrationActivity.this, StartPageActivity.class);
                startActivity(startPageActivity);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

            Intent reportForBrowsersIntent = new Intent(RegistrationActivity.this, ReportForBrowsersActivity.class);
            startActivity(reportForBrowsersIntent);

        } else if (id == R.id.nav_aboutUs) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private String getAdminEamil()
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
                            Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("Please check your e-mail!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("Connection failed!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("sql (select) query error!-outer"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("Improper request method!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("Invalid platform!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("sql (select) query error!-inner"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("No row selected! Please debug!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(RegistrationActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NoConnectionError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(RegistrationActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof AuthFailureError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(RegistrationActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NetworkError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(RegistrationActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ServerError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(RegistrationActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ParseError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(RegistrationActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
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

        MySingleton.getInstance(RegistrationActivity.this).addToRequestQueue(stringRequest);


        getAdminEmail = true;
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        System.out.println("-------------------------------------------------------mail (sharedPreferences)= " + sharedPreferences.getString("admin_email_verify", ""));
        return sharedPreferences.getString("admin_email_verify", "");
    }

    private void registerAccount(final String UserType, final String Name, final String Email, final String AdminEmail, final String Phone,
                                 final String BVC_number, final String Password, final String RetypePassword,
                                 final String University, final String Designation, final String Posting_area,
                                 final String District, final String Division)
    {
        //data input checking
        if (UserType.equals("Select"))
        {
            Toast.makeText(RegistrationActivity.this, "Please select user type!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Name))
        {
            Toast.makeText(RegistrationActivity.this, "Your name is required!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Email))
        {
            Toast.makeText(RegistrationActivity.this, "Your email address is required!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Phone))
        {
            Toast.makeText(RegistrationActivity.this, "Your phone number is required!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(BVC_number))
        {
            Toast.makeText(RegistrationActivity.this, "Your BVC registration number is required!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Password))
        {
            Toast.makeText(RegistrationActivity.this, "Please set a password for your account!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(RetypePassword))
        {
            Toast.makeText(RegistrationActivity.this, "Re-enter your password again!", Toast.LENGTH_SHORT).show();
        }
        else if (University.equals("Select"))
        {
            Toast.makeText(RegistrationActivity.this, "Please select your university!", Toast.LENGTH_SHORT).show();
        }
        else if (!Password.equals(RetypePassword))
        {
            Toast.makeText(RegistrationActivity.this, "Password didn't match!", Toast.LENGTH_SHORT).show();
        }
        else if (Patterns.EMAIL_ADDRESS.matcher(Email).matches() == false)
        {
            Toast.makeText(RegistrationActivity.this, "Email address is not valid!", Toast.LENGTH_SHORT).show();
        }
        else
        {

            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("Creating a new account");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            //server operation (volley) start
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.REGISTER_URL,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            if (response.equals("Registration complete! Request sent to admin!"))
                            {
                                progressDialog.dismiss();
                                showokbutton();
                                popupTextView.setText(response);
                                mDialog.show();
                            }
                            if (response.equals("Please check your e-mail!"))
                            {
                                progressDialog.dismiss();
                                showokbutton();
                                popupTextView.setText(response);
                                mDialog.show();
                            }
                            if (response.equals("Connection failed!"))
                            {
                                progressDialog.dismiss();
                                hideokbutton();
                                popupTextView.setText(response);
                                mDialog.show();
                            }
                            if (response.equals("Account already exists!"))
                            {
                                progressDialog.dismiss();
                                hideokbutton();
                                popupTextView.setText(response);
                                mDialog.show();
                            }
                            if (response.equals("Improper request method!"))
                            {
                                progressDialog.dismiss();
                                hideokbutton();
                                popupTextView.setText(response);
                                mDialog.show();
                            }
                            if (response.equals("Invalid platform!"))
                            {
                                progressDialog.dismiss();
                                hideokbutton();
                                popupTextView.setText(response);
                                mDialog.show();
                            }
                            //progressDialog.dismiss();
                            //mDialog.show();
                        }
                    }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    if (error instanceof TimeoutError)
                    {
                        Toast.makeText(RegistrationActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof NoConnectionError)
                    {
                        Toast.makeText(RegistrationActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof AuthFailureError)
                    {
                        Toast.makeText(RegistrationActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof NetworkError)
                    {
                        Toast.makeText(RegistrationActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof ServerError)
                    {
                        Toast.makeText(RegistrationActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof ParseError)
                    {
                        Toast.makeText(RegistrationActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                    }
                }
            })
            {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(ServerConstants.KEY_USER_TYPE, UserType);
                    params.put(ServerConstants.KEY_NAME, Name);
                    params.put(ServerConstants.KEY_EMAIL, Email);
                    params.put(ServerConstants.KEY_ADMIN_EMAIL, AdminEmail);
                    params.put(ServerConstants.KEY_PHONE, Phone);
                    params.put(ServerConstants.KEY_BVC_REG, BVC_number);
                    params.put(ServerConstants.KEY_PASSWORD, Password);
                    params.put(ServerConstants.KEY_UNIVERSITY, University);
                    params.put(ServerConstants.KEY_DESIGNATION, Designation);
                    params.put(ServerConstants.KEY_POSTING_AREA, Posting_area);
                    params.put(ServerConstants.KEY_DISTRICT, District);
                    params.put(ServerConstants.KEY_DIVISION, Division);

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

            MySingleton.getInstance(RegistrationActivity.this).addToRequestQueue(stringRequest);

        }
    }


    public void hideokbutton()
    {
        popupOKButton.setVisibility(View.INVISIBLE);
    }

    public void showokbutton()
    {
        popupOKButton.setVisibility(View.VISIBLE);
    }
}
