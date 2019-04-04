package com.vetdevelopers.vetnetwork;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
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

public class LoginActivity extends AppCompatActivity
{

    private EditText userID, password;
    private Button signInButton;
    private TextView needAccount, forgotPassword;

    //custom popup
    private Dialog mDialogMsg, mDialogEmail;
    private TextView msgPopupTextView;
    private EditText popupGetEmail;
    private Button popupOKButton, popupConfirmButton;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(LoginActivity.this);

        //custom popup
        mDialogMsg = new Dialog(LoginActivity.this);
        mDialogMsg.setContentView(R.layout.custompopup_success);
        mDialogMsg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        msgPopupTextView = (TextView) mDialogMsg.findViewById(R.id.popup_textView);
        popupOKButton = (Button) mDialogMsg.findViewById(R.id.popup_OK_Button);

        mDialogEmail = new Dialog(LoginActivity.this);
        mDialogEmail.setContentView(R.layout.custompopup_get_email);
        mDialogEmail.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupGetEmail = (EditText) mDialogEmail.findViewById(R.id.getEmail);
        popupConfirmButton = (Button) mDialogEmail.findViewById(R.id.getEmail_confirm_Button);
        popupConfirmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkEmail();
            }
        });
        //custom popup

        userID = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
        needAccount = (TextView) findViewById(R.id.login_needAccount_textView);
        forgotPassword = (TextView) findViewById(R.id.login_forgotPassword_textView);
        signInButton = (Button) findViewById(R.id.login_signInButton);


        //autologin - part : 2
        /*sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        rem_userpass = (CheckBox) findViewById(R.id.checkBox);

        if (sharedPreferences.getBoolean(KEY_REMEMBER, false))
        {
            rem_userpass.setChecked(true);
        }
        else
        {
            rem_userpass.setChecked(false);
        }

        userID.setText(sharedPreferences.getString(KEY_USERNAME, ""));
        password.setText(sharedPreferences.getString(KEY_PASS, ""));

        userID.addTextChangedListener(this);
        password.addTextChangedListener(this);
        rem_userpass.setOnCheckedChangeListener(this);*/


        needAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent registrationIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(registrationIntent);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String UserID = userID.getText().toString().trim();
                final String Password = password.getText().toString().trim();

                System.out.println("...................................................." + UserID);
                System.out.println("...................................................." + Password);

                //checking input data
                if (UserID.equals("") && Password.equals(""))
                {
                    Toast.makeText(LoginActivity.this, "Please type your ID & Password!", Toast.LENGTH_SHORT).show();
                }
                else if (UserID.equals(""))
                {
                    Toast.makeText(LoginActivity.this, "Please type your ID!", Toast.LENGTH_SHORT).show();
                }
                else if (Password.equals(""))
                {
                    Toast.makeText(LoginActivity.this, "Please type your Password!", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.LOGIN_URL,
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response)
                                {
                                    if (response.contains("Connection failed!"))
                                    {
                                        msgPopupTextView.setText(response);
                                        mDialogMsg.show();
                                    }
                                    else if (response.contains("Please check your ID & Password!"))
                                    {
                                        msgPopupTextView.setText(response);
                                        mDialogMsg.show();
                                    }
                                    else if (response.contains("Improper request method!"))
                                    {
                                        msgPopupTextView.setText(response);
                                        mDialogMsg.show();
                                    }
                                    else if (response.contains("Invalid platform!"))
                                    {
                                        msgPopupTextView.setText(response);
                                        mDialogMsg.show();
                                    }
                                    else if (response.contains("sql error"))
                                    {
                                        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                                    }

                                    //server data  retrieve code below...
                                    else
                                    {

                                        try
                                        {
                                            BundleFunctions bundleFunctions = new BundleFunctions();
                                            if (bundleFunctions.MakeBundleFromJSON(response).getString("User_Type").equals("Doctor"))
                                            {
                                                Intent profileIntent = new Intent(LoginActivity.this, ProfileActivity.class);
                                                profileIntent.putExtras(bundleFunctions.MakeBundleFromJSON(response));
                                                profileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(profileIntent);
                                                finish();
                                            }
                                            else if (bundleFunctions.MakeBundleFromJSON(response).getString("User_Type").equals("Admin") ||
                                                    bundleFunctions.MakeBundleFromJSON(response).getString("User_Type").equals("pending"))
                                            {
                                                if (bundleFunctions.MakeBundleFromJSON(response).getString("User_Type").equals("Admin"))
                                                {
                                                    Intent profileIntent = new Intent(LoginActivity.this, AdminProfileActivity.class);
                                                    profileIntent.putExtras(bundleFunctions.MakeBundleFromJSON(response));
                                                    profileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(profileIntent);
                                                    finish();
                                                }
                                                else if (bundleFunctions.MakeBundleFromJSON(response).getString("User_Type").equals("pending"))
                                                {
                                                    msgPopupTextView.setText("Please activate your admin account!");
                                                    mDialogMsg.show();
                                                }
                                            }

                                        }
                                        catch (Exception e)
                                        {
                                            e.printStackTrace();
                                            System.out.println("-----------LoginActivity : --------string response error occured------------!!!");
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
                                Toast.makeText(LoginActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                            }
                            else if (error instanceof NoConnectionError)
                            {
                                Toast.makeText(LoginActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                            }
                            else if (error instanceof AuthFailureError)
                            {
                                Toast.makeText(LoginActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                            }
                            else if (error instanceof NetworkError)
                            {
                                Toast.makeText(LoginActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                            }
                            else if (error instanceof ServerError)
                            {
                                Toast.makeText(LoginActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                            }
                            else if (error instanceof ParseError)
                            {
                                Toast.makeText(LoginActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put(ServerConstants.KEY_PHONE, UserID);
                            params.put(ServerConstants.KEY_BVC_REG, UserID);
                            params.put(ServerConstants.KEY_PASSWORD, Password);
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

                    MySingleton.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);
                }
            }
        });


        popupOKButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mDialogMsg.dismiss();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                popupGetEmail.setText("");
                mDialogEmail.show();
            }
        });
    } //onCreate

    private void checkEmail()
    {
        final String ForgotPassEmail = popupGetEmail.getText().toString().trim();
        if (Patterns.EMAIL_ADDRESS.matcher(ForgotPassEmail).matches() == false)
        {
            Toast.makeText(LoginActivity.this, "Email address isn't valid!", Toast.LENGTH_SHORT).show();
        }
        else
        {

            //System.out.println("------------------------------------------ForgotPassEmail : " + ForgotPassEmail);

            mDialogEmail.dismiss();

            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("Checking the email");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            //volley code
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.FORGOT_PASSWORD,
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
                            else if (response.contains("SQL error!"))
                            {
                                msgPopupTextView.setText(response);
                                progressDialog.dismiss();
                                mDialogMsg.show();
                            }
                            else if (response.contains("This email is not registered!"))
                            {
                                msgPopupTextView.setText(response);
                                progressDialog.dismiss();
                                mDialogMsg.show();
                            }
                            else if (response.contains("Mail sending failed!"))
                            {
                                msgPopupTextView.setText(response);
                                progressDialog.dismiss();
                                mDialogMsg.show();
                            }
                            else if (response.contains("Please check your email!"))
                            {
                                msgPopupTextView.setText(response);
                                progressDialog.dismiss();
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
                        Toast.makeText(LoginActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof NoConnectionError)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof AuthFailureError)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof NetworkError)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof ServerError)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof ParseError)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                    }
                }
            })
            {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(ServerConstants.KEY_EMAIL, ForgotPassEmail);
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

            MySingleton.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);
        }
    }

    /*@Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {
        //managePrefs();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
    {
        //managePrefs();
    }

    private void managePrefs()
    {
        if (rem_userpass.isChecked())
        {
            editor.putString(KEY_USERNAME, userID.getText().toString().trim());
            editor.putString(KEY_PASS, password.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();
        }
        else
        {
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_PASS);//editor.putString(KEY_PASS,"");
            editor.remove(KEY_USERNAME);//editor.putString(KEY_USERNAME, "");
            editor.apply();
        }
    }*/
} //Class
