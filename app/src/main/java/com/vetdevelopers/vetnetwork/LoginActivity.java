package com.vetdevelopers.vetnetwork;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements TextWatcher,
        CompoundButton.OnCheckedChangeListener
{

    private EditText userID, password;
    private Button signInButton;
    private TextView needAccount;

    private Dialog mDialog;
    private TextView popupTextView;
    private Button popupOKButton;


    //autologin - part : 1
    private CheckBox rem_userpass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //updateUI(firebaseAuth);


        mDialog = new Dialog(this);  //custom popup window
        mDialog.setContentView(R.layout.custompopup_success);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupTextView = (TextView) mDialog.findViewById(R.id.popup_textView);
        popupOKButton = (Button) mDialog.findViewById(R.id.popup_OK_Button);

        userID = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
        needAccount = (TextView) findViewById(R.id.login_needAccount_textView);
        signInButton = (Button) findViewById(R.id.login_signInButton);


        //autologin - part : 2
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
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
        rem_userpass.setOnCheckedChangeListener(this);


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
                                        popupTextView.setText(response);
                                        mDialog.show();
                                    }
                                    else if (response.contains("Please check your ID & Password!"))
                                    {
                                        popupTextView.setText(response);
                                        mDialog.show();
                                    }
                                    else if (response.contains("Improper request method!"))
                                    {
                                        popupTextView.setText(response);
                                        mDialog.show();
                                    }
                                    else if (response.contains("Invalid platform!"))
                                    {
                                        popupTextView.setText(response);
                                        mDialog.show();
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
                                            if(bundleFunctions.MakeBundleFromJSON(response).getString("User_Type").equals("Doctor"))
                                            {
                                                Intent profileIntent = new Intent(LoginActivity.this, ProfileActivity.class);
                                                profileIntent.putExtras(bundleFunctions.MakeBundleFromJSON(response));
                                                profileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(profileIntent);
                                                finish();
                                            }
                                            else if(bundleFunctions.MakeBundleFromJSON(response).getString("User_Type").equals("Admin"))
                                            {
                                                Intent profileIntent = new Intent(LoginActivity.this, AdminProfileActivity.class);
                                                profileIntent.putExtras(bundleFunctions.MakeBundleFromJSON(response));
                                                profileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(profileIntent);
                                                finish();
                                            }

                                        }
                                        catch (Exception e)
                                        {
                                            e.printStackTrace();
                                            System.out.println("-----------LoginActivity : --------string response error occured ------------!!!");
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
                mDialog.dismiss();
            }
        });
    } //onCreate

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {
        managePrefs();
    }

    @Override
    public void afterTextChanged(Editable editable)
    {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
    {
        managePrefs();
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
    }
} //Class
