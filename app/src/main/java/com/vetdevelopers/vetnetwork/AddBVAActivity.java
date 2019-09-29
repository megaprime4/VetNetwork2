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
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddBVAActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //custom popup
    private Dialog mDialogPass, mDialogMsg;
    private EditText popupGetPassword;
    private Button popupConfirmButton, msgPopupOKButton;
    private TextView msgPopupTextView;
    //custom popup

    private ProgressDialog progressDialog;

    private Button addBtn;
    private EditText name, phone, email;
    private Spinner designation_spinner;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bva);

        progressDialog = new ProgressDialog(this);
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        //custom popup
        mDialogPass = new Dialog(this);  //custom popup window
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

        mDialogMsg = new Dialog(this);  //custom popup window
        mDialogMsg.setContentView(R.layout.custompopup_success);
        mDialogMsg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        msgPopupTextView = (TextView) mDialogMsg.findViewById(R.id.popup_textView);
        msgPopupOKButton = (Button) mDialogMsg.findViewById(R.id.popup_OK_Button);
        msgPopupOKButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mDialogMsg.dismiss();
            }
        });

        designation_spinner = (Spinner) findViewById(R.id.addBVA_designation_spinner);
        name = (EditText) findViewById(R.id.addBVA_name);
        phone = (EditText) findViewById(R.id.addBVA_contact);
        email = (EditText) findViewById(R.id.addBVA_email);
        addBtn = (Button) findViewById(R.id.addBVA_addButton);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupGetPassword.setText("");
                mDialogPass.show();
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.addbva_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void checkPassword()
    {
        String password = popupGetPassword.getText().toString().trim();
        String matchPassword = sharedPreferences.getString("Password", "");

        if (password.equals(matchPassword))
        {
            mDialogPass.dismiss();
            checkData();
        }
        else
        {
            mDialogPass.dismiss();
            msgPopupTextView.setText("Wrong password!");
            mDialogMsg.show();
        }
    }

    private void checkData()
    {
        String Designation = designation_spinner.getSelectedItem().toString();
        String Name = name.getText().toString().trim();
        String Phone = phone.getText().toString().trim();
        String Email = email.getText().toString().trim();

        if(TextUtils.isEmpty(Name))
        {
            Toast.makeText(this, "Sorry! Name is required!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Phone))
        {
            Toast.makeText(this, "Sorry! Contact is required!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Email))
        {
            Toast.makeText(this, "Sorry! Email is required!", Toast.LENGTH_SHORT).show();
        }
        else if (Designation.equals("Select"))
        {
            Toast.makeText(this, "Please select a designation!", Toast.LENGTH_SHORT).show();
        }
        else if (Patterns.EMAIL_ADDRESS.matcher(Email).matches() == false)
        {
            Toast.makeText(this, "Email address is not valid!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            addBVAfunction(Designation, Name, Phone, Email);
        }
    }

    private void addBVAfunction(final String designation, final String name, final String phone, final String email)
    {
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Operation in progress");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.ADD_BVA_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        if (response.contains("Connection failed!"))
                        {
                            msgPopupTextView.setText(response);
                            progressDialog.dismiss();
                            mDialogMsg.show();
                        }
                        else if (response.contains("Invalid platform!"))
                        {
                            msgPopupTextView.setText(response);
                            progressDialog.dismiss();
                            mDialogMsg.show();
                        }
                        else if (response.contains("Improper request method!"))
                        {
                            msgPopupTextView.setText(response);
                            progressDialog.dismiss();
                            mDialogMsg.show();
                        }
                        else if (response.contains("sql (update) query error!"))
                        {
                            msgPopupTextView.setText(response);
                            progressDialog.dismiss();
                            mDialogMsg.show();
                        }
                        else
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
                    Toast.makeText(AddBVAActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NoConnectionError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AddBVAActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof AuthFailureError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AddBVAActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NetworkError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AddBVAActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ServerError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AddBVAActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ParseError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(AddBVAActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(ServerConstants.KEY_DESIGNATION, designation);
                params.put(ServerConstants.KEY_NAME, name);
                params.put(ServerConstants.KEY_PHONE, phone);
                params.put(ServerConstants.KEY_EMAIL, email);

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

        MySingleton.getInstance(AddBVAActivity.this).addToRequestQueue(stringRequest);
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

        if (id == R.id.nav_adminProfile)
        {
            Intent adminProfileIntent = new Intent(AddBVAActivity.this, AdminProfileActivity.class);
            startActivity(adminProfileIntent);
        }
        else if (id == R.id.nav_editProfile)
        {
            Intent editProfileIntent = new Intent(AddBVAActivity.this, EditProfileAdminActivity.class);
            startActivity(editProfileIntent);
        }
        else if (id == R.id.nav_changePassword)
        {
            Intent changePasswordIntent = new Intent(AddBVAActivity.this, ChangePasswordActivity.class);
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

            Intent deleteAccountAdminIntent = new Intent(AddBVAActivity.this, DeleteAccountAdminActivity.class);
            startActivity(deleteAccountAdminIntent);

        }
        else if (id == R.id.nav_adminPanel)
        {
            Intent adminControlPanel = new Intent(AddBVAActivity.this, AdminPanelActivity.class);
            startActivity(adminControlPanel);
        }
        else if (id == R.id.nav_reportActivity)
        {
            Intent reportForAdminIntent = new Intent(AddBVAActivity.this, ReportForAdminActivity.class);
            startActivity(reportForAdminIntent);
        }
        else if (id == R.id.nav_aboutUs)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutUser()
    {
        Intent startPageIntent = new Intent(AddBVAActivity.this, StartPageActivity.class);
        startPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startPageIntent);
        finish();
    }
}
