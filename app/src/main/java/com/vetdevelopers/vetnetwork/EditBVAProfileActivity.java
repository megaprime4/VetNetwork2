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
import android.text.TextUtils;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EditBVAProfileActivity extends AppCompatActivity {

    private EditText name, email, phone;
    private TextView designation;
    private Button change;

    //Resources resources;

    private ProgressDialog progressDialog;

    //custom popup
    private Dialog mDialogPass, mDialogMsg;
    private EditText popupGetPassword;
    private Button popupConfirmButton, msgPopupOKButton;
    private TextView msgPopupTextView;

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";

    private String[] bvaDesignationArray;
    private String Id, Name, Phone, Email, Designation = "";
    private Boolean msgCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bvaprofile);

        progressDialog = new ProgressDialog(this);
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        //custom popup
        mDialogPass = new Dialog(this);  //custom popup window
        mDialogPass.setContentView(R.layout.custompopup_get_password);
        mDialogPass.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupGetPassword = (EditText) mDialogPass.findViewById(R.id.getPassword);
        popupConfirmButton = (Button) mDialogPass.findViewById(R.id.getPassword_confirm_Button);

        mDialogMsg = new Dialog(this);  //custom popup window
        mDialogMsg.setContentView(R.layout.custompopup_success);
        mDialogMsg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        msgPopupTextView = (TextView) mDialogMsg.findViewById(R.id.popup_textView);
        msgPopupOKButton = (Button) mDialogMsg.findViewById(R.id.popup_OK_Button);


        name = (EditText) findViewById(R.id.editBVAProfile_name);
        email = (EditText) findViewById(R.id.editBVAProfile_email);
        phone = (EditText) findViewById(R.id.editBVAProfile_phone);
        designation = (TextView) findViewById(R.id.editBVAProfile_Designation);
        change = (Button) findViewById(R.id.editBVAProfile_changeButton);

        Bundle bundle = getIntent().getExtras();

        Id = bundle.getString("Id");
        Name = bundle.getString("Name");
        Email = bundle.getString("Email");
        Phone = bundle.getString("Phone");
        Designation = bundle.getString("Designation");

        System.out.println("-----------------"+Id);
        System.out.println("-----------------"+Name);
        System.out.println("-----------------"+Phone);
        System.out.println("-----------------"+Email);
        System.out.println("-----------------"+Designation);

        name.setText(Name);
        email.setText(Email);
        phone.setText(Phone);
        designation.setText(Designation);

        /*resources = getResources();  ///getting xml string array resource
        bvaDesignationArray = resources.getStringArray(R.array.bva_designation);
        int bvaDesignationIndex = Integer.parseInt("" + Arrays.asList(bvaDesignationArray).indexOf(Designation));
        designation.setSelection(bvaDesignationIndex);*/

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupGetPassword.setText("");
                mDialogPass.show();
            }
        });

        popupConfirmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkPassword();
            }
        });

        msgPopupOKButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(msgCheck == true)
                {
                    Intent intent = new Intent(EditBVAProfileActivity.this, EditBVAActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    mDialogMsg.dismiss();
                }
            }
        });

    }

    private void checkPassword() {
        String password = popupGetPassword.getText().toString();
        String matchPassword = sharedPreferences.getString("Password", "");

        if(password.equals(matchPassword)) {
            mDialogPass.dismiss();
            checkData();
        } else {
            mDialogPass.dismiss();
            msgPopupTextView.setText("Wrong password!");
            mDialogMsg.show();
        }
    }

    private void checkData() {
        //getting data
        String id = Id;
        String Name = name.getText().toString().trim();
        String Phone = phone.getText().toString().trim();
        String Email = email.getText().toString().trim();

        //checking data
        if(TextUtils.isEmpty(Name))
        {
            Toast.makeText(this, "Name is required!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Phone))
        {
            Toast.makeText(this, "Phone is required!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Email))
        {
            Toast.makeText(this, "Email is required!", Toast.LENGTH_SHORT).show();
        }
        else if(Patterns.EMAIL_ADDRESS.matcher(Email).matches() == false)
        {
            Toast.makeText(this, "Invalid email address!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            changeBVAProfile(id, Name, Phone, Email);
        }
    }

    private void changeBVAProfile(final String id, final String name, final String phone, final String email) {
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Changing your profile");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.UPDATE_BVA_PROFILE_URL,
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
                        else if (response.contains("No row selected!"))
                        {
                            msgPopupTextView.setText(response);
                            progressDialog.dismiss();
                            mDialogMsg.show();
                        }
                        else if (response.contains("SQL (update) query error!"))
                        {
                            msgPopupTextView.setText(response);
                            progressDialog.dismiss();
                            mDialogMsg.show();
                        }
                        else if (response.contains("Operation successful!"))
                        {
                            msgPopupTextView.setText(response);
                            progressDialog.dismiss();
                            msgCheck = true;
                            mDialogMsg.show();
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
                    Toast.makeText(EditBVAProfileActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NoConnectionError)
                {
                    Toast.makeText(EditBVAProfileActivity.this, "Internet connection required!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof AuthFailureError)
                {
                    Toast.makeText(EditBVAProfileActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NetworkError)
                {
                    Toast.makeText(EditBVAProfileActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ServerError)
                {
                    Toast.makeText(EditBVAProfileActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ParseError)
                {
                    Toast.makeText(EditBVAProfileActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(ServerConstants.KEY_ID, id);
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

        MySingleton.getInstance(EditBVAProfileActivity.this).addToRequestQueue(stringRequest);
    }
}
