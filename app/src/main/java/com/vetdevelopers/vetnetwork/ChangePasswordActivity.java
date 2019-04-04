package com.vetdevelopers.vetnetwork;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText getCurrentPassword, getNewPassword, getConfirmPassword;
    private Button changeButton;

    private ProgressDialog progressDialog;

    //custom popup
    private Dialog mDialogMsg;
    private Button msgPopupOKButton;
    private TextView msgPopupTextView;
    //custom popup

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";

    private Boolean passwordChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        progressDialog = new ProgressDialog(ChangePasswordActivity.this);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        //custom popup
        mDialogMsg = new Dialog(ChangePasswordActivity.this);  //custom popup window
        mDialogMsg.setContentView(R.layout.custompopup_success);
        mDialogMsg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        msgPopupTextView = (TextView) mDialogMsg.findViewById(R.id.popup_textView);
        msgPopupOKButton = (Button) mDialogMsg.findViewById(R.id.popup_OK_Button);
        msgPopupOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordChanged == true)
                {
                    //destroy shared preference & logout please..
                    getApplicationContext().getSharedPreferences(PREF_NAME, 0).edit().clear().commit();
                    logoutUser();
                }
                else if(passwordChanged == false)
                {
                    mDialogMsg.dismiss();
                }
            }
        });
        //custom popup

        getCurrentPassword = (EditText) findViewById(R.id.changePassword_currentPassword);
        getNewPassword = (EditText) findViewById(R.id.changePassword_newPassword);
        getConfirmPassword = (EditText) findViewById(R.id.changePassword_confirmPassword);
        changeButton = (Button) findViewById(R.id.changePassword_changeButton);

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPassword();
            }
        });
    }

    private void checkPassword()
    {
        String userInputPass = getCurrentPassword.getText().toString().trim();

        String currentUserPhone = sharedPreferences.getString("Phone", "");
        String currentPassword = getCurrentPasswordFromDB(currentUserPhone);

        if(userInputPass.equals(currentPassword))
        {
            String newPassword = getNewPassword.getText().toString().trim();
            String confirmPassword = getConfirmPassword.getText().toString().trim();
            if(newPassword.equals(confirmPassword)) {

                String Phone = sharedPreferences.getString("Phone", "");
                progressDialog.setTitle("Please Wait");
                progressDialog.setMessage("Changing your password");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                changePassword(newPassword, Phone);

            } else {
                msgPopupTextView.setText("Confirm password didn't match!");
                mDialogMsg.show();
            }
        }
        else
        {
            msgPopupTextView.setText("Wrong password!");
            mDialogMsg.show();
        }
    }

    private String getCurrentPasswordFromDB(final String currentUserPhone)
    {
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Getting credential");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        final String[] currentPassword = {""};

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.GET_CURRENT_PASSWORD,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        if (response.equals("Registration complete! Request sent to admin!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(ChangePasswordActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("Please check your e-mail!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(ChangePasswordActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("Connection failed!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(ChangePasswordActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("sql (select) query error!-outer"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(ChangePasswordActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("Improper request method!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(ChangePasswordActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("Invalid platform!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(ChangePasswordActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("sql (select) query error!-inner"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(ChangePasswordActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        else if (response.equals("No row selected! Please debug!"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(ChangePasswordActivity.this, response, Toast.LENGTH_SHORT).show();
                        }

                        try
                        {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            currentPassword [0] = jsonObject.getString("current_user_password");

                            //set password
                            editor = sharedPreferences.edit();
                            editor.putString("current_user_password_verify", currentPassword[0]);
                            editor.apply();

                            System.out.println("-----------------------------------------------current_user_password[0] = " + currentPassword[0]);
                            System.out.println("-----------------------------------------------current_user_password[1] = " + sharedPreferences.getString("current_user_password_verify", ""));
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                            System.out.println("----------------------- json response error occured ------------!!!");
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
                    Toast.makeText(ChangePasswordActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NoConnectionError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(ChangePasswordActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof AuthFailureError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(ChangePasswordActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NetworkError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(ChangePasswordActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ServerError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(ChangePasswordActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ParseError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(ChangePasswordActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(ServerConstants.KEY_PHONE, currentUserPhone);

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

        MySingleton.getInstance(ChangePasswordActivity.this).addToRequestQueue(stringRequest);

        System.out.println("-------------------------------------------------------current_user_password (return) (sharedPreferences)= " + sharedPreferences.getString("current_user_password_verify", ""));
        return sharedPreferences.getString("current_user_password_verify", "");
    }

    private void changePassword(final String newPassword, final String Phone)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.CHANGE_PASSWORD,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        if (response.contains("Connection failed!")) {
                            msgPopupTextView.setText(response);
                            progressDialog.dismiss();
                            mDialogMsg.show();
                        }
                        else if (response.contains("Invalid platform!")) {
                            msgPopupTextView.setText(response);
                            progressDialog.dismiss();
                            mDialogMsg.show();
                        }
                        else if (response.contains("Improper request method!")) {
                            msgPopupTextView.setText(response);
                            progressDialog.dismiss();
                            mDialogMsg.show();
                        }
                        else if (response.contains("SQL error (select)!")) {
                            msgPopupTextView.setText(response);
                            progressDialog.dismiss();
                            mDialogMsg.show();
                        }
                        else if (response.contains("No row selected! Please debug!")) {
                            msgPopupTextView.setText(response);
                            progressDialog.dismiss();
                            mDialogMsg.show();
                        }
                        else if(response.contains("SQL error (update)!")) {
                            msgPopupTextView.setText(response);
                            progressDialog.dismiss();
                            mDialogMsg.show();
                        }
                        else if(response.contains("Warning : Password changed!")) {
                            progressDialog.dismiss();
                            msgPopupTextView.setText(response);
                            System.out.println("---------------------ChangePassActivity------------------------" + response);
                            passwordChanged = true; //for logout on pressing "OK" at Dialog box
                            mDialogMsg.show();
                        }
                        //System.out.println("---------------------ChangePassActivity------------------------" + response);
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                if (error instanceof TimeoutError) {
                    progressDialog.dismiss();
                    Toast.makeText(ChangePasswordActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NoConnectionError) {
                    progressDialog.dismiss();
                    Toast.makeText(ChangePasswordActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof AuthFailureError) {
                    progressDialog.dismiss();
                    Toast.makeText(ChangePasswordActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NetworkError) {
                    progressDialog.dismiss();
                    Toast.makeText(ChangePasswordActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ServerError) {
                    progressDialog.dismiss();
                    Toast.makeText(ChangePasswordActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ParseError) {
                    progressDialog.dismiss();
                    Toast.makeText(ChangePasswordActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(ServerConstants.KEY_PHONE, Phone);
                params.put(ServerConstants.KEY_PASSWORD, newPassword);
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

        MySingleton.getInstance(ChangePasswordActivity.this).addToRequestQueue(stringRequest);
    }

    private void logoutUser() {
        Intent startPageIntent = new Intent(ChangePasswordActivity.this, StartPageActivity.class);
        startPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startPageIntent);
        finish();
    }
}