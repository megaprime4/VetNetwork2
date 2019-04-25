package com.vetdevelopers.vetnetwork;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class ReportForBrowsersActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;

    private EditText name, phone, email, topic, description;
    private Button submitButton;

    private ProgressDialog progressDialog;
    private Dialog mDialogMsg;
    private TextView msgPopupTextView;
    private Button popupOKButton;

    private String AdminEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_for_browsers);
        mToolbar = (Toolbar) findViewById(R.id.report_page_forBrowsers_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Report");

        mDialogMsg = new Dialog(ReportForBrowsersActivity.this);
        mDialogMsg.setContentView(R.layout.custompopup_success);
        mDialogMsg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        msgPopupTextView = (TextView) mDialogMsg.findViewById(R.id.popup_textView);
        popupOKButton = (Button) mDialogMsg.findViewById(R.id.popup_OK_Button);
        popupOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogMsg.dismiss();
                msgPopupTextView.setText("");
            }
        });

        progressDialog = new ProgressDialog(ReportForBrowsersActivity.this);

        name = (EditText) findViewById(R.id.reportBrowser_name);
        phone = (EditText) findViewById(R.id.reportBrowser_phone);
        email = (EditText) findViewById(R.id.reportBrowser_email);
        topic = (EditText) findViewById(R.id.reportBrowser_topic);
        description = (EditText) findViewById(R.id.reportBrowser_description);
        submitButton = (Button) findViewById(R.id.reportBrowser_submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name = name.getText().toString().trim();
                String Phone = phone.getText().toString().trim();
                String Email = email.getText().toString().trim();
                String Topic = topic.getText().toString().trim();
                String Description = description.getText().toString().trim();

                checkDataAndSubmit(Name, Phone, Email, Topic, Description);
            }
        });

        getAdminEamil(new VolleyCallback()
        {
            @Override
            public void onSuccess(String response)
            {
                try
                {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    AdminEmail = jsonObject.getString("admin_email");

                    //System.out.println("-----------------------------------------------admin email[0] = " + AdminEmail[0]);
                    //System.out.println("-----------------------------------------------admin email[1] = " + sharedPreferences.getString("admin_email_verify", ""));

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    System.out.println("-----------------------json response error occured ------------!!!");
                }
                //System.out.println("---------------------------------------------------Volley captured : " + AdminEmail + "--"+getAdminEmail);
                //return k;
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void checkDataAndSubmit(final String Name, final String Phone, final String Email, final String Topic, final String Description)
    {
        if(TextUtils.isEmpty(Name))
        {
            Toast.makeText(ReportForBrowsersActivity.this, "Please tell us your name!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Phone))
        {
            Toast.makeText(ReportForBrowsersActivity.this, "Please give us your phone number so that we can contact with you!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Topic))
        {
            Toast.makeText(ReportForBrowsersActivity.this, "Please write down topic of your problem!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Description))
        {
            Toast.makeText(ReportForBrowsersActivity.this, "Please describe your problem so that we can understand clearly!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String dateTime = getDateTime();
            //Toast.makeText(ReportForBrowsersActivity.this, dateTime, Toast.LENGTH_SHORT).show();
            //System.out.println("-------------------------------------------------dateTime " + dateTime);
            String[] tempArray;
            String splitter = "@";
            tempArray = dateTime.split(splitter);
            final String Date = tempArray[0];
            final String Time = tempArray[1];

            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("We are sending your problem to admin panel");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            //volley code
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.REPORT_BROWSERS_URL,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            if (response.contains("Connection failed!"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(ReportForBrowsersActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                            else if (response.contains("Invalid platform!"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(ReportForBrowsersActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                            else if (response.contains("Data insertion failed!"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(ReportForBrowsersActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                            else if (response.contains("Improper request method!"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(ReportForBrowsersActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                            else if (response.contains("SQL (insert) query error!"))
                            {
                                progressDialog.dismiss();
                                msgPopupTextView.setText(response);
                                mDialogMsg.show();
                                //Toast.makeText(ReportForBrowsersActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                            else if (response.contains("Sending failed!"))
                            {
                                progressDialog.dismiss();
                                msgPopupTextView.setText(response);
                                mDialogMsg.show();
                            }
                            //server data  retrieve code below...
                            else if(response.contains("Thank you for your cooperation! We will review your problem as soon as possible!"))
                            {
                                progressDialog.dismiss();
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
                        Toast.makeText(ReportForBrowsersActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof NoConnectionError)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(ReportForBrowsersActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof AuthFailureError)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(ReportForBrowsersActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof NetworkError)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(ReportForBrowsersActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof ServerError)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(ReportForBrowsersActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof ParseError)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(ReportForBrowsersActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                    }
                }
            })
            {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(ServerConstants.KEY_ADMIN_EMAIL, AdminEmail);
                    params.put(ServerConstants.KEY_NAME, Name);
                    params.put(ServerConstants.KEY_PHONE, Phone);
                    params.put(ServerConstants.KEY_EMAIL, Email);
                    params.put(ServerConstants.KEY_TOPIC, Topic);
                    params.put(ServerConstants.KEY_DESCRIPTION, Description);
                    params.put(ServerConstants.KEY_DATE, Date);
                    params.put(ServerConstants.KEY_TIME, Time);
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

            MySingleton.getInstance(ReportForBrowsersActivity.this).addToRequestQueue(stringRequest);
        }
    }

    private String getDateTime()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy@hh:mm:ss a");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+6:00"));

        String dateTime = simpleDateFormat.format(calendar.getTime());
        return dateTime;
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
            //nothing to do

        } else if (id == R.id.nav_aboutUs) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getAdminEamil(final ReportForBrowsersActivity.VolleyCallback callback)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.GET_ADMIN_EMAIL_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        if (response.equals("Registration complete! Request sent to admin!"))
                        {
                            Toast.makeText(ReportForBrowsersActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("Please check your e-mail!"))
                        {
                            Toast.makeText(ReportForBrowsersActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("Connection failed!"))
                        {
                            Toast.makeText(ReportForBrowsersActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("sql (select) query error!-outer"))
                        {
                            Toast.makeText(ReportForBrowsersActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("Improper request method!"))
                        {
                            Toast.makeText(ReportForBrowsersActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("Invalid platform!"))
                        {
                            Toast.makeText(ReportForBrowsersActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("sql (select) query error!-inner"))
                        {
                            Toast.makeText(ReportForBrowsersActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("No row selected! Please debug!"))
                        {
                            Toast.makeText(ReportForBrowsersActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            callback.onSuccess(response);

                                /*try
                            {

                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                AdminEmail = jsonObject.getString("admin_email");
                                //set email
                                *//*sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                                editor = sharedPreferences.edit();
                                editor.putString("admin_email_verify", AdminEmail[0]);
                                editor.apply();*//*

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
                    Toast.makeText(ReportForBrowsersActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NoConnectionError)
                {
                    Toast.makeText(ReportForBrowsersActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof AuthFailureError)
                {
                    Toast.makeText(ReportForBrowsersActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NetworkError)
                {
                    Toast.makeText(ReportForBrowsersActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ServerError)
                {
                    Toast.makeText(ReportForBrowsersActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ParseError)
                {
                    Toast.makeText(ReportForBrowsersActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
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

        MySingleton.getInstance(ReportForBrowsersActivity.this).addToRequestQueue(stringRequest);
    }

    public interface VolleyCallback{
        void onSuccess(String result);
    }
}
