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
import android.view.MenuItem;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class DeleteAccountAdminActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText currentPassword, confirmPassword;
    private Button deleteButton;

    private ProgressDialog progressDialog;

    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "prefs";

    //custom popup
    private Dialog mDialogMsg;
    private Button msgPopupOKButton;
    private TextView msgPopupTextView;

    private boolean matchPass = false, deleteAcc = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account_admin);

        progressDialog = new ProgressDialog(DeleteAccountAdminActivity.this);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        currentPassword = (EditText) findViewById(R.id.deleteAccountAdmin_currentPassword);
        confirmPassword = (EditText) findViewById(R.id.deleteAccountAdmin_confirmPassword);
        deleteButton = (Button) findViewById(R.id.deleteAccountAdmin_deleteButton);

        mDialogMsg = new Dialog(DeleteAccountAdminActivity.this);
        mDialogMsg.setContentView(R.layout.custompopup_success);
        mDialogMsg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        msgPopupTextView = (TextView) mDialogMsg.findViewById(R.id.popup_textView);
        msgPopupOKButton = (Button) mDialogMsg.findViewById(R.id.popup_OK_Button);
        msgPopupOKButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(matchPass == false && deleteAcc == false)
                {
                    mDialogMsg.dismiss();
                    msgPopupTextView.setText("");
                }
                else if(matchPass == true && deleteAcc == false)
                {
                    mDialogMsg.dismiss();
                    msgPopupTextView.setText("");

                    String UserPhone = sharedPreferences.getString("Phone", "");

                    progressDialog.setTitle("Warning");
                    progressDialog.setMessage("Permanently deleting your account!");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    deleteAccount(UserPhone);
                }
                else if(deleteAcc == true)
                {
                    mDialogMsg.dismiss();
                    msgPopupTextView.setText("");

                    logoutUser();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPassword();
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void checkPassword()
    {
        String RightPassword = sharedPreferences.getString("Password", "");
        String CurrentPassword = currentPassword.getText().toString().trim();
        String ConfirmPassword = confirmPassword.getText().toString().trim();

        if(TextUtils.isEmpty(CurrentPassword) && TextUtils.isEmpty(ConfirmPassword))
        {
            matchPass = false;
            msgPopupTextView.setText("Please type your password!");
            mDialogMsg.show();
        }
        else if(CurrentPassword.equals(ConfirmPassword))
        {
            if(CurrentPassword.equals(RightPassword))
            {
                matchPass = true;
                msgPopupTextView.setText("Warning : Your account will be permanently deleted!");
                mDialogMsg.show();
            }
            else
            {
                matchPass = false;
                msgPopupTextView.setText("Wrong password!");
                mDialogMsg.show();
            }
        }
        else
        {
            matchPass = false;
            msgPopupTextView.setText("Confirm password did not match!");
            mDialogMsg.show();
        }
    }

    private void deleteAccount(final String UserPhone)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.DELETE_ACCOUNT_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        if (response.contains("Connection failed!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(DeleteAccountAdminActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.contains("Invalid platform!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(DeleteAccountAdminActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.contains("Improper request method!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(DeleteAccountAdminActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.contains("SQL (select) query error!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(DeleteAccountAdminActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.contains("No row selected! Please debug!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(DeleteAccountAdminActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.contains("SQL (delete) query error!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(DeleteAccountAdminActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.contains("Account permanently deleted!"))
                        {
                            deleteAcc = true;
                            matchPass = false;
                            progressDialog.dismiss();
                            msgPopupTextView.setText("Your account permanently deleted!");
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
                    Toast.makeText(DeleteAccountAdminActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NoConnectionError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(DeleteAccountAdminActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof AuthFailureError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(DeleteAccountAdminActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NetworkError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(DeleteAccountAdminActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ServerError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(DeleteAccountAdminActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ParseError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(DeleteAccountAdminActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put(ServerConstants.KEY_PHONE, UserPhone);
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

        MySingleton.getInstance(DeleteAccountAdminActivity.this).addToRequestQueue(stringRequest);
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

        if (id == R.id.nav_adminProfile) {

            Intent ProfileIntent = new Intent(DeleteAccountAdminActivity.this, AdminProfileActivity.class);
            startActivity(ProfileIntent);

        } else if (id == R.id.nav_editProfile) {

            Intent editProfileIntent = new Intent(DeleteAccountAdminActivity.this, EditProfileUserActivity.class);
            startActivity(editProfileIntent);

        } else if (id == R.id.nav_changePassword) {

            Intent changePasswordIntent = new Intent(DeleteAccountAdminActivity.this, ChangePasswordActivity.class);
            startActivity(changePasswordIntent);

        } else if (id == R.id.nav_logout) {

            getApplicationContext().getSharedPreferences("prefs", 0).edit().clear().commit();
            //exit intent - clear previous flags of intent
            logoutUser();

        } else if (id == R.id.nav_deleteAccount) {
            //nothing to do

        }else if (id == R.id.nav_adminPanel) {

            Intent adminPanelIntent = new Intent(DeleteAccountAdminActivity.this, AdminPanelActivity.class);
            startActivity(adminPanelIntent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutUser()
    {
        Intent startPageIntent = new Intent(DeleteAccountAdminActivity.this, StartPageActivity.class);
        startPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startPageIntent);
        finish();
    }
}
